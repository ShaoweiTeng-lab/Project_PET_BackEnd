package project_pet_backEnd.user.model;

import lombok.Data;

import java.sql.Date;
@Data
public class User {
    private Integer userId;
    private String  userName;
    private  String  userNickName;
    private Integer userGender;
    private  String  userEmail;
    private String  userPassword;
    private  String userAddress;
    private Date userBirthday;;//sql.date
    private Integer userPoint;
    private byte[] userPic;
    private IdentityProvider identityProvider;
    private java.util.Date userCreated;//util.date
}
