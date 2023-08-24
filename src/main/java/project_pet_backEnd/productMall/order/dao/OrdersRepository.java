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

    @Query(value = "SELECT new project_pet_backEnd.productMall.order.dto.response.FrontOrderResDTO(" +
            "o.ordNo, o.userId, o.ordStatus, o.ordPayStatus, " +
            "o.ordPick, o.ordCreate, o.ordFinish, o.ordFee, " +
            "o.totalAmount, o.orderAmount, o.recipientName, " +
            "o.recipientAddress, o.recipientPh, o.userPoint," +
            "ol.qty, ol.price, p.pdName ) " +
            "FROM Orders o " +
            "JOIN project_pet_backEnd.user.vo.User u ON o.userId = u.userId " +
            "JOIN project_pet_backEnd.productMall.userPayment.vo.OrderList ol ON o.ordNo = ol.ordNo " +
            "JOIN project_pet_backEnd.productMall.productsmanage.vo.Product p ON ol.pdNo = p.pdNo " +
            "WHERE o.ordNo = :ordNo")
    List<FrontOrderResDTO> findFrontOrderResDtoList(@Param("ordNo") Integer ordNo);



//    @Query("SELECT new FrontOrderResDTO(" +
//            "o.ORD_NO ) " +
//            "FROM Orders o " +
//            "WHERE o.ORD_NO = :ordNo")
//    List<FrontOrderResDTO> findByOrdNo(@Param("ordNo") Integer ordNo);
}
