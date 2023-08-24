package project_pet_backEnd.productMall.productsmanage.vo;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Table(name = "PRODUCT_COLLECT")
//@Entity (確認JPA)
@Data
public class ProductCollect {

    @Column(name = "PD_NO")
    private Integer pdNo;


    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "PDC_CREATED" , columnDefinition = "TIMESTAMP  DEFAULT CURRENT_DATE")
    private Date pdcCreated; //sql.date
}
