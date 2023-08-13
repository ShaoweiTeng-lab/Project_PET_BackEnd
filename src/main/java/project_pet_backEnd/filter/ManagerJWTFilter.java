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
        if (!requestURI.startsWith("/manager/")) {
            filterChain.doFilter(request,response);
            return;
        }
        String token = request.getHeader("Authorization_M");
        if(!StringUtils.hasText(token)) {
            filterChain.doFilter(request,response);
            return;
        }
        String  managerId=null;
        Claims claims= managerJwtUtil.validateToken(token);
        if(claims==null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"認證異常");
        managerId=claims.getSubject();
        String managerLoginJson=redisTemplate.opsForValue().get("Manager_Login_"+managerId);
        if(managerLoginJson==null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"您的權限已被修改，請重新登入");
        ManagerDetailsImp managerDetail=null;
        managerDetail=objectMapper.readValue(managerLoginJson,ManagerDetailsImp.class);
        if(managerDetail.getManager().getManagerState()==0)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"您已被停權");
        UsernamePasswordAuthenticationToken managerAuthentication =new UsernamePasswordAuthenticationToken(managerDetail,null,managerDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(managerAuthentication);
        request.setAttribute("managerId",Integer.valueOf(managerId));
        filterChain.doFilter(request,response);
    }
}
