package project_pet_backEnd.productMall.order.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.productMall.order.dao.OrdersDao;
import project_pet_backEnd.productMall.order.dto.response.OrdersResTestDTO;


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
    public OrdersResTestDTO getByOrdNo(Integer ordNo) {
        Map<String, Object> map = new HashMap<>();
        map.put("ordNo", ordNo);
        List<OrdersResTestDTO> list = namedParameterJdbcTemplate.query(sqlSelectByOrdNo, map, new RowMapper<OrdersResTestDTO>() {
            @Override
            public OrdersResTestDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                OrdersResTestDTO ordersResTestDTO = new OrdersResTestDTO();
                ordersResTestDTO.setOrdNo(rs.getInt("ORD_NO"));
                ordersResTestDTO.setOrdStatus(rs.getByte("ORD_STATUS"));
                ordersResTestDTO.setOrdPayStatus(rs.getByte("ORD_PAY_STATUS"));
                ordersResTestDTO.setOrdPick(rs.getByte("ORD_PICK"));
                ordersResTestDTO.setOrdCreate(rs.getDate("ORD_CREATE"));
                ordersResTestDTO.setOrdFinish(rs.getDate("ORD_FINISH"));
                ordersResTestDTO.setOrdFee(rs.getInt("ORD_FEE"));
                ordersResTestDTO.setUserPoint(rs.getInt("USER_POINT"));
                ordersResTestDTO.setTotalAmount(rs.getInt("TOTAL_AMOUNT"));
                ordersResTestDTO.setOrderAmount(rs.getInt("ORDER_AMOUNT"));
                ordersResTestDTO.setRecipientName(rs.getString("RECIPIENT"));
                ordersResTestDTO.setRecipientAddress(rs.getString("RECIPIENT_ADDRESS"));
                ordersResTestDTO.setRecipientPh(rs.getString("RECIPIENT_PH"));
                return ordersResTestDTO;
            }
        });
        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }
}
