package project_pet_backEnd.productMall.productsmanage.vo;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PD_NO", unique = true)
    private Integer pdNo;

    @Column(name = "PD_NAME")
    private String pdName;

    @Column(name = "PD_PRICE")
    private Integer pdPrice;

    @Column(name = "PD_DESCRIPTION")
    private String pdDescription;

    @Column(name = "PD_UPDATE" , columnDefinition = "TIMESTAMP  DEFAULT CURRENT_TIMESTAMP")
    private Timestamp pdUpdate; //sql.Timestamp

    @Column(name = "PD_STATUS")
    private Integer pdStatus;

    @Column(name = "PD_TOTALREVIEW")
    private Integer pdTotalreview;

    private Integer pdScore;

    @OneToMany(mappedBy = "product")
    private List<ProductPic> pics;

    @PrePersist
    protected void onCreate() {
        pdUpdate = new Timestamp(System.currentTimeMillis());
    }
}
