package project_pet_backEnd.productMall.order.vo;

import lombok.Data;
import lombok.Generated;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Data //生成符合 Java Bean getter setter 無參建構子
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORD_NO")
    private Integer ordNo;
    @Column(name = "USER_ID")
    private Integer userId;
    @Column(name = "ORD_STATUS")
    private Byte ordStatus;
    @Column(name = "ORD_PAY_STATUS")
    private Byte ordPayStatus;
    @Column(name="ORD_PICK")
    private Byte ordPick;
    @Column(name = "ORD_CREATE", insertable = false)
    private Date ordCreate;
    @Column(name = "ORD_FINISH", insertable = false)
    private Date ordFinish;
    @Column(name = "ORD_FEE")
    private Integer ordFee;
    @Column(name = "TOTAL_AMOUNT")
    private Integer totalAmount;
    @Column(name = "ORDER_AMOUNT")
    private Integer orderAmount;
    @Column(name = "RECIPIENT")
    private String recipient;
    @Column(name = "RECIPIENT_ADDRESS")
    private String recipientAddress;
    @Column(name = "RECIPIENT_PH")
    private String recipientPh;
    @Column(name = "USER_POINT")
    private Integer userPoint;
}
