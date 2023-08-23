package project_pet_backEnd.productMall.productsmanage.vo;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "PRODUCT_PIC")
public class ProductPic {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "PD_PIC_NO")
        private Integer pdPicNo;

        @NotNull
        private Integer pdNo; // 商品編號 (Foreign Key)

        @Column(name = "PD_PIC")
        @Lob
        private byte[] pdPic; //商品照片 LONGBLOB

        @ManyToOne
        @JoinColumn(name = "PD_NO")
        private Product product;

}
