package project_pet_backEnd.groomer.petgroomer.dto.response;

import lombok.Data;

@Data
public class ChatRes {
    private Integer chatNo;
    private Integer userId;
    private Integer pgId;
    private String chatText;
    private String chatStatus;
    private String chatCreated;
    // 此處省略建構子、Getter 和 Setter 方法
}