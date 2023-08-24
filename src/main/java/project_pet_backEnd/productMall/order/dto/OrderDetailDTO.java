package project_pet_backEnd.productMall.order.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


@Data
public class OrderDetailDTO {
    @NotBlank
    private Integer proNo;
    @Min(0)
    private Integer orderListQty;

    private Integer orderListPrice;
}
