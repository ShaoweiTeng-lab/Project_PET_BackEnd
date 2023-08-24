package project_pet_backEnd.productMall.order.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import project_pet_backEnd.productMall.order.dto.OrderDetailDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResDTO {

    private Integer ordNo;
    private String userName;
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


    private List<OrderDetailResDTO> detailList = new ArrayList<>();

    public void addOrderDetail(OrderDetailResDTO orderDetail) {
        detailList.add(orderDetail);
    }
}
