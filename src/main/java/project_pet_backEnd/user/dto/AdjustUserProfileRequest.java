package project_pet_backEnd.user.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.sql.Date;
@Data
public class AdjustUserProfileRequest {
    @NotBlank(message = "使用者名稱不可為空")
    private String  userName;
    private  String  userNickName;
    @NotNull
    @Min(0)
    @Max(1)
    private Integer userGender;
    @NotBlank(message = "使用者電話不可為空")
    private String  userPhone;
    @NotBlank(message = "使用者地址不可為空")
    private  String userAddress;
    private Date userBirthday;
    private byte[] userPic;
}
