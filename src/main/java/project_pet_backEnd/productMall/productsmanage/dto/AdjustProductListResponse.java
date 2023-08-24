package project_pet_backEnd.productMall.productsmanage.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Data        //res:修改商品列表中資訊(價格、狀態)
public class AdjustProductListResponse {
    @NotNull
    private Integer pdNo;

    @Min(0)
    @Max(1)
    private Integer pdStatus;  //商品上下架狀態 DEFAULT 1 ,'0:上架中  1:已下架 此為商品上架狀態'

}
