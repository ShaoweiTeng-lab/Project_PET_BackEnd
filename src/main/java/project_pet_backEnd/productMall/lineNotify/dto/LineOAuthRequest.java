package project_pet_backEnd.productMall.lineNotify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.productMall.lineNotify.Util.ApiUtil;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineOAuthRequest {
    private String client_id;
    private String redirect_uri;
    private String response_type = "code";
    private String scope = "notify";
    private String state = "csrfToken";
//    private String response_mode="form_post";

    public Map<String, Object> getUriParams(){
        try {
            //檢查值並回傳 屬性名與值構成的鍵值對
            beforeSendCheck();
            return ApiUtil.getRequestUriVariables(this);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    public void beforeSendCheck() {
        if(!StringUtils.hasText(client_id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"client_id is empty");
        }
        else if(!StringUtils.hasText(redirect_uri)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"redirect_uri is empty");

        }
        else if(!StringUtils.hasText(state)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"state is empty");
        }
    }
}
