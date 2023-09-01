package project_pet_backEnd.filter;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class IpRequestFilter extends OncePerRequestFilter {
    private static final int MAX_REQUESTS_PER_DAY = 10000;
    private Map<String, Integer> ipRequestCountMap = new ConcurrentHashMap<>();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String clientIp = request.getRemoteAddr();
        if(ipRequestCountMap.containsKey(clientIp)&&isIpBlocked(clientIp)) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/plain");
            response.getWriter().write("Access Denied: 您的IP已被封鎖.");
            return;
        }
        ipAddCount(clientIp);
        filterChain.doFilter(request,response);
    }


    public void clearCacheDaily() {
        // 清空缓存的操作，每天触发
        ipRequestCountMap.clear();
    }
    public boolean isIpBlocked(String clientIp) {
        int requestCount = ipRequestCountMap.get(clientIp);
        return requestCount > MAX_REQUESTS_PER_DAY;
    }
    public void ipAddCount(String clientIp) {
        if(!ipRequestCountMap.containsKey(clientIp)){
            ipRequestCountMap.put(clientIp,1);
            return;
        }
        int requestCount = ipRequestCountMap.get(clientIp);
        requestCount++;
        ipRequestCountMap.put(clientIp,requestCount);
    }
}
