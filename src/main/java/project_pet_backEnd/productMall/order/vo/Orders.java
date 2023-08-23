package project_pet_backEnd.productMall.order.vo;

import lombok.Data;
import project_pet_backEnd.productMall.order.dto.OrderDetailDTO;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

@Data //生成符合 Java Bean getter setter 無參建構子
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORD_NO")
    private Integer ordNo;

    @Column(name = "USER_ID")
    @NotNull
    private Integer userId;

    @Column(name = "ORD_STATUS", insertable = false)
    private Integer ordStatus;

    @Column(name = "ORD_PAY_STATUS")
    private Integer ordPayStatus;

    @Column(name="ORD_PICK")
    private Integer ordPick;

    @Column(name = "ORD_CREATE", insertable = false)
    private Date ordCreate;

    @Column(name = "ORD_FINISH", insertable = false)
    private Date ordFinish;

    @Column(name = "ORD_FEE")
    private Integer ordFee;

    @Column(name = "TOTAL_AMOUNT")
    private Integer totalAmount;

    @Column(name = "ORDER_AMOUNT")
    @Min(0)
    private Integer orderAmount;

    @Column(name = "RECIPIENT")
    @NotBlank
    private String recipientName;

    @Column(name = "RECIPIENT_ADDRESS")
    @NotBlank
    private String recipientAddress;

    @Column(name = "RECIPIENT_PH")
    @NotBlank
    private String recipientPh;

    @Column(name = "USER_POINT", insertable = false)
    private Integer userPoint;

//    private List<OrderDetailDTO> orderDetailDTOS;
}
