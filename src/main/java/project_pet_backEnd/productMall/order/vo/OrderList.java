package project_pet_backEnd.productMall.order.vo;

import lombok.Data;
import java.io.Serializable;

@Data //生成符合 Java Bean getter setter 無參建構子
public class OrderList implements Serializable {
    private Integer ordNo; //訂單編號 {pk,fk}
    private Integer pdNo;  //商品編號 {pk,fk}
    private Integer qty;   //訂購數量 quantity
    private Integer price; //訂購單價
}
