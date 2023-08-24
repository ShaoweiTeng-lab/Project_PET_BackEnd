package project_pet_backEnd.productMall.order.dto.response;

import lombok.Data;

@Data
public class OrderDetailResDTO {
    private Integer ordNo;
    private Integer pdNo;
    private Integer qty;
    private Integer price;
    private String proName;
}
