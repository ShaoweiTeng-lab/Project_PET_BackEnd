package project_pet_backEnd.exceptionHandler.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class AuthenticationEntryPointImp implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //處理異常 認證失敗 回傳  json格式
        ObjectMapper mapper = new ObjectMapper();
        OnErrorMessage msg =new OnErrorMessage();
        msg.setMsg("認證異常");
        String json =mapper.writeValueAsString(msg);
        response.setStatus(401);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().print(json);
    }
}
