package project_pet_backEnd.productMall.order.controller;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.productMall.order.dto.response.OrdersRes;
import project_pet_backEnd.productMall.order.service.OrdersService;
import project_pet_backEnd.productMall.order.vo.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class ProductMallController {

    @Autowired
    private OrdersService ordersService;

    //新增訂單 Spring Jpa寫法
    @PostMapping("/insertOrders")
    public ResponseEntity<String> insertOrders(@RequestBody OrdersRes ordersRes){
        ordersService.insertOrders(ordersRes);
        return ResponseEntity.status(HttpStatus.OK).body("新增成功!");
    }

    //刪除單一訂單By OrdId --練習
    @DeleteMapping("/deleteOrdersByOrdNo/{ordNo}")
    public ResponseEntity<String> deleteByOrdNo(@PathVariable Integer ordNo){
        ordersService.deleteOrdersByOrdNo(ordNo);
        return ResponseEntity.status(HttpStatus.OK).body("刪除訂單成功!");
    }

    //修改單一訂單By OrdId --練習
    @PutMapping("/updateOrdersByOrdNo/{ordNo}")
    public  ResponseEntity<String> updateByOrdNo(@PathVariable Integer ordNo,
                                                 @RequestBody OrdersRes ordersRes){
        ordersService.updateOrdersByOrdNo(ordNo,ordersRes);
        return ResponseEntity.status(HttpStatus.OK).body("修改訂單成功!");
    }

    //查詢全部訂單
    @GetMapping("/selectAllOrders")
    public ResponseEntity<List<Orders>> selectAll(){
        return ResponseEntity.status(HttpStatus.OK).body(ordersService.selectAll());
    }

    //查詢該使用者全部訂單
    @GetMapping("/selectByUserId/{userId}")
    public ResponseEntity<List<Orders>> selectByUserId(@PathVariable Integer userId){
        return ResponseEntity.status(HttpStatus.OK).body(ordersService.findByUserId(userId));
    }

    //練習:查詢By 訂單編號ORD_NO
    @GetMapping("/orders/select/{ordNo}")
    public ResponseEntity<OrdersRes> selectByOrdNo(@PathVariable Integer ordNo){
        return ResponseEntity.status(HttpStatus.OK).body(ordersService.getByOrdNo(ordNo));
    }

}
