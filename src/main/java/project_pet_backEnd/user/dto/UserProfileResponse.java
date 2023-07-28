package project_pet_backEnd.user.dto;

import lombok.Data;
import project_pet_backEnd.user.vo.IdentityProvider;

import java.sql.Date;

@Data
public class UserProfileResponse {
    private String userName;
    private  String userNickName;
    private  String userGender;
    private  String userAddress;
    private Date userBirthday;//sql.date
    private Integer userPoint;
    private String userPic;
    private IdentityProvider identityProvider;
    private java.util.Date userCreated;//util.date
}
