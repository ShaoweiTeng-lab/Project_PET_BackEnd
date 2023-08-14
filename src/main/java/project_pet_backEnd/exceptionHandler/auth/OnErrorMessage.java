package project_pet_backEnd.exceptionHandler.auth;

import lombok.Data;

@Data
public class OnErrorMessage {
    private String msg;
    private  int code=200;
}
