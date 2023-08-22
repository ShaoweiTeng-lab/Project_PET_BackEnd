package project_pet_backEnd.productMall.productsmanage.dao.imp;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import project_pet_backEnd.productMall.productsmanage.dao.ProductsManageDao;
import project_pet_backEnd.productMall.productsmanage.dto.AdjustProductListResponse;
import project_pet_backEnd.productMall.productsmanage.dto.ProductInfo;
import project_pet_backEnd.productMall.productsmanage.dto.ProductListQueryParameter;
import project_pet_backEnd.productMall.productsmanage.dto.ProductListResponse;
import project_pet_backEnd.productMall.productsmanage.vo.ProductPic;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static project_pet_backEnd.productMall.productsmanage.dto.ProductOrderBy.pdNo;


@Repository
public class ProductsManageDaoImp implements ProductsManageDao {
   @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<ProductListResponse> getAllProduct() {
        String sql ="SELECT PD_NO, PD_NAME, PD_PRICE, PD_STATUS "+
                    "FROM PRODUCT " +
                    "WHERE PD_NO =:pdNo" +
                    "ORDER BY PD_NO"; // Default sorting by PD_NO

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pdNo", pdNo);

        List<ProductListResponse> ProductList = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(ProductListResponse.class));
        return ProductList;
    }

    @Override //關鍵字搜尋要再找資料看
    public List<ProductListResponse> getAllProductWithSearch(ProductListQueryParameter productListQueryParameter) {
//        String sql = "SELECT a.PGA_NO, a.PG_ID, a.USER_ID, a.PGA_DATE, a.PGA_TIME, a.PGA_STATE, a.PGA_OPTION, a.PGA_NOTES, a.PGA_PHONE, g.PG_NAME, u.USER_NAME " +
//                "FROM PET_GROOMER_APPOINTMENT a " +
//                "LEFT JOIN PET_GROOMER g ON a.PG_ID = g.PG_ID " +
//                "LEFT JOIN USER u ON a.USER_ID = u.USER_ID " +
//                "WHERE 1=1 ";
//
//        MapSqlParameterSource params = new MapSqlParameterSource();
//
//        if (groomerAppointmentQueryParameter.getSearch() != null) {
//            sql += "AND (u.USER_NAME LIKE :search OR a.USER_ID LIKE :search OR g.PG_ID LIKE :search OR g.PG_NAME LIKE :search " +
//                    "OR a.PGA_NO LIKE :search OR a.PGA_DATE LIKE :search OR a.PGA_STATE LIKE :search) ";
//            params.addValue("search", "%" + groomerAppointmentQueryParameter.getSearch() + "%");
//        }

        return null;
    }

    @Override //分頁搜尋要再找資料看
    public Integer countAllProductWithSearch(ProductListQueryParameter productListQueryParameter) {
        String sql = "SELECT COUNT(*) " +
                "FROM PRODUCT pd " +
                "WHERE 1=1 ";

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (productListQueryParameter.getSearch() != null) {
            sql += "AND (PD_NAME LIKE :search OR PD_NO LIKE :search OR PD_PRICE LIKE :search OR PD_STATUS LIKE :search) ";
        params.addValue("search", "%" + productListQueryParameter.getSearch() + "%");
        }
//
          //這裡開始看
         /* 這裡是飛飛的 */
//        Integer totalCount = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
//        return totalCount;
//        ------------------------------------------------------------------------------------------------
//       /* 這裡是chatGPT的 */
//        if (productListQueryParameter.getSearch() != null) {
//        sql += "AND (PD_NAME LIKE :search OR PD_NO = :exactSearch OR PD_PRICE = :exactSearch OR PD_STATUS = :exactSearch) ";
//        params.addValue("search", "%" + productListQueryParameter.getSearch() + "%");
//        try {
//            Integer searchAsInt = Integer.parseInt(productListQueryParameter.getSearch());
//            params.addValue("exactSearch", searchAsInt);
//        } catch (NumberFormatException e) {
//            // Handle the case where search cannot be parsed as an integer
//            params.addValue("exactSearch", -1); // Using a value that won't match any record
//        }
//    }
//
//       // Execute the query and return the result count
//    return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
//}
        return  null;
    }

    @Override
    public void updateproductstatusByPdNo(AdjustProductListResponse adjustProductListResponse) {
        String sql = "UPDATE PRODUCT " +
                "SET PD_STATUS = :pdStatus, " +
                "WHERE PD_NO = :pdNo";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pdNo",adjustProductListResponse.getPdNo());
        params.addValue("pdStatus", adjustProductListResponse.getPdStatus());

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override   //FK PdNo
    public void insertProductPic(ProductPic prodictpic) {
        String sql = "INSERT INTO PRODUCT_PIC (PD_PIC) " +
                "VALUES(:pdPic)";

        Map<String, Object> map = new HashMap<>();
        map.put("pdPic", prodictpic.getPdPic());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
    }

    @Override   //FK PdNo
    public void updateproductPicByPdNo(ProductPic prodictpic) {
        String sql = "UPDATE PRODUCT_PIC " +
                "SET PD_PIC = :pdPic, " +
                "WHERE PD_PIC_NO = :pdPicNo";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pdPic",prodictpic.getPdPic());
        params.addValue("pdPicNo", prodictpic.getPdPicNo());

        namedParameterJdbcTemplate.update(sql, params);
    }


    public void insertProductInfo(ProductInfo productInfo) {
        String sql = "INSERT INTO PET_GROOMER_APPOINTMENT (PD_NAME, PD_PRICE, PD_FORMAT, PD_STATUS, PD_DESCRIPTION) " +
                "VALUES (:pdName, :pdPrice, :pdFormat, :pdStatus, :pdDescription)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pdName", productInfo.getPdName());
        params.addValue("pdPrice", productInfo.getPdPrice());
        params.addValue("pdFormat", productInfo.getPdFormat());
        params.addValue("pdStatus", productInfo.getPdStatus());
        params.addValue("pdDescription", productInfo.getPdDescription());

        namedParameterJdbcTemplate.update(sql, params);

    }

}
