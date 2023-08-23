package project_pet_backEnd.productMall.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project_pet_backEnd.productMall.order.vo.OrderDetail;

public interface OrdersDetailRepository extends JpaRepository<OrderDetail, Integer> {
}
