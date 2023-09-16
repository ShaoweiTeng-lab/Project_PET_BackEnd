package project_pet_backEnd.groomer.petgroomer.dto.response;

import lombok.Data;

import javax.persistence.Column;

@Data
public class ChatRes {
    private Integer chatNo;
    private Integer userId;
    private String userName;
    private Integer pgId;
    private String pgName;
    private String chatText;
    private String chatStatus;
    private String chatCreated;
    private String userNickName;
    private Integer userGender;
    private String userGenderName;
    private String userEmail;
    private String userPhone;
    // 此處省略建構子、Getter 和 Setter 方法
}