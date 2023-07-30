package project_pet_backEnd.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
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
        String token = request.getHeader("Authorization");
        if(!StringUtils.hasText(token)) {
            filterChain.doFilter(request,response);
            return;
        }
        String  managerId=null;
        Claims claims= managerJwtUtil.validateToken(token);
        if(claims==null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().println("Token Auth Error");
            return;
        }
        managerId=claims.getSubject();
        String managerLoginJson=redisTemplate.opsForValue().get("Manager_Login_"+managerId);
        ManagerDetailsImp managerDetail=null;
        managerDetail=objectMapper.readValue(managerLoginJson,ManagerDetailsImp.class);
        UsernamePasswordAuthenticationToken managerAuthentication =new UsernamePasswordAuthenticationToken(managerDetail,null,managerDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(managerAuthentication);
        request.setAttribute("managerId",Integer.valueOf(managerId));
        filterChain.doFilter(request,response);
    }
}
