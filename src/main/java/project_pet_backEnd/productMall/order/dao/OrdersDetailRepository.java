package project_pet_backEnd.productMall.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.productMall.order.vo.OrderDetail;

@Repository
public interface OrdersDetailRepository extends JpaRepository<OrderDetail, Integer> {

//    @Query("SELECT new project_pet_backEnd.productMall.order.dto.response.FrontOrderResDTO(" +
//            "o.ORD_NO, " +
//            "o.USER_ID, " +
//            "o.ORD_STATUS, " +
//            "o.ORD_PAY_STATUS, " +
//            "o.ORD_PICK, " +
//            "o.ORD_CREATE, " +
//            "o.ORD_FINISH, " +
//            "o.ORD_FEE, " +
//            "o.TOTAL_AMOUNT, " +
//            "o.ORDER_AMOUNT, " +
//            "o.RECIPIENT, " +
//            "o.RECIPIENT_ADDRESS, " +
//            "o.RECIPIENT_PH, " +
//            "o.USER_POINT, " +
//            "ol.QTY, " +
//            "ol.PRICE, " +
//            "p.PD_NAME) " +
//            "FROM ORDERS o " +
//            "JOIN `user` u on o.USER_ID = u.USER_ID " +
//            "JOIN ORDERLIST ol ON o.ORD_NO = ol.ORD_NO " +
//            "JOIN PRODUCT p ON ol.PD_NO = p.PD_NO " +
//            "WHERE o.ORD_NO = :ORD_NO")
//    List<FrontOrderResDTO> findByOrdNo(@Param("ORD_NO") Integer ordNo);



}
