package project_pet_backEnd.productMall.productsmanage.vo;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
@Data //自動讓所有屬性擁有getter/setter
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PD_NO")
    private Integer pdNo;

    @Column(name = "PD_NAME")
    private String pdName;

    @Column(name = "PD_PRICE")
    private Integer pdPrice;

    @Column(name = "PD_FORMAT")
    private String pdFormat;

    @Column(name = "PD_DESCRIPTION", columnDefinition = "TIMESTAMP  DEFAULT CURRENT_TIMESTAMP")
    private String pdDescription;

    @Column(name = "PD_UPDATE")
    private Date pdUpdate; //sql.date

    @Column(name = "PD_STATUS")
    private Integer pdStatus;

    @Column(name = "PD_TOTALREVIEW")
    private Integer pdTotalreview;

    @Column(name = "PD_SCORE")
    private Integer pdScore;
}
