package project_pet_backEnd.userLogin.dto;

import lombok.Data;
import project_pet_backEnd.userLogin.model.IdentityProvider;

import javax.validation.constraints.*;
import java.sql.Date;

@Data
public class UserSignUpRequest {
    @NotBlank
    private String  userName;
    private  String  userNickName;
    @NotNull
    @Min(0)
    @Max(1)
    private Integer userGender;
    @NotBlank
    @Email
    private  String  userEmail;
    @NotBlank
    private String  userPassword;
    @NotBlank
    private  String userAddress;
    private Date userBirthday;
    private IdentityProvider identityProvider;
    @NotBlank
    private String captcha;//驗證碼
}
