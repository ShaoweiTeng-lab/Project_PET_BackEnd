package project_pet_backEnd.filter;


import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.user.dao.UserDao;
import project_pet_backEnd.utils.UserJwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserJWTFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(!StringUtils.hasText(token)) {
            //放行  因為沒傳入security contextHolder ，其他過濾器會擋掉
            filterChain.doFilter(request,response);
            return;
        }
        String  userId=null;
        UserJwtUtil jwt =new UserJwtUtil();

        Claims claims= jwt.validateToken(token);
        if(claims==null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().println("Token null");
            return;
        }
        userId=claims.getSubject();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =new UsernamePasswordAuthenticationToken(userId,null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


        request.setAttribute("userId",userId);
        System.out.println("setAttribute : "+ request.getAttribute("userId"));

        filterChain.doFilter(request,response);


    }
}
