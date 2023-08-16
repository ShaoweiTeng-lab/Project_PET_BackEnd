package project_pet_backEnd.productMall.order.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.productMall.order.dao.OrdersDao;
import project_pet_backEnd.productMall.order.dao.OrdersRepository;
import project_pet_backEnd.productMall.order.dto.response.OrdersRes;
import project_pet_backEnd.productMall.order.service.OrdersService;
import project_pet_backEnd.productMall.order.vo.Orders;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    OrdersDao ordersDao;
    @Autowired
    OrdersRepository ordersRepository;

    @Override
    public void insertOrders(OrdersRes ordersRes) {
        Orders orders = new Orders();
        if(ordersRes.getUserId() == null || ordersRes.getUserId() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "無此使用者");
        }
        orders.setUserId(ordersRes.getUserId());
        orders.setOrdStatus(ordersRes.getOrdStatus());
        orders.setOrdPayStatus(ordersRes.getOrdPayStatus());
        orders.setOrdPick(ordersRes.getOrdPick());
        orders.setOrdCreate(ordersRes.getOrdCreate());
        orders.setOrdFinish(ordersRes.getOrdFinish());
        orders.setOrdFee(ordersRes.getOrdFee());
        orders.setTotalAmount(ordersRes.getTotalAmount());
        orders.setOrderAmount(ordersRes.getOrderAmount());
        orders.setRecipient(ordersRes.getRecipient());
        orders.setRecipientPh(ordersRes.getRecipientPh());
        orders.setRecipientAddress(ordersRes.getRecipientAddress());
        orders.setUserPoint(ordersRes.getUserPoint());
        ordersRepository.save(orders);
    }

    @Override
    public void deleteOrdersByOrdNo(Integer ordNo) {
        Orders orders = ordersRepository.findById(ordNo).orElse(null);
        if (ordNo < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "請輸入正確訂單編號");
        }else if(orders == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "無此訂單,請重新輸入正確訂單編號");
        }else{
            ordersRepository.deleteById(ordNo);
        }

    }

    @Override
    public void updateOrdersByOrdNo(Integer ordNo, OrdersRes ordersRes) {
        Orders orders = ordersRepository.findById(ordNo).orElse(null);
        if(orders == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "無此訂單,請重新輸入正確訂單編號");
        }
        if(ordNo < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "請輸入正確的訂單編號");
        }
        orders.setUserId(ordersRes.getUserId());
        orders.setOrdStatus(ordersRes.getOrdStatus());
        orders.setOrdPayStatus(ordersRes.getOrdPayStatus());
        orders.setOrdPick(ordersRes.getOrdPick());
        orders.setOrdFee(ordersRes.getOrdFee());
        orders.setTotalAmount(ordersRes.getTotalAmount());
        orders.setOrderAmount(ordersRes.getOrderAmount());
        orders.setRecipient(ordersRes.getRecipient());
        orders.setRecipientPh(ordersRes.getRecipientPh());
        orders.setRecipientAddress(ordersRes.getRecipientAddress());
        orders.setUserPoint(ordersRes.getUserPoint());
        ordersRepository.save(orders);
    }

    @Override
    public OrdersRes getByOrdNo(Integer ordNo) {
        if(ordNo < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "請輸入正確的訂單編號");
        }else{
            return ordersDao.getByOrdNo(ordNo);
        }

    }

    @Override
    public List<Orders> findByUserId(Integer userId) {
        if(userId == null){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "請輸入正確會員編號");
        }else if(userId != null){
            return ordersRepository.findByUserId(userId);
        }
        return null;
    }

    @Override
    public List<Orders> selectAll() {
        List<Orders> list = new ArrayList<>();
        list = ordersRepository.findAll();
        return list;
    }


}
