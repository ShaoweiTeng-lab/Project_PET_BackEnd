package project_pet_backEnd.productMall.productsmanage.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import project_pet_backEnd.groomer.appointment.dto.response.PGAppointmentRes;
import project_pet_backEnd.productMall.productsmanage.dao.ProductPicDao;
import project_pet_backEnd.productMall.productsmanage.dao.ProductsManageDao;
import project_pet_backEnd.productMall.productsmanage.dto.AdjustProductListResponse;
import project_pet_backEnd.productMall.productsmanage.dto.ProductInfo;
import project_pet_backEnd.productMall.productsmanage.dto.ProductListQueryParameter;
import project_pet_backEnd.productMall.productsmanage.dto.ProductListResponse;
import project_pet_backEnd.productMall.productsmanage.vo.ProductPic;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductsManageDaoImp implements ProductsManageDao, ProductPicDao {
   @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

//    @Override  //ok商品列表列出全部商品
//    public List<ProductListResponse> getAllProduct(Integer pdNo) {
//        String sql ="SELECT PD_NO, PD_NAME, PD_PRICE, PD_STATUS "+
//                    "FROM PRODUCT " +
//                    "WHERE PD_NO =:pdNo" +
//                    "ORDER BY PD_NO"; // Default sorting by PD_NO
//
//        MapSqlParameterSource params = new MapSqlParameterSource();
//        params.addValue("pdNo", pdNo);
//
//        List<ProductListResponse> productList = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(ProductListResponse.class));
//        return productList;
//    }

    @Override //ok關鍵字搜尋、分頁搜尋
    public List<ProductListResponse> getAllProductWithSearch(ProductListQueryParameter productListQueryParameter) {
        String sql = "SELECT PD_NO, PD_NAME, PD_PRICE, PD_STATUS " +
                "FROM PRODUCT " +
                "WHERE 1=1 ";

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (productListQueryParameter.getSearch() != null) {
            sql += "AND (PD_NO LIKE :search OR PD_NAME LIKE :search OR PD_PRICE LIKE :search OR PD_STATUS LIKE :search ";
            params.addValue("search", "%" + productListQueryParameter.getSearch() + "%");
        }

        if (productListQueryParameter.getMinPrice() != null && productListQueryParameter.getMaxPrice() != null) {
        sql += "AND PD_PRICE BETWEEN :minPrice AND :maxPrice ";
        params.addValue("minPrice", productListQueryParameter.getMinPrice());
        params.addValue("maxPrice", productListQueryParameter.getMaxPrice());
    }

        // Sort  再加回case
        if (productListQueryParameter.getOrder() != null) {
            String orderBy;
            switch (productListQueryParameter.getOrder()) {
                case PD_NAME:
                    orderBy = "PD_NAME";
                    break;
                case PD_PRICE:
                    orderBy = "PD_PRICE";
                    break;
                case PD_STATUS:
                    orderBy = "PD_STATUS";
                    break;
                default:
                    orderBy = "PD_NO"; // Default sorting by PD_NO
            }
            sql += "ORDER BY " + orderBy + " ";
        }

        // Sort
        if (productListQueryParameter.getSort() != null) {
            sql += productListQueryParameter.getSort() + " ";
        }

        // Limit and Offset
        sql += "LIMIT :limit OFFSET :offset ";
        params.addValue("limit", productListQueryParameter.getLimit());
        params.addValue("offset", productListQueryParameter.getOffset());

        List<ProductListResponse> productList = namedParameterJdbcTemplate.query(sql, params, new RowMapper<ProductListResponse>() {
            @Override
            public ProductListResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                ProductListResponse productListResponse = new ProductListResponse();
                productListResponse.setPdNo(rs.getInt("PD_NO"));
                productListResponse.setPdName(rs.getString("PD_NAME"));
                productListResponse.setPdStatus(rs.getInt("PD_STATUS"));
                return  productListResponse;
            }
        });

        return productList;

    }

    @Override //ok計算搜尋商品總筆數
    public Integer countAllProductWithSearch(ProductListQueryParameter productListQueryParameter) {
        String sql = "SELECT COUNT(*) " +
                "FROM PRODUCT pd " +
                "WHERE 1=1 ";

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (productListQueryParameter.getSearch() != null) {
            sql += "AND (PD_NAME LIKE :search OR PD_NO LIKE :search OR PD_PRICE LIKE :search OR PD_STATUS LIKE :search) ";
        params.addValue("search", "%" + productListQueryParameter.getSearch() + "%");
        }

        Integer totalCount = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        return totalCount;

    }

    @Override   //ok(可改用JPA就不用寫)新增商品資訊
    public void insertProductInfo(ProductInfo productInfo) {
        String sql = "INSERT INTO PET_GROOMER_APPOINTMENT (PD_NAME, PD_PRICE, PD_STATUS, PD_DESCRIPTION) " +
                "VALUES (:pdName, :pdPrice, :pdStatus, :pdDescription)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pdName", productInfo.getPdName());
        params.addValue("pdPrice", productInfo.getPdPrice());
        params.addValue("pdStatus", productInfo.getPdStatus());
        params.addValue("pdDescription", productInfo.getPdDescription());

        namedParameterJdbcTemplate.update(sql, params);

    }

    @Override   //ok批次新增商品圖片
    public void batchinsertProductPic(List<ProductPic> pics){
        String sql ="INSERT INTO PRODUCT_PIC (PD_PIC, PD_NO) " +
                "VALUES(:pdPic, :pdNo)";
        Map<String, Object> [] maps =new HashMap[pics.size()];
        for(int i = 0; i < pics.size(); i++){
            ProductPic productPic = pics.get(i);
            maps[i] = new HashMap<>();
            maps[i].put("pdPic", productPic.getPdPic());
            maps[i].put("pdNo", productPic.getPdNo());
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.batchUpdate(sql, maps);

    }

    @Override //ok編輯商品顯示(獲取)商品圖片(pic)
    public List<ProductPic> getAllProductPic(Integer PdNo) {
        String sql ="SELECT FROM PRODUCT_PIC WHERE PD_NO = :pdNo";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pdNo", PdNo);

        List<ProductPic> pics = namedParameterJdbcTemplate.query(sql, params, new RowMapper<ProductPic>() {
            @Override
            public ProductPic mapRow(ResultSet rs, int rowNum) throws SQLException {
                ProductPic productPic = new ProductPic();
                productPic.setPdPicNo(rs.getInt("PD_PIC_NO"));

                return productPic;
            }
    });
    return pics;
}

    @Override  //ok批次修改狀態
    public void batchupdateproductstatusByPdNo(List<AdjustProductListResponse> adjustProductListResponse) {
        String sql = "UPDATE PRODUCT " +
                "SET PD_STATUS = :pdStatus " +
                "WHERE PD_NO = :pdNo";

        for (AdjustProductListResponse adjust: adjustProductListResponse) {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("pdStatus", adjust.getPdStatus());
            params.addValue("pdNo", adjust.getPdNo());

            namedParameterJdbcTemplate.update(sql, params);
        }
    }

    @Override   //ok批次修改商品圖片們
    public void batchupdateproductPicByPdNo(List<ProductPic> pics) {
        String sql = "UPDATE PRODUCT_PIC " +
                "SET PD_No = :pdNo, " +
                "PD_PIC = :pdPic, " +
                "WHERE PD_PIC_NO = :pdPicNo";

        SqlParameterSource[] batchParams = new SqlParameterSource[pics.size()];
        for (int i = 0; i < pics.size(); i++) {
            ProductPic productPic = pics.get(i);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pdNo", productPic.getPdNo());
        params.addValue("pdPic",productPic.getPdPic());
        params.addValue("pdPicNo", productPic.getPdPicNo());
            batchParams[i] = params;
        }

        namedParameterJdbcTemplate.batchUpdate(sql, batchParams);
    }

}
