package project_pet_backEnd.productMall.productsmanage.dao;


import project_pet_backEnd.productMall.productsmanage.dto.AdjustProductListResponse;
import project_pet_backEnd.productMall.productsmanage.dto.ProductInfo;
import project_pet_backEnd.productMall.productsmanage.dto.ProductListQueryParameter;
import project_pet_backEnd.productMall.productsmanage.dto.ProductListResponse;


import java.util.List;

public interface ProductsManageDao {

    /**(改用JPA不用寫)
     * 條列顯示(取得)所有商品(dto(ProductListResponse):編號、名稱、價格、狀態)
     * @return 所有商品列表
     */
//    public static List<ProductListResponse> getAllProduct(Integer pdNo);

    /*
    * 根據條件獲取商品列表
    * @param productQueryParameter 查詢條件可以包含搜索關鍵字(
    * PD_NO LIKE :search OR PD_NAME LIKE :search OR PD_PRICE LIKE :search OR PD_STATUS LIKE :search
    * 排序方式(
    * pdNo 商品編號, pdName 商品名稱, pdStatus 商品上下架狀態 0:上架中  1:已下架、分頁等信息
    * @return 符合條件的商品列表
    */
    public List<ProductListResponse> getAllProductWithSearch(ProductListQueryParameter productListQueryParameter);

    //計算搜尋商品總筆數
    public Integer countAllProductWithSearch(ProductListQueryParameter productListQueryParameter);


    /*
     * 根據商品編號批次修改商品列表狀態
     * @param AdjustProductListResponse 要更新的商品狀態，其中pdNo指定要更新的商品編號，商品狀態指定要更新的值
     */
    public void batchupdateproductstatusByPdNo(List<AdjustProductListResponse> adjustProductListResponse);

    //(可改用JPA就不用寫)新增商品資訊
    void insertProductInfo(ProductInfo productInfo);
}
