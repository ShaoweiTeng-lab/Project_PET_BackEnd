package project_pet_backEnd.user.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import project_pet_backEnd.user.vo.IdentityProvider;

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
    private String  userPhone;
    @NotBlank
    private  String userAddress;
    @ApiModelProperty(value = "Hidden property", hidden = true)
    private  byte[] userPic;
    @ApiModelProperty(value = "Hidden property", hidden = true)
    private Date userBirthday;
    @ApiModelProperty(value = "Hidden property", hidden = true)
    private IdentityProvider identityProvider;
    @NotBlank
    private String captcha;//驗證碼
}
