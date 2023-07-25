package project_pet_backEnd.userLogin.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserLoginRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String  password;
}
