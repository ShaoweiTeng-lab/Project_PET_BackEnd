package project_pet_backEnd.productMall.mall.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.productMall.mall.dao.MallDao;
import project_pet_backEnd.productMall.mall.dto.GetAllMall;
import project_pet_backEnd.productMall.mall.dto.MallQueryParameter;
import project_pet_backEnd.productMall.mall.dto.ProductPage;


import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MallDaoImp implements MallDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<GetAllMall> getMallProducts(MallQueryParameter mallQueryParameter) {
        String sql = "SELECT P.PD_NO, P.PD_NAME, P.PD_PRICE, PIC.PD_PIC "
                + "FROM PRODUCT P "
                + "LEFT JOIN PRODUCT_PIC PIC ON P.PD_NO = PIC.PD_NO AND PIC.PIC_ORDER = 1 ";  // 與 product_pic 表進行聯結，選擇第一張圖片

        Map<String, Object> map = new HashMap<>();
        sql += "WHERE P.PD_STATUS = 0 "; // 只選擇狀態為 0（上架）的商品

        if (mallQueryParameter.getSearch() != null) {
            sql += "AND P.PD_NAME LIKE :search ";
            map.put("search", "%" + mallQueryParameter.getSearch() + "%");
        }

        if (mallQueryParameter.getMinPrice() != null) {
            sql += "AND P.PD_PRICE >= :minPrice ";
            map.put("minPrice", mallQueryParameter.getMinPrice());
        }

        if (mallQueryParameter.getMaxPrice() != null) {
            sql += "AND P.PD_PRICE <= :maxPrice ";
            map.put("maxPrice", mallQueryParameter.getMaxPrice());
        }

        // Sort  再加回case
        if(mallQueryParameter.getOrder() != null){
            String orderBy;
            switch (mallQueryParameter.getOrder()) {
                case PD_NAME:
                    orderBy = "PD_NAME";
                    break;
                case PD_PRICE:
                    orderBy = "PD_PRICE";
                    break;
                default:
                    orderBy = "PD_NO"; // Default sorting by PD_NO
            }
            sql += "ORDER BY " + orderBy + " ";
        }

        // Sort
        if (mallQueryParameter.getSortField() != null) {
            String orderBy = mallQueryParameter.getSortField();
            if (mallQueryParameter.getSortOrder() != null) {
                orderBy += " " + mallQueryParameter.getSortOrder();
            }
            sql += "ORDER BY " + orderBy + " ";
        }


        // 分頁
        sql += "LIMIT :limit OFFSET :offset ";
        map.put("limit", mallQueryParameter.getLimit());
        map.put("offset", mallQueryParameter.getOffset());

        List<GetAllMall> list = namedParameterJdbcTemplate.query(sql, map, new RowMapper<GetAllMall>() {
            @Override
            public GetAllMall mapRow(ResultSet rs, int rowNum) throws SQLException {
                GetAllMall product = new GetAllMall();
                product.setPdNo(rs.getInt("PD_NO"));
                product.setPdName(rs.getString("PD_NAME"));
                product.setPdPrice(rs.getInt("PD_PRICE"));

                // 將 BLOB 數據轉換為 Base64 字符串
                Blob blob = rs.getBlob("PD_PIC");
                if (blob != null) {
                    byte[] blobData = blob.getBytes(1, (int) blob.length());
                    String base64Image = Base64.getEncoder().encodeToString(blobData);
                    product.setBase64Image(base64Image);
                }

                return product;
            }
        });

        return list;
    }


    @Override
    public Integer countMallProducts(MallQueryParameter mallQueryParameter) {
        String sql = "SELECT COUNT(*) AS total_count " +
                "FROM PRODUCT P " +
                "LEFT JOIN PRODUCT_PIC PIC ON P.PD_NO = PIC.PD_NO AND PIC.PIC_ORDER = 1 ";  // 與 product_pic 表進行聯結，選擇第一張圖片

        Map<String, Object> map = new HashMap<>();
        sql += "WHERE P.PD_STATUS = 0 "; // 只選擇狀態為 0（上架）的商品

        if (mallQueryParameter.getSearch() != null) {
            sql += "AND P.PD_NAME LIKE :search ";
            map.put("search", "%" + mallQueryParameter.getSearch() + "%");
        }

        if (mallQueryParameter.getMinPrice() != null) {
            sql += "AND P.PD_PRICE >= :minPrice ";
            map.put("minPrice", mallQueryParameter.getMinPrice());
        }

        if (mallQueryParameter.getMaxPrice() != null) {
            sql += "AND P.PD_PRICE <= :maxPrice ";
            map.put("maxPrice", mallQueryParameter.getMaxPrice());
        }

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("total_count");
            }
        });

        return total;
    }

//    @Override
//    public List<ProductPage> getOneProduct(ProductPage productPage) {
//        String sql = "SELECT P.PD_NO, P.PD_NAME, P.PD_PRICE, P.PD_DESCRIPTION, PIC.PD_PIC "
//                + "FROM PRODUCT P "
//                + "LEFT JOIN PRODUCT_PIC PIC ON P.PD_NO = PIC.PD_NO " // 與 product_pic 表進行聯結
//                + "WHERE P.PD_NO = :pdNo ";
//
//        SqlParameterSource parameters = new MapSqlParameterSource("pdNo", productPage.getPdNo());
//
//
//        List<ProductPage> pdpagelist = namedParameterJdbcTemplate.query(sql, parameters, new RowMapper<ProductPage>() {
//            @Override
//            public ProductPage mapRow(ResultSet rs, int rowNum) throws SQLException {
//                ProductPage oneProduct = new ProductPage();
//                oneProduct.setPdNo(rs.getInt("PD_NO"));
//                oneProduct.setPdName(rs.getString("PD_NAME"));
//                oneProduct.setPdPrice(rs.getInt("PD_PRICE"));
//                oneProduct.setPdDescription(rs.getString("PD_DESCRIPTION"));
//
////                 將 BLOB 數據轉換為 Base64 字符串
//                Blob blob = rs.getBlob("PD_PIC");
//                if (blob != null) {
//                        byte[] blobData = blob.getBytes(1, (int) blob.length());
//                        String base64Image = Base64.getEncoder().encodeToString(blobData);
//                        oneProduct.addBase64Image(base64Image);
//                    }
//
//
//                return oneProduct;
//            }
//        });
//
//        return pdpagelist;
//    }
}
