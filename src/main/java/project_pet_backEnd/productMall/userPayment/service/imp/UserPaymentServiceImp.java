package project_pet_backEnd.productMall.userPayment.service.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.ecpay.payment.integration.AllInOne;
import project_pet_backEnd.ecpay.payment.integration.domain.AioCheckOutALL;
import project_pet_backEnd.productMall.lineNotify.dto.LineNotifyResponse;
import project_pet_backEnd.productMall.lineNotify.service.LineNotifyService;
import project_pet_backEnd.productMall.order.dao.OrdersRepository;
import project_pet_backEnd.productMall.order.vo.Orders;
import project_pet_backEnd.productMall.userPayment.dao.OrderListRepository;
import project_pet_backEnd.productMall.userPayment.service.UserPaymentService;
import project_pet_backEnd.utils.AllDogCatUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserPaymentServiceImp implements UserPaymentService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderListRepository orderListRepository;
    private  final  static Logger log= LoggerFactory.getLogger(UserPaymentServiceImp.class);
    @Autowired
    private LineNotifyService lineNotifyService;
    @Value("${ecpay-RedirectHttpsUrl}")
    private String ecpayRedirectHttpsUrl;
    @Override
    public String getPaymentForm(Integer userId,Integer orderId) {
        Orders orders= ordersRepository.findById(orderId).orElse(null);
        if(orders==null|| orders.getUserId()!=userId)
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"您輸入的訂單編號錯誤");
        if(orders.getOrdPayStatus().intValue()==1)
            throw  new ResponseStatusException(HttpStatus.GONE,"您已經完成訂單");//回傳410 表示所請求的資源不再可用
        List<String> products=orderListRepository.findOrderProductByOrderId(orderId);
        StringBuilder str =new StringBuilder();
        products.forEach(val->str.append(val+"  "));
        String form=generateEcpayForm(orderId,str.toString(),orders.getOrderAmount());

        return form;
    }

    @Override
    public void successPayCallBack(Integer orderId) {
        Orders orders= ordersRepository.findById(orderId).orElse(null);
        if(orders==null)
            log.warn("orderId : "+orderId+" 回傳異常");
        orders.setOrdPayStatus(1); //修改為1 完成訂單
        orders.setOrdFinish(LocalDateTime.now());
        ordersRepository.save(orders);
        String toManagerMessage = "訂單編號為:" + orderId + "->已付款完成,請管理員準備出貨喔!!";
        LineNotifyResponse lineNotifyResponse = new LineNotifyResponse();
        lineNotifyResponse.setMessage(toManagerMessage);
        lineNotifyService.notify(lineNotifyResponse);
    }

    @Override
    public String checkIsPay(Integer orderId) {
        Orders orders= ordersRepository.findById(orderId).orElse(null);
        if(orders==null) {
            log.warn("orderId : " + orderId + " 回傳異常");
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"無此訂單");
        }
        int ordPayStatus=orders.getOrdPayStatus();
        if(ordPayStatus==1)
            return "isPay";
        return  "unPay";
    }


    public String generateEcpayForm(Integer orderId,String productName,Integer total){

        AllInOne all = new AllInOne("");
        String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
        AioCheckOutALL obj = new AioCheckOutALL();
        obj.setMerchantTradeNo(uuId);
        obj.setMerchantTradeDate("2023/12/31 08:05:23");
        obj.setTotalAmount(Integer.toString(total));
        obj.setTradeDesc("test Description");
        obj.setItemName(productName);
        // 交易結果回傳網址，只接受 https 開頭的網站 ;
        obj.setNeedExtraPaidInfo("N");
        // 商店轉跳網址 (Optional)
        obj.setReturnURL(ecpayRedirectHttpsUrl+"/user/successPay/"+orderId);
        String form = all.aioCheckOut(obj, null);
        //obj.getMerchantTradeNo();
        return form;
    }
}
