package project_pet_backEnd.productMall.order.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.productMall.order.dao.OrdersDao;
import project_pet_backEnd.productMall.order.dto.response.OrdersRes;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrdersDaoImpl implements OrdersDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String sqlSelectByOrdNo = "SELECT * FROM ORDERS WHERE ORD_NO = :ordNo";
    @Override
    public OrdersRes getByOrdNo(Integer ordNo) {
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
                ordersRes.setRecipientName(rs.getString("RECIPIENT"));
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
