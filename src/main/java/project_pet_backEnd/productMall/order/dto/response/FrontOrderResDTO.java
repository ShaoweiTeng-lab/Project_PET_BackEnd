package project_pet_backEnd.productMall.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FrontOrderResDTO {

    private Integer ordNo;
    private Integer userId;
    private Integer ordStatus;
    private Integer ordPayStatus;
    private Integer ordPick;
    private LocalDateTime ordCreate;
    private LocalDateTime ordFinish;
    private Integer ordFee;
    private Integer totalAmount;
    private Integer orderAmount;
    private String recipientName;
    private String recipientAddress;
    private String recipientPh;
    private Integer userPoint;
    private Integer qty;
    private Integer price;
    private String pdName;

}
