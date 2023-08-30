package project_pet_backEnd.userManager.dto;

import lombok.Data;
import project_pet_backEnd.user.dto.IdentityProvider;

import java.sql.Date;
@Data
public class UserDetailProfileResponse {
    private  Integer userId;
    private String userName;
    private  String userNickName;
    private  String userGender;
    private  String userAddress;
    private  String userPhone;
    private Date userBirthday;//sql.date
    private Integer userPoint;
    private String userPic;
    private IdentityProvider identityProvider;
    private String userCreated;
}
