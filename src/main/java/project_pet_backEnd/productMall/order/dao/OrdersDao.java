package project_pet_backEnd.productMall.order.dao;

import project_pet_backEnd.productMall.order.dto.DeleteOrderDTO;
import project_pet_backEnd.productMall.order.dto.response.OrdersResTestDTO;

public interface OrdersDao{

    public abstract DeleteOrderDTO findOrdStatus(Integer ordNo);

    public abstract void deleteOrderDetail(Integer ordNo);

    public abstract void deleteOrder(Integer ordNo);

    /**
     * @param ordNo
     * @return
     * @測試用:用訂單編號查詢
     */
    public abstract OrdersResTestDTO getByOrdNo(Integer ordNo);


}
