package project_pet_backEnd.user.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import project_pet_backEnd.user.vo.IdentityProvider;

import javax.validation.constraints.*;
import java.sql.Date;

@Data
public class UserSignUpRequest {
    @NotBlank(message = "使用者姓名不可為空")
    private String  userName;
    private  String  userNickName;
    @NotNull(message = "性別不可為空")
    @Min(0)
    @Max(1)
    private Integer userGender;
    @NotBlank(message = "信箱不可為空")
    @Email(message = "信箱格式異常")
    private  String  userEmail;
    @NotBlank(message = "密碼不可為空")
    private String  userPassword;
    @NotBlank(message = "電話不可為空")
    private String  userPhone;
    @NotBlank(message = "地址不可為空")
    private  String userAddress;
    @ApiModelProperty(value = "Hidden property", hidden = true)
    private  byte[] userPic;
    @ApiModelProperty(value = "Hidden property", hidden = true)
    private Date userBirthday;
    @ApiModelProperty(value = "Hidden property", hidden = true)
    private IdentityProvider identityProvider;
    @NotBlank(message = "驗證碼不可為空")
    private String captcha;//驗證碼
}
