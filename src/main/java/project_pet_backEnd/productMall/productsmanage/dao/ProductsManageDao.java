package project_pet_backEnd.productMall.productsmanage.dao;


import project_pet_backEnd.productMall.productsmanage.dto.AdjustProductListResponse;
import project_pet_backEnd.productMall.productsmanage.dto.ProductListQueryParameter;
import project_pet_backEnd.productMall.productsmanage.dto.ProductListResponse;
import project_pet_backEnd.productMall.productsmanage.vo.ProductPic;

import java.util.List;

public interface ProductsManageDao {

    /**
     * 條列顯示(取德)所有商品(dto(ProductListResponse):編號、名稱、價格、狀態)
     * @return 所有商品列表
     */
    public List<ProductListResponse> getAllProduct();

    /*[這邊還沒修改成商品]
    * 根據條件獲取商品列表
    * @param productQueryParameter 查詢條件可以包含搜索關鍵字(
    * u.USER_NAME LIKE :search OR a.USER_ID LIKE :search OR g.PG_ID LIKE :search OR
    * g.PG_NAME LIKE :search)、
    * 排序方式(
    * pdNo 商品編號, pdName 商品名稱, pdPrice 商品價錢, pdStatus 商品上下架狀態 0:上架中  1:已下架、分頁等信息
    * @return 符合條件的商品列表
    */
    public List<ProductListResponse> getAllProductWithSearch(ProductListQueryParameter productListQueryParameter);

    //計算搜尋預約單總筆數
    public Integer countAllProductWithSearch(ProductListQueryParameter productListQueryParameter);


    /*
     * 根據商品編號修改商品列表狀態
     * @param AdjustProductListResponse 要更新的商品狀態，其中pdNo指定要更新的商品編號，商品狀態指定要更新的值
     */
    public void updateproductstatusByPdNo(AdjustProductListResponse adjustProductListResponse);

    /*
    *顯示(獲取)商品圖片(pic)
     */
    public void insertProductPic(ProductPic prodictpic);

    /*
    *修改商品圖片
    */
    public void updateproductPicByPdNo(ProductPic prodictpic);
}
