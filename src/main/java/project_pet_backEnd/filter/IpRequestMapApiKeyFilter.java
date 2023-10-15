package project_pet_backEnd.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import project_pet_backEnd.scheduler.ITask;
import project_pet_backEnd.scheduler.IpScheduler;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class IpRequestMapApiKeyFilter extends OncePerRequestFilter implements  ITask {
    private static final int MAX_REQUESTS_PER_DAY = 1000;//設定 map api 一天內不可被呼叫次數
    private Map<String, Integer> ipRequestCountMap = new ConcurrentHashMap<>();
    @Autowired
    private IpScheduler ipScheduler;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @PostConstruct
    public void init(){
        ipScheduler.getTaskObserver().add(this);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!request.getRequestURI().startsWith("/customer/homePage/mapApiKey")){
            filterChain.doFilter(request,response);
            return;
        }
        //限制當日 api 請求次數 防止 google map key 訪問過多
        String clientIp = request.getRemoteAddr();
        ipAddCount(clientIp);
        if(isIpBlocked(clientIp)) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/plain");
            response.getWriter().write("Access Denied: 您的IP已被封鎖.");
            return;
        }
        filterChain.doFilter(request,response);
    }


    public void clearCacheDaily() {
        // 清空缓存的操作，每天触发
        ipRequestCountMap.clear();
    }
    public boolean isIpBlocked(String clientIp) {
        //如果已經變成黑名單 禁止訪問
        if(redisTemplate.opsForList().range("ipBlocked", 0, -1).contains(clientIp))
            return  true;
        int requestCount = ipRequestCountMap.get(clientIp);
        if(requestCount > MAX_REQUESTS_PER_DAY){//當日超出次數 列入異常ip
            redisTemplate.opsForList().leftPush("ipBlocked",clientIp);
            return true;
        }
        return false;
    }
    /**
     * 每次訪問記錄一次
     * */
    public void ipAddCount(String clientIp) {
        if(!ipRequestCountMap.containsKey(clientIp)){
            ipRequestCountMap.put(clientIp,1);
            return;
        }
        int requestCount = ipRequestCountMap.get(clientIp);
        requestCount++;
        ipRequestCountMap.put(clientIp,requestCount);
    }

    @Override
    public void execute() {
        clearCacheDaily();
    }
}
