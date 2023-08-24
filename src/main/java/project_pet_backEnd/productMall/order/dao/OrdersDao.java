package project_pet_backEnd.productMall.order.dao;

import project_pet_backEnd.productMall.order.dto.response.OrdersResDTO;

public interface OrdersDao{
    /**
     * @param ordNo
     * @return
     * @測試用:用訂單編號查詢
     */
    public abstract OrdersResDTO getByOrdNo(Integer ordNo);

}
