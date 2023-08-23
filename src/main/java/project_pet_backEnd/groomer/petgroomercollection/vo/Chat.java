package project_pet_backEnd.groomer.petgroomercollection.vo;

import lombok.Data;

import javax.persistence.Table;

@Table(name = "GROOMER_CHAT")
@Data
public class Chat {
    // 作品
    private Integer chatNo;
    private Integer userId;
    private Integer pgId;
    private String chatText;
    private String chatStatus;
    private java.sql.Date chatCreated;//util.date

    // 此處省略建構子、Getter 和 Setter 方法
}