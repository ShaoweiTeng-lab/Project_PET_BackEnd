package project_pet_backEnd.filter;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.user.dao.UserRepository;
import project_pet_backEnd.user.vo.User;
import project_pet_backEnd.userPushNotify.NotifyMsg;
import project_pet_backEnd.userPushNotify.NotifyType;
import project_pet_backEnd.userPushNotify.UserNotifyWebSocketHandler;
import project_pet_backEnd.utils.AllDogCatUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UserPointFilter extends OncePerRequestFilter {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserRepository userRepository;
    //todo 增加點數推波給使用者
    @Autowired
    private UserNotifyWebSocketHandler userNotifyWebSocketHandler;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        //每日登入 獲得點數
        //拿到 UserJWTFilter 設定的attribute
        Integer userId = (Integer) request.getAttribute("userId");
        if (userId == null){
            //若無拿到 放行
            filterChain.doFilter(request, response);
            return;
        } 
        String key = "User:loginTimeStamp:userId_" + userId;
        //獲得上次登入時間戳
        String loginTimeStampStr = redisTemplate.opsForValue().get(key);
        long currentTimestamp = System.currentTimeMillis(); //當前時間戳
        String dateString = AllDogCatUtils.timestampToString(currentTimestamp);
        if (loginTimeStampStr == null) {
            //第一次登入  贈送點數   設定當前時間戳
            redisTemplate.opsForValue().set(key, dateString);
            //增加點數
            userAddPoint(userId);
            filterChain.doFilter(request, response);
            return;
        }
        long oneDayInMillis = 24 * 60 * 60 * 1000; // 一天的毫秒数
        long loginTimeStamp = AllDogCatUtils.parseStringToTimeStamp(loginTimeStampStr);
        //上次發放點數的時間戳到現在超過一天
        if (Math.abs(currentTimestamp - loginTimeStamp) >= oneDayInMillis) {
            redisTemplate.opsForValue().set(key, dateString);
            userAddPoint(userId);
        }
        filterChain.doFilter(request, response);
    }

    private void userAddPoint(Integer userId)  {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "無此使用者");
        user.setUserPoint(user.getUserPoint()+5);//加上5點
        userRepository.save(user);
        //會員個人推播
        userNotifyWebSocketHandler.publishPersonalNotifyMsg(userId,new NotifyMsg(NotifyType.GetPoint,null,"每日登入增加點數:5"));

    }
}
