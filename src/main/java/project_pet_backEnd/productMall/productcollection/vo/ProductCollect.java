package project_pet_backEnd.productMall.productcollection.vo;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;


@Table(name = "PRODUCT_COLLECT")
@Entity
@Data
public class ProductCollect {

    @EmbeddedId
    private ProductCollectPk id;

    @Column(name = "PDC_CREATED" , columnDefinition = "DATE  DEFAULT CURRENT_DATE")
    private Date pdcCreated; //sql.date

    @PrePersist
    protected void onCreate() {
        pdcCreated = new Date(System.currentTimeMillis());
    }
}
