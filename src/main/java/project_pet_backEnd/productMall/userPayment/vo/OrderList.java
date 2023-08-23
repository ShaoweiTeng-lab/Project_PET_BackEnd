package project_pet_backEnd.productMall.userPayment.vo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(OrderListId.class)
public class OrderList {

    @Id
    @Column(name = "ORD_NO")
    private Integer ordNo; // 主鍵由 ORD_NO 和 PD_NO 組成
    @Id
    @Column(name = "PD_NO")
    private Integer pdNo;
    private int qty;
    private int price;
}
class  OrderListId implements Serializable {
    private Integer ordNo;
    private Integer pdNo;
}
