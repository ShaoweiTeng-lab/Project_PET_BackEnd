package project_pet_backEnd.productMall.lineNotify.dto;

import lombok.Data;

@Data
public class LineTokenResponse {
    private Integer status;
    private String message;
    private String access_token;
}
