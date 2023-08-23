package project_pet_backEnd.productMall.order.service;

import project_pet_backEnd.productMall.order.dto.CreateOrderDTO;
import project_pet_backEnd.productMall.order.dto.response.OrdersRes;
import project_pet_backEnd.productMall.order.vo.Orders;

import java.util.List;

public interface OrdersService {

    //新增訂單商業邏輯
    public abstract void createOrders(CreateOrderDTO createOrderDTO);

    //查詢該位會員所有未出貨之訂單
    public abstract List<Orders> getByUserIdAndOrdStatusNot(Integer userId);

    //刪除訂單商業邏輯--byOrdNo
    public abstract void deleteOrdersByOrdNo(Integer ordNo);

    //修改訂單商業邏輯--byOrdNo
    public abstract void updateOrdersByOrdNo(Integer ordNo,OrdersRes ordersRes);

    //查詢訂單商業邏輯--byOrdNo
    public abstract OrdersRes getByOrdNo(Integer ordNo);

    //查詢訂單商業邏輯--byUserId
    public abstract List<Orders> findByUserId(Integer userId);

    //查詢全部訂單商業邏輯
    public abstract List<Orders> selectAll();
}
