package project_pet_backEnd.productMall.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.productMall.order.dto.response.FrontOrderResDTO;
import project_pet_backEnd.productMall.order.vo.Orders;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByUserId(Integer userId);

    List<Orders> findByUserIdAndOrdStatus(Integer userId, Integer ordStatus);

//    @Query(value = "SELECT new project_pet_backEnd.productMall.order.dto.response.FrontOrderResDto( " +
//            "o.ORD_NO, o.USER_ID, o.ORD_STATUS, o.ORD_PAY_STATUS, " +
//            "o.ORD_PICK, o.ORD_CREATE, o.ORD_FINISH, o.ORD_FEE, " +
//            "o.TOTAL_AMOUNT, o.ORDER_AMOUNT, o.RECIPIENT, " +
//            "o.RECIPIENT_ADDRESS, o.RECIPIENT_PH, o.USER_POINT," +
//            "ol.QTY, ol.PRICE, p.PD_NAME ) " +
//            "FROM Orders as o " +
//            "JOIN user u ON o.USER_ID = u.USER_ID " +
//            "JOIN orderList ol ON o.ORD_NO = ol.ORD_NO " +
//            "JOIN product p ON ol.PD_NO = p.PD_NO " +
//            "WHERE o.ORD_NO = :ordNo")
//    List<FrontOrderResDTO> findFrontOrderResDtoList(@Param("ordNo") Integer ordNo);

//    @Query("SELECT new FrontOrderResDTO(" +
//            "o.ORD_NO ) " +
//            "FROM Orders o " +
//            "WHERE o.ORD_NO = :ordNo")
//    List<FrontOrderResDTO> findByOrdNo(@Param("ordNo") Integer ordNo);
}
