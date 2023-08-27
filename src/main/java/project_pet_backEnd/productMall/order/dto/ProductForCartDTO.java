package project_pet_backEnd.productMall.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
public class ProductForCartDTO{
    private String pdName;
    private Integer pdPrice;
    private byte[] pics;
    public ProductForCartDTO() {}
}
