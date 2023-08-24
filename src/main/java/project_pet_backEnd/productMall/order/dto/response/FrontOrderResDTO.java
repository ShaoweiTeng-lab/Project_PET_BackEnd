package project_pet_backEnd.productMall.order.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrontOrderResDTO {

    private Integer ordNo;
    private Integer userId;
    private Integer ordStatus;
    private Integer ordPayStatus;
    private Integer ordPick;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime ordCreate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
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

//    public FrontOrderResDTO(Integer ordNo, Integer userId, Integer ordStatus, Integer ordPayStatus, Integer ordPick, LocalDateTime ordCreate, LocalDateTime ordFinish, Integer ordFee, Integer totalAmount, Integer orderAmount, String recipientName, String recipientAddress, String recipientPh, Integer userPoint, Integer qty, Integer price, String pdName) {
//        this.ordNo = ordNo;
//        this.userId = userId;
//        this.ordStatus = ordStatus;
//        this.ordPayStatus = ordPayStatus;
//        this.ordPick = ordPick;
//        this.ordCreate = ordCreate;
//        this.ordFinish = ordFinish;
//        this.ordFee = ordFee;
//        this.totalAmount = totalAmount;
//        this.orderAmount = orderAmount;
//        this.recipientName = recipientName;
//        this.recipientAddress = recipientAddress;
//        this.recipientPh = recipientPh;
//        this.userPoint = userPoint;
//        this.qty = qty;
//        this.price = price;
//        this.pdName = pdName;
//    }
}
