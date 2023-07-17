package project_pet_backEnd.exceptionHandler.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImp  implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //處理異常 授權失敗 回傳  json格式
        ObjectMapper mapper = new ObjectMapper();
        OnErrorMessage msg =new OnErrorMessage();
        msg.setMsg("授權異常");
        String json =mapper.writeValueAsString(msg);
        response.setStatus(403);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().print(json);
    }
}
