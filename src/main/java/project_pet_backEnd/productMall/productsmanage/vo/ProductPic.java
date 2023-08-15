package project_pet_backEnd.productMall.productsmanage.vo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Lob;

@Data
public class ProductPic {
        private Integer pdPicNo;
        private Integer pdNo;
        private Integer pdOrderList;

        @Column(name = "PD_PIC")
        @Lob
        private byte[] pdPic;
}
