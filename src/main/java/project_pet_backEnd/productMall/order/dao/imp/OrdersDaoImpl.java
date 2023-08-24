package project_pet_backEnd.productMall.order.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.productMall.order.dao.OrdersDao;
import project_pet_backEnd.productMall.order.dto.response.OrdersResDTO;


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
    public OrdersResDTO getByOrdNo(Integer ordNo) {
        Map<String, Object> map = new HashMap<>();
        map.put("ordNo", ordNo);
        List<OrdersResDTO> list = namedParameterJdbcTemplate.query(sqlSelectByOrdNo, map, new RowMapper<OrdersResDTO>() {
            @Override
            public OrdersResDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                OrdersResDTO ordersResDTO = new OrdersResDTO();
                ordersResDTO.setOrdNo(rs.getInt("ORD_NO"));
                ordersResDTO.setOrdStatus(rs.getByte("ORD_STATUS"));
                ordersResDTO.setOrdPayStatus(rs.getByte("ORD_PAY_STATUS"));
                ordersResDTO.setOrdPick(rs.getByte("ORD_PICK"));
                ordersResDTO.setOrdCreate(rs.getDate("ORD_CREATE"));
                ordersResDTO.setOrdFinish(rs.getDate("ORD_FINISH"));
                ordersResDTO.setOrdFee(rs.getInt("ORD_FEE"));
                ordersResDTO.setUserPoint(rs.getInt("USER_POINT"));
                ordersResDTO.setTotalAmount(rs.getInt("TOTAL_AMOUNT"));
                ordersResDTO.setOrderAmount(rs.getInt("ORDER_AMOUNT"));
                ordersResDTO.setRecipientName(rs.getString("RECIPIENT"));
                ordersResDTO.setRecipientAddress(rs.getString("RECIPIENT_ADDRESS"));
                ordersResDTO.setRecipientPh(rs.getString("RECIPIENT_PH"));
                return ordersResDTO;
            }
        });
        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }
}
