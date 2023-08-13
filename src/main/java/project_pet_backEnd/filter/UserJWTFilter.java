package project_pet_backEnd.filter;


import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.user.dto.UserAuthentication;
import project_pet_backEnd.utils.UserJwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserJWTFilter extends OncePerRequestFilter {
    @Autowired
    private UserJwtUtil userJwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI =request.getRequestURI();
        if (!requestURI.startsWith("/user/")) {
            filterChain.doFilter(request,response);
            return;
        }

        String token = request.getHeader("Authorization_U");
        if(!StringUtils.hasText(token)) {
            filterChain.doFilter(request,response);
            return;
        }
        String  userId=null;
        Claims claims= userJwtUtil.validateToken(token);
        if(claims==null){
            filterChain.doFilter(request,response);
            return;
        }
        userId=claims.getSubject();
        UserAuthentication userAuthentication =new UserAuthentication();
        userAuthentication.setUserId(userId);
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
        request.setAttribute("userId",Integer.valueOf(userId));
        filterChain.doFilter(request,response);
    }
}
