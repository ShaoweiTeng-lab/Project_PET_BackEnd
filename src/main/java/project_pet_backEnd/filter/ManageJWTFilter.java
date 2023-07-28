package project_pet_backEnd.filter;


import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import project_pet_backEnd.user.dto.UserAuthentication;
import project_pet_backEnd.utils.ManagerJwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ManageJWTFilter extends OncePerRequestFilter {
    @Autowired
    private ManagerJwtUtil managerJwtUtil;
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
        UserAuthentication userAuthentication =new UserAuthentication();
        userAuthentication.setUserId(managerId);
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
        request.setAttribute("managerId",managerId);
        filterChain.doFilter(request,response);
    }
}
