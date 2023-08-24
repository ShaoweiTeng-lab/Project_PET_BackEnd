package project_pet_backEnd.exceptionHandler.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class AuthenticationEntryPointImp implements AuthenticationEntryPoint {
    @Autowired
    private ObjectMapper mapper;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //處理異常 認證失敗 回傳  json格式
        ResultResponse rs =new ResultResponse();
        rs.setMessage("認證異常");
        rs.setCode(401);
        String json =mapper.writeValueAsString(rs);
        response.setStatus(200);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().print(json);
    }
}
