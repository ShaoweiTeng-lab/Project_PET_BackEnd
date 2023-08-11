package project_pet_backEnd.productMall.productsmanage.vo;

import lombok.Data;

import java.sql.Date;

@Data
public class ProductCollect {
    private Integer pdNo;
    private Integer userId;
    private Date pdcCreated; //sql.date
}
