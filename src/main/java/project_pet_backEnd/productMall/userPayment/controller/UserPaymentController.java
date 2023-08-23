package project_pet_backEnd.productMall.userPayment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.ecpay.demo.dto.OrderRequest;
import project_pet_backEnd.ecpay.demo.dto.OrderResponse;
import project_pet_backEnd.productMall.userPayment.service.UserPaymentService;
import project_pet_backEnd.utils.commonDto.ResultResponse;
@RequestMapping("/user")
@RestController
public class UserPaymentController {
    @Autowired
    private UserPaymentService userPaymentService;

    /**
     * 得到渲染後的form表單
     * */
    @GetMapping("/productMall/order/getPaymentForm")
    public ResultResponse<String> getPaymentForm(@RequestAttribute Integer userId, @RequestParam Integer orderId){
        ResultResponse rs =new ResultResponse();
        String  form =userPaymentService.getPaymentForm(userId,orderId);
        rs.setMessage(form);
        return  rs;
    }

    @PostMapping("/successPay/{orderId}")
    public ResponseEntity<String> successPayCallBack(@PathVariable("orderId") Integer orderId){
        userPaymentService.successPayCallBack(orderId);
        System.out.println("付款成功");
        return  ResponseEntity.status(HttpStatus.CREATED).body("付款成功");

    }

    @GetMapping("/productMall/order")
    public ResultResponse<String> checkIsPay(@RequestParam Integer orderId){

        ResultResponse rs =new ResultResponse();
        rs.setMessage(userPaymentService.checkIsPay(orderId));
        return  rs;

    }
}
