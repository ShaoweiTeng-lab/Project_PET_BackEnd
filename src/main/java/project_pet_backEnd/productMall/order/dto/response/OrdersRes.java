package project_pet_backEnd.productMall.order.dto.response;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class OrdersRes {
    private Integer ordNo;
    private Integer userId;
    private Byte ordStatus;
    private Byte ordPayStatus;
    private Byte ordPick;
    private Date ordCreate;
    private Date ordFinish;
    private Integer ordFee;
    private Integer userPoint;
    private Integer totalAmount;
    private Integer orderAmount;
    private String recipientName;
    private String recipientAddress;
    private String recipientPh;
}
