package project_pet_backEnd.productMall.order.vo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Data //生成符合 Java Bean getter setter 無參建構子
@Entity
@Table(name = "orderlist") //注意有錯先排除這個大小寫的問題
public class OrderDetail{
    @EmbeddedId
    private OrderDetailPk id;
    @Column(name = "QTY")
    private Integer qty;   //訂購數量 quantity
    @Column(name = "PRICE")
    private Integer price; //訂購單價
}
