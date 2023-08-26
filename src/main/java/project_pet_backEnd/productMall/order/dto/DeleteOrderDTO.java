package project_pet_backEnd.productMall.order.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data

public class DeleteOrderDTO {

    @Min(0)
    @Max(6)
    private Integer ordStatus;
}
