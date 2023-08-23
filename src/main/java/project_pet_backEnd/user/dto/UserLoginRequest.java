package project_pet_backEnd.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserLoginRequest {
    @NotBlank(message = "信箱不可為空")
    @Email
    private String email;
    @NotBlank(message = "密碼不可為空")
    private String  password;
}
