package project_pet_backEnd.productMall.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import project_pet_backEnd.productMall.order.vo.Orders;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateOrderDTO {
    @NotNull
    private Orders orders;
    @NotEmpty
    @JsonProperty("detailList")
    private List<OrderDetailDTO> orderDetailDTOS;
}
