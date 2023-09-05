package project_pet_backEnd.productMall.order.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.productMall.order.dto.response.AllOrdersResDTO;
import project_pet_backEnd.productMall.order.dto.response.FindByOrdNoResDTO;
import project_pet_backEnd.productMall.order.dto.response.OrdersNotCancelDTO;
import project_pet_backEnd.productMall.order.vo.Orders;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByUserId(Integer userId);

    @Query(value = "SELECT new project_pet_backEnd.productMall.order.dto.response.OrdersNotCancelDTO(" +
            "o.ordNo, o.userId, o.ordStatus, o.ordPayStatus, o.ordPick, " +
            "o.ordCreate, o.ordFinish, o.ordFee, o.totalAmount, o.orderAmount, " +
            "o.recipientName, o.recipientAddress, o.recipientPh, o.evaluateStatus, " +
            "o.userPoint) FROM Orders o " +
            "WHERE o.ordStatus <> :ordStatus AND o.userId = :userId")
    List<OrdersNotCancelDTO> findByOrdStatusNotCancel(@Param("userId") Integer userId,
                                                      @Param("ordStatus") Integer ordStatus);

    Orders findByOrdNo(Integer ordNo);

    @Query(value = "SELECT new project_pet_backEnd.productMall.order.dto.response.FindByOrdNoResDTO(" +
            "o.ordNo,u.userName, o.userId, o.ordStatus, o.ordPayStatus, " +
            "o.ordPick, o.ordCreate, o.ordFinish, o.ordFee, " +
            "o.totalAmount, o.orderAmount, o.recipientName, " +
            "o.recipientAddress, o.recipientPh, o.userPoint," +
            "o.evaluateStatus, ol.qty, ol.price, p.pdName ) " +
            "FROM Orders o " +
            "JOIN project_pet_backEnd.user.vo.User u ON o.userId = u.userId " +
            "JOIN project_pet_backEnd.productMall.userPayment.vo.OrderList ol ON o.ordNo = ol.ordNo " +
            "JOIN project_pet_backEnd.productMall.productsmanage.vo.Product p ON ol.pdNo = p.pdNo " +
            "WHERE o.ordNo = :ordNo")
    List<FindByOrdNoResDTO> findFrontOrderResDtoList(@Param("ordNo") Integer ordNo);


    @Query(value = "SELECT new project_pet_backEnd.productMall.order.dto.response.AllOrdersResDTO( " +
            "o.ordNo, " +
            "u.userName, " +
            "o.ordCreate, " +
            "o.orderAmount, " +
            "o.recipientName, " +
            "o.recipientPh, " +
            "o.ordStatus, " +
            "o.ordPayStatus) " +
            "FROM Orders o " +
            "JOIN project_pet_backEnd.user.vo.User u ON o.userId = u.userId")
    List<AllOrdersResDTO> findAllOrdersList(Pageable pageable);
}
