package project_pet_backEnd.productMall.productsmanage.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.productMall.productsmanage.dao.ProductsManageDao;
import project_pet_backEnd.productMall.productsmanage.dto.CreateProductRequest;
import project_pet_backEnd.productMall.productsmanage.dto.ProductListResponse;
import project_pet_backEnd.productMall.productsmanage.vo.Product;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductsManageDaoImp implements ProductsManageDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public void insertProduct(CreateProductRequest createProductRequest) {
        String sql = "INSERT INTO PRODUCT (" +
                "PD_NAME, " +
                "PD_PRICE, " +
                "PD_FORMAT, " +
                "PD_DESCRIPTION, " +
                "PD_STATUS )" +
                "VALUES (" +
                ":pdName, " +
                ":pdPrice, " +
                ":pdFormat, " +
                ":pdDescription, " +
                ":pdStatus)";

        Map<String, Object> map = new HashMap<>();
        map.put("pdName", createProductRequest.getPdName());
        map.put("pdPrice", createProductRequest.getPdPrice());
        map.put("pdFormat", createProductRequest.getPdFormat());
        map.put("pdDescription", createProductRequest.getPdDescription());
        map.put("pdStatus", createProductRequest.getPdStatus());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
//        return ResponseEntity.status(200).body(ResultResponse);
    }

    @Override
    public List<ProductListResponse> getallProductList() {
        return null;  //還沒寫
    }
}
