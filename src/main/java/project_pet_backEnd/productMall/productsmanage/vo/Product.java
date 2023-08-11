package project_pet_backEnd.productMall.productsmanage.vo;

import lombok.Data;

import java.sql.Date;
@Data //自動讓所有屬性擁有getter/setter
public class Product {
    private Integer pdNo;
    private String pdName;
    private Integer pdPrice;
    private String pdDescription;
    private Date pdUpdate; //sql.date
    private Integer pdStatus;
    private Integer pdTotalreview;
    private Integer pdScore;
}
