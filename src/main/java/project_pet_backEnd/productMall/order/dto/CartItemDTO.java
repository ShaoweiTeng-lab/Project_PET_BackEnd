package project_pet_backEnd.productMall.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CartItemDTO {
    private Integer pdNo; // 商品ID
    private String productName; // 商品名稱（可選）
    private Integer productPrice; // 商品價格（可選）
    private byte[] productImage; // 商品圖片URL（可選）
    private Integer quantity; // 商品數量

    public CartItemDTO(Integer pdNo, String productName, Integer productPrice, byte[] productImage, Integer quantity) {
        this.pdNo = pdNo;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.quantity = quantity;
    }

    public CartItemDTO() {

    }

// 其他需要的屬性
}
