package project_pet_backEnd.productMall.productsmanage.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data        // 新增商品
public class CreateProductInfoRequest {

    @NotNull
    @NotBlank
    private String pdName; //商品名稱 VARCHAR(15) NOT NULL

    @NotNull
    private Integer pdPrice; //商品價錢 NOT NULL

    private String pdFormat; //商品規格 VARCHAR(15)

    @Min(0)
    @Max(1)
    private Integer pdStatus;  //商品上下架狀態 DEFAULT 1 ,'0:上架中  1:已下架 此為商品上架狀態'

    private String pdDescription; //商品說明 VARCHAR(500)

}
