package project_pet_backEnd.user.vo;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Integer userId;
    @Column(name = "USER_NAME")
    private String  userName;
    @Column(name = "USER_NICKNAME")
    private  String  userNickName;
    @Column(name = "USER_GENDER")
    private Integer userGender;
    @Column(name = "USER_EMAIL")
    private  String  userEmail;
    @Column(name = "USER_PASSWORD")
    private String  userPassword;
    @Column(name = "USER_PHONE")
    private String  userPhone;
    @Column(name = "USER_ADDRESS")
    private  String userAddress;
    @Column(name = "USER_BIRTHDAY")
    private Date userBirthday;//sql.date
    @Column(name = "USER_POINT", columnDefinition = "INT DEFAULT 0")
    private Integer userPoint;
    @Column(name = "USER_PIC")
    private byte[] userPic;
    @Column(name = "USER_PROVIDER")
    @Enumerated(EnumType.STRING)
    private IdentityProvider identityProvider;
    @Column(name = "USER_CREATED", columnDefinition = "TIMESTAMP  DEFAULT CURRENT_TIMESTAMP")
    private java.util.Date userCreated;//util.date
}
