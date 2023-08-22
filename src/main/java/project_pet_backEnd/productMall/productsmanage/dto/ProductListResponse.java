package project_pet_backEnd.productMall.productsmanage.dto;

import lombok.Data;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data        //res:條列顯示所有 商品列表(編號、名稱、價格、狀態) 、 對應欄位輸入(查詢)後，顯示資訊
public class ProductListResponse {

    @NotNull
    private Integer pdNo; //商品編號 Primary Key, AUTO_INCREMENT

    @NotNull
    @NotBlank
    private String pdName; //商品名稱 VARCHAR(15) NOT NULL

    @NotNull
    private Integer pdPrice; //商品價錢 NOT NULL

    @Min(0)
    @Max(1)
    private Integer pdStatus;  //商品上下架狀態 DEFAULT 1 ,'0:上架中  1:已下架 此為商品上架狀態'

}
