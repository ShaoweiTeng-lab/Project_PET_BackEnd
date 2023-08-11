package project_pet_backEnd.productMall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.productMall.vo.Orders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class ProductMallController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String sqlInsert = "insert into ORDERS(USER_ID, ORD_STATUS, ORD_PAY_STATUS," +
            "ORD_PICK, ORD_FEE, TOTAL_AMOUNT, ORDER_AMOUNT, RECIPIENT, RECIPIENT_ADDRESS, RECIPIENT_PH," +
            "USER_POINT) VALUES(:userId, :ordStatus, :ordPayStatus, :ordPick, :ordFee, :totalAmount, " +
            ":orderAmount, :recipient, :recipientAddress, :recipientPh, :userPoint)";
    private final String sqlDelete = "DELETE FROM ORDERS WHERE ORD_NO = :ordNo";
    //查詢資料庫有沒有訂單 萬能的Map
    @GetMapping("/ordersList")
    public List<Map<String, Object>> ordersList(){
        String sql = "SELECT * FROM manager";
        List<Map<String, Object>> listMaps = jdbcTemplate.queryForList(sql);
        return listMaps;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    //練習:新增訂單
    @PostMapping("/orders")
    public String insert(@RequestBody Orders orders){
        Map<String, Object> map = new HashMap<>();
        map.put("userId", orders.getUserId());
        map.put("ordStatus", orders.getOrdStatus());
        map.put("ordPayStatus", orders.getOrdPayStatus());
        map.put("ordPick", orders.getOrdPick());
        map.put("ordFee", orders.getOrdFee());
        map.put("totalAmount", orders.getTotalAmount());
        map.put("orderAmount", orders.getOrderAmount());
        map.put("recipient", orders.getRecipient());
        map.put("recipientAddress", orders.getRecipientAddress());
        map.put("recipientPh", orders.getRecipientPh());
        map.put("userPoint", orders.getUserPoint());
        namedParameterJdbcTemplate.update(sqlInsert, map);
        return "執行 INSERT sql";
    }

    //刪除訂單
    @DeleteMapping("/orders/{ordNo}")
    public String delete(@PathVariable Integer ordNo){
        Map<String, Object> map = new HashMap<>();
        map.put("ordNo", ordNo);
        namedParameterJdbcTemplate.update(sqlDelete, map);
        return "執行 DELETE sql 成功";
    }

}
