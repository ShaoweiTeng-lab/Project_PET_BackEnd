package project_pet_backEnd.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.manager.security.ManagerDetailsImp;
import project_pet_backEnd.utils.ManagerJwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ManagerJWTFilter extends OncePerRequestFilter {
    @Autowired
    private ManagerJwtUtil managerJwtUtil;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI =request.getRequestURI();
        //URL 路徑檢查
        if (!requestURI.startsWith("/manager/")) {
            filterChain.doFilter(request,response);
            return;
        }
        String token = request.getHeader("Authorization_M");
        if(!StringUtils.hasText(token)) {
            //若無 token 依然可放行，因沒傳入 security context ，之後的filter會throw AuthenticationException
            filterChain.doFilter(request,response);
            return;
        }
        String  managerId=null;
        //驗證 jwt
        Claims claims= managerJwtUtil.validateToken(token);
        if(claims==null){
            //返回 null 代表驗證失敗
            filterChain.doFilter(request,response);
            return;
        }
        //取得sub
        managerId=claims.getSubject();
        //拿回 redis 儲存的 已經認證的principal
        String managerLoginJson=redisTemplate.opsForValue().get("Manager:Login:"+managerId);
        if(managerLoginJson==null){
            //被最高管理員修改後 會須重新登入
            filterChain.doFilter(request,response);
            return;
        }
        ManagerDetailsImp managerDetail=null;
        managerDetail=objectMapper.readValue(managerLoginJson,ManagerDetailsImp.class);
        if(managerDetail.getManager().getManagerState()==0){
            //被停權
            filterChain.doFilter(request,response);
            return;
        }
        //認證成功 傳入 SecurityContext  到當前SecurityContextHolder
        UsernamePasswordAuthenticationToken managerAuthentication =new UsernamePasswordAuthenticationToken(managerDetail,null,managerDetail.getAuthorities());
        //傳入此次請求的安全上下文
        SecurityContextHolder.getContext().setAuthentication(managerAuthentication);
        //設定attribute ，讓controller 獲得
        request.setAttribute("managerId",Integer.valueOf(managerId));
        //認證通過
        filterChain.doFilter(request,response);
    }
}
