package project_pet_backEnd.productMall.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import project_pet_backEnd.productMall.order.dto.response.OrdersRes;
import project_pet_backEnd.productMall.order.vo.Orders;

public interface OrdersDao{
    /**
     * @param ordNo
     * @return
     * @測試用:用訂單編號查詢
     */
    public abstract OrdersRes getByOrdNo(Integer ordNo);

}
