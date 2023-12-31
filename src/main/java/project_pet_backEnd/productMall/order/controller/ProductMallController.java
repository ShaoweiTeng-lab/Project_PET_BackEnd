package project_pet_backEnd.productMall.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.productMall.order.dto.response.OrdersResTestDTO;
import project_pet_backEnd.productMall.order.service.OrdersService;
import project_pet_backEnd.productMall.order.vo.Orders;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class ProductMallController {

    @Autowired
    private OrdersService ordersService;

    //新增訂單 Spring Jpa寫法
//    @PostMapping("/insertOrders")
//    public ResponseEntity<ResultResponse<String>> insertOrders(@RequestBody OrdersRes ordersRes){
//        ResultResponse rs = new ResultResponse();
//        ordersService.insertOrders(ordersRes);
//        rs.setMessage("新增成功");
//        return ResponseEntity.status(HttpStatus.OK).body(rs);
//    }

    //刪除單一訂單By OrdId --練習
    @DeleteMapping("/deleteOrdersByOrdNo/{ordNo}")
    public ResponseEntity<ResultResponse<String>> deleteByOrdNo(@PathVariable Integer ordNo){
        ResultResponse rs = new ResultResponse();
        rs.setMessage("刪除訂單成功!");
        ordersService.deleteOrdersByOrdNo(ordNo);
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

    //修改單一訂單By OrdId --練習
    @PutMapping("/updateOrdersByOrdNo/{ordNo}")
    public  ResponseEntity<ResultResponse<String>> updateByOrdNo(@PathVariable Integer ordNo,
                                                 @RequestBody OrdersResTestDTO ordersResTestDTO){
        ordersService.updateOrdersByOrdNo(ordNo, ordersResTestDTO);
        ResultResponse rs = new ResultResponse();
        rs.setMessage("修改訂單成功!");
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

    //查詢全部訂單
    @GetMapping("/selectAllOrders")
    public ResponseEntity<ResultResponse<List<Orders>>> selectAll(){
        ResultResponse rs = new ResultResponse();
        rs.setMessage(ordersService.selectAll());
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

    //查詢該使用者全部訂單
    @GetMapping("/selectByUserId/{userId}")
    public ResponseEntity<ResultResponse<List<Orders>>> selectByUserId(@PathVariable Integer userId){
        ResultResponse rs = new ResultResponse();
        rs.setMessage(ordersService.findByUserId(userId));
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

    //練習:查詢By 訂單編號ORD_NO
    @GetMapping("/orders/select/{ordNo}")
    public ResponseEntity<ResultResponse<OrdersResTestDTO>> selectByOrdNo(@PathVariable Integer ordNo){
        ResultResponse rs = new ResultResponse();
        rs.setMessage(ordersService.getByOrdNo(ordNo));
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

}
