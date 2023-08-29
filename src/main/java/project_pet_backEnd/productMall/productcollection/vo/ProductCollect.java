package project_pet_backEnd.productMall.productcollection.vo;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;


@Table(name = "PRODUCT_COLLECT")
@Entity
@Data
public class ProductCollect {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PD_NO")
    @Id
    private Integer pdNo;


    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "PDC_CREATED" , columnDefinition = "TIMESTAMP  DEFAULT CURRENT_DATE")
    private Date pdcCreated; //sql.date
}
