package project_pet_backEnd.productMall.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.productMall.order.dto.response.OrdersRes;
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
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String sqlInsert = "insert into ORDERS(USER_ID, ORD_STATUS, ORD_PAY_STATUS," +
            "ORD_PICK, ORD_FEE, TOTAL_AMOUNT, ORDER_AMOUNT, RECIPIENT, RECIPIENT_ADDRESS, RECIPIENT_PH," +
            "USER_POINT) VALUES(:userId, :ordStatus, :ordPayStatus, :ordPick, :ordFee, :totalAmount, " +
            ":orderAmount, :recipient, :recipientAddress, :recipientPh, :userPoint)";
    private final String sqlDelete = "DELETE FROM ORDERS WHERE ORD_NO = :ordNo";
    private final String sqlSelectAll = "SELECT ORD_NO, RECIPIENT FROM ORDERS";
    private final String sqlSelectByOrdNo = "SELECT * FROM ORDERS WHERE ORD_NO = :ordNo";
    //練習:新增訂單
    //練習:取得自動生成之ID
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

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sqlInsert, new MapSqlParameterSource(map), keyHolder);
        int id = keyHolder.getKey().intValue();
        System.out.println("自動生成的id為:" + id);
        return "執行 INSERT sql";
    }

    //練習:刪除訂單
    @DeleteMapping("/orders/{ordNo}")
    public String delete(@PathVariable Integer ordNo){
        Map<String, Object> map = new HashMap<>();
        map.put("ordNo", ordNo);
        namedParameterJdbcTemplate.update(sqlDelete, map);
        return "執行 DELETE sql 成功";
    }


    //練習:查詢全部
    @GetMapping("/orders/selectAll")
    public List<OrdersRes> select(){

        Map<String, Object> map = new HashMap<>();

        List<OrdersRes> list = namedParameterJdbcTemplate.query(sqlSelectAll, map, new RowMapper<OrdersRes>() {
            @Override
            public OrdersRes mapRow(ResultSet rs, int rowNum) throws SQLException {
                OrdersRes ordersRes = new OrdersRes();
                ordersRes.setOrdNo(rs.getInt("ORD_NO"));
//                ordersRes.setOrdStatus(rs.getByte("ORD_STATUS"));
//                ordersRes.setOrdPayStatus(rs.getByte("ORD_PAY_STATUS"));
//                ordersRes.setOrdPick(rs.getByte("ORD_PICK"));
//                ordersRes.setOrdCreate(rs.getTimestamp("ORD_CREATE"));
//                ordersRes.setOrdFinish(rs.getDate("ORD_FINISH"));
//                ordersRes.setOrdFee(rs.getInt("ORD_FEE"));
//                ordersRes.setUserPoint(rs.getInt("USER_POINT"));
                ordersRes.setTotalAmount(rs.getInt("TOTAL_AMOUNT"));
                ordersRes.setOrderAmount(rs.getInt("ORDER_AMOUNT"));
                ordersRes.setRecipient(rs.getString("RECIPIENT"));
                ordersRes.setRecipientAddress(rs.getString("RECIPIENT_ADDRESS"));
                ordersRes.setRecipientPh(rs.getString("RECIPIENT_PH"));
                return ordersRes;
            }
        });
        return list;
    }

    //練習:查詢By 訂單編號ORD_NO
    @GetMapping("/orders/select/{ordNo}")
    public OrdersRes selectByOrdNo(@PathVariable Integer ordNo){

        Map<String, Object> map = new HashMap<>();
        map.put("ordNo", ordNo);
        List<OrdersRes> list = namedParameterJdbcTemplate.query(sqlSelectByOrdNo, map, new RowMapper<OrdersRes>() {
            @Override
            public OrdersRes mapRow(ResultSet rs, int rowNum) throws SQLException {
                OrdersRes ordersRes = new OrdersRes();
                ordersRes.setOrdNo(rs.getInt("ORD_NO"));
                ordersRes.setOrdStatus(rs.getByte("ORD_STATUS"));
                ordersRes.setOrdPayStatus(rs.getByte("ORD_PAY_STATUS"));
                ordersRes.setOrdPick(rs.getByte("ORD_PICK"));
                ordersRes.setOrdCreate(rs.getDate("ORD_CREATE"));
                ordersRes.setOrdFinish(rs.getDate("ORD_FINISH"));
                ordersRes.setOrdFee(rs.getInt("ORD_FEE"));
                ordersRes.setUserPoint(rs.getInt("USER_POINT"));
                ordersRes.setTotalAmount(rs.getInt("TOTAL_AMOUNT"));
                ordersRes.setOrderAmount(rs.getInt("ORDER_AMOUNT"));
                ordersRes.setRecipient(rs.getString("RECIPIENT"));
                ordersRes.setRecipientAddress(rs.getString("RECIPIENT_ADDRESS"));
                ordersRes.setRecipientPh(rs.getString("RECIPIENT_PH"));
                return ordersRes;
            }
        });
        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }
}
