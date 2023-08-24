package project_pet_backEnd.productMall.order.service;

import project_pet_backEnd.productMall.order.dto.CreateOrderDTO;
import project_pet_backEnd.productMall.order.dto.response.FrontOrderResDTO;
import project_pet_backEnd.productMall.order.dto.response.OrderResDTO;
import project_pet_backEnd.productMall.order.dto.response.OrdersResDTO;
import project_pet_backEnd.productMall.order.vo.Orders;

import java.util.List;

public interface OrdersService {

    //新增訂單商業邏輯
    public abstract void createOrders(CreateOrderDTO createOrderDTO);

    //查詢該位會員所有未出貨之訂單
    public abstract List<Orders> getByUserIdAndOrdStatusNot(Integer userId);

    //查詢該筆訂單詳情
    public abstract List<FrontOrderResDTO> getOrderDetailByOrdNo(Integer ordNo);

    //查詢該位會員所有未出貨之訂單
    public abstract String updateOrderStatus(Integer ordNo, Integer ordStatus);

    //刪除訂單商業邏輯--byOrdNo
    public abstract void deleteOrdersByOrdNo(Integer ordNo);

    //修改訂單商業邏輯--byOrdNo
    public abstract void updateOrdersByOrdNo(Integer ordNo, OrdersResDTO ordersResDTO);

    //查詢訂單商業邏輯--byOrdNo
    public abstract OrdersResDTO getByOrdNo(Integer ordNo);

    //查詢訂單商業邏輯--byUserId
    public abstract List<Orders> findByUserId(Integer userId);

    //查詢全部訂單商業邏輯
    public abstract List<Orders> selectAll();
}
