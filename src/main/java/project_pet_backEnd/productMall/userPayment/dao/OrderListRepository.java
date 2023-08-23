package project_pet_backEnd.productMall.userPayment.dao;

<<<<<<< HEAD
//todo 等待 orderList變成 @Entity
public interface OrderListRepository  {
=======
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.productMall.userPayment.vo.OrderList;

import java.util.List;


@Repository
public interface OrderListRepository extends JpaRepository<OrderList, Integer> {
    @Query(value = "select P.PD_NAME from product p \n" +
                    "join orderlist ol on p.PD_NO = ol.PD_NO\n" +
                    "where ol.ORD_NO = ?1 ",nativeQuery = true)
    List<String> findOrderProductByOrderId(Integer orderID);
>>>>>>> b8700379cc9c4bdc786f9f9ca0cf57d3ff42334f
}
