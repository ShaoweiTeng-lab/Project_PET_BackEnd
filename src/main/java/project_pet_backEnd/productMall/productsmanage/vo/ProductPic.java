package project_pet_backEnd.productMall.productsmanage.vo;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "PRODUCT_PIC")
public class ProductPic {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "PD_PIC_NO")
        private Integer pdPicNo;

        @Column(name = "PD_NO",insertable = false,updatable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer pdNo; // 商品編號 (Foreign Key)

        @Column(name = "PD_PIC")
        @Lob
        private byte[] pdPic; //商品照片 LONGBLOB

        @Column(name = "PIC_ORDER")
        private Integer pdOrderList;

        @ManyToOne
        @JoinColumn(name = "PD_NO")
        private Product product;

}
