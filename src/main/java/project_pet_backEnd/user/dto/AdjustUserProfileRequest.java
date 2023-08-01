package project_pet_backEnd.user.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.sql.Date;
@Data
public class AdjustUserProfileRequest {
    @NotBlank
    private String  userName;
    private  String  userNickName;
    @NotNull
    @Min(0)
    @Max(1)
    private Integer userGender;
    @NotBlank
    private String  userPassword;
    @NotBlank
    private String  userPhone;
    @NotBlank
    private  String userAddress;
    private Date userBirthday;
    private byte[] userPic;
}
