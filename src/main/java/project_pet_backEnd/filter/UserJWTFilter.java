package project_pet_backEnd.filter;


import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
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
        //URL 路徑檢查
        if (!requestURI.startsWith("/user/")) {
            filterChain.doFilter(request,response);
            return;
        }

        String token = request.getHeader("Authorization_U");
        //檢查字串是否包含任何非空白字元
        if(!StringUtils.hasText(token)) {
            //若無 token 依然可放行，因沒傳入 security context ，之後的filter會throw AuthenticationException
            filterChain.doFilter(request,response);
            return;
        }
        String  userId=null;
        Claims claims= userJwtUtil.validateToken(token);
        if(claims==null){
            //返回 null 代表驗證失敗
            filterChain.doFilter(request,response);
            return;
        }
        userId=claims.getSubject();
        //認證成功 傳入 SecurityContext  到當前SecurityContextHolder
        UserAuthentication userAuthentication =new UserAuthentication();
        userAuthentication.setUserId(userId);
        //傳入此次請求的安全上下文
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
        //設定attribute ，讓controller 獲得
        request.setAttribute("userId",Integer.valueOf(userId));
        //認證通過
        filterChain.doFilter(request,response);
    }
}
