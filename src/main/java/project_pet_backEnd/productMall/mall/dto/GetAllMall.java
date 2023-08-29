package project_pet_backEnd.productMall.mall.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class GetAllMall {

    private Integer pdNo;
    private String pdName;
    private Integer pdPrice;
    @Min(0)
    @Max(1)
    private Integer pdStatus;  //商品上下架狀態 DEFAULT 1 ,'0:上架中  1:已下架 此為商品上架狀態'
    private String base64Image;;
}
