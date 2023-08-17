package project_pet_backEnd.productMall.productsmanage.dao;


import project_pet_backEnd.productMall.productsmanage.dto.CreateProductInfoRequest;
import project_pet_backEnd.productMall.productsmanage.dto.ProductListResponse;
import project_pet_backEnd.productMall.productsmanage.vo.ProductPic;

import java.util.List;

public interface ProductsManageDao {

    void insertProduct(CreateProductInfoRequest createProductInfoRequest);

    /*
     * [新增商品]
     * @param createProductRequest 要新增的商品
     */
    public void insertProduct(CreateProductInfoRequest createProductInfoRequest, ProductPic productPic);

    /*
     * 根據商品編號 [編輯(更新)商品資訊]
     * @param editProductRequest 要編輯的商品資訊，pdNo指定要更新的商品編號，其他屬性指定要更新的值
     */


    /*
     * 根據條件獲取商品資訊
     * @param QueryproductInfoResponse 查詢條件可以包含搜索關鍵字(
     * 這裡要看一下怎麼寫!{u.USER_NAME LIKE :search OR a.USER_ID LIKE :search OR g.PG_ID LIKE :search OR
     * g.PG_NAME LIKE :search)、}
     * 排序方式(
     * PD_NO 商品編號,PD_NAME 商品名稱,PD_PRICE 商品價格,PD_STATUS 商品狀態狀態 0:上架 1:下架 分頁等信息
     * @return 符合條件的商品的列表
     */

    //計算搜尋預約單總筆數



    /*
     * 顯示所有商品(部分資訊)列表[查詢全部]
     * @return 所有商品列表(商品編號、商品名稱、商品價格、商品狀態)
     */
    public List<ProductListResponse> getallProductList();



}
