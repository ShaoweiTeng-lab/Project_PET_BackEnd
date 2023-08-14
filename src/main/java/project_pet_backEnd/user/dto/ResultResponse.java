package project_pet_backEnd.user.dto;

import lombok.Data;

@Data
public class ResultResponse {
    private Integer code =200;
    private  Object message;
}
