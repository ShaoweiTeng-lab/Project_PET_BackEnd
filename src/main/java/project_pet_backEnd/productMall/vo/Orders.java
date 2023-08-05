package project_pet_backEnd.productMall.vo;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data //生成符合 Java Bean getter setter 無參建構子
public class Orders {
    private Integer ordNo;
    private Integer userId;
    private Byte ordStatus;
    private Byte ordPayStatus;
    private Byte ordPick;
    private Timestamp ordCreate;
    private Date ordFinish;
    private Integer ordFee;
    private Integer totalAmount;
    private Integer orderAmount;
    private String recipient;
    private String recipientAddress;
    private String recipientPh;
    private Integer userPoint;
}
