package project_pet_backEnd.productMall.productcollection.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.productMall.productcollection.dao.ProductCollectDao;
import project_pet_backEnd.productMall.productcollection.dto.EditProductCollect;
import project_pet_backEnd.productMall.productcollection.dto.ProductCollectList;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductCollectDaoImp implements ProductCollectDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

//    @Override
//    public void insertProductCollect(EditProductCollect editProductCollect) {
//        String sql = "INSERT INTO PRODUCT_COLLECT (PD_NO, USER_ID) " +
//                "VALUES(:pdNo,:userId)";
//        Map<String, Object> map = new HashMap<>();
//        map.put("pdNo", editProductCollect.getPdNo());
//        map.put("userId", editProductCollect.getUserId());
//        namedParameterJdbcTemplate.update(sql, map);
//
//    }

//    @Override
//    public void insertProductCollect(Integer pdNo){
//
//    }

//    @Override
//    public void deleteProductCollect(EditProductCollect editProductCollect) {
//        String sql = "DELETE FROM PRODUCT_COLLECT WHERE PD_NO = :pdNo";
//        Map<String, Object> map = new HashMap<>();
//        map.put("pdNo", editProductCollect.getPdNo());
//        namedParameterJdbcTemplate.update(sql, map);
//    }

//    @Override
//    public void deleteProductCollect(Integer pdNo) {
//
//
//    }

    @Override
    public List<ProductCollectList> getAllCollect(Integer userId) {
        String sql = "SELECT PC.PD_NO, PC.USER_ID, P.PD_PRICE, PIC.PD_PIC " +
                    "FROM PRODUCT_COLLECT PC " +
                    "JOIN PRODUCT P ON PC.PD_NO = P.PD_NO " +
                    "JOIN PRODUCT_PIC PIC ON PC.PD_NO = PIC.PD_NO AND PIC.PIC_ORDER = 1  " +
                    "WHERE PC.USER_ID = ? " +
                    "ORDER BY PDC_CREATED DESC";
        List<ProductCollectList> list = namedParameterJdbcTemplate.query(sql, new RowMapper<ProductCollectList>() {
            @Override
            public ProductCollectList mapRow(ResultSet rs, int rowNum) throws SQLException {
                ProductCollectList pc = new ProductCollectList();
                pc.setPdNo(rs.getInt("PD_NO"));
                pc.setUserId(rs.getInt("USER_ID"));
                pc.setPdPrice(rs.getInt("PD_PRICE"));
                pc.setBase64Image(rs.getString("PD_PIC"));

                // 將 BLOB 數據轉換為 Base64 字符串
                Blob blob = rs.getBlob("PD_PIC");
                if (blob != null) {
                    byte[] blobData = blob.getBytes(1, (int) blob.length());
                    String base64Image = Base64.getEncoder().encodeToString(blobData);
                    pc.setBase64Image(base64Image);
                }
                return pc;
            }
        });
        return list;
    }
}
