package project_pet_backEnd.productMall.order.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResDTO {
    private Integer ordNo;
    private Integer userId;
    private Integer ordStatus;
    private Integer ordPayStatus;
    private Integer ordPick;
    private LocalDateTime ordCreate;
    private LocalDateTime ordFinish;
    private Integer ordFee;
    private Integer userPoint;
    private Integer totalAmount;
    private Integer orderAmount;
    private String recipientName;
    private String recipientAddress;
    private String recipientPh;
    private List<OrderDetailResDTO> detailList;

}
