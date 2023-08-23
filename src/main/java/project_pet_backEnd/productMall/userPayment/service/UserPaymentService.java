package project_pet_backEnd.productMall.userPayment.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;


public interface UserPaymentService {
   String getPaymentForm(Integer userId,Integer orderId);
   void  successPayCallBack(Integer orderId);

   String checkIsPay(Integer orderId);
}
