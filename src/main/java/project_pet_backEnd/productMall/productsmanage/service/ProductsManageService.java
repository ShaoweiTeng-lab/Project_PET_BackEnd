package project_pet_backEnd.productMall.productsmanage.service;


import org.springframework.transaction.annotation.Transactional;
import project_pet_backEnd.productMall.mall.dto.ProductPage;
import project_pet_backEnd.productMall.productsmanage.dao.ProductPicRepository;
import project_pet_backEnd.productMall.productsmanage.dto.*;
import project_pet_backEnd.productMall.productsmanage.vo.ProductPic;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;
import java.util.Map;

public interface ProductsManageService {

    //後台 查看全部商品列表
    // no,name查無商品 | price此區間無商品 | 修改成功
    List<ProductListResponse> getAllProductsForMan();

    //後台 商品列表查詢
    Page<List<ProductListResponse>> getAllProductsWithSearch(ProductListQueryParameter productListQueryParameter);

    //後台 修改單一商品列表狀態
    ResultResponse<String> updateoneProductStatus(AdjustProductListResponse adjustProductListResponse);


    //後台 修改商品列表狀態
    ResultResponse<String> updateProductStatus(List<AdjustProductListResponse> adjustProductListResponse);

    //後台 查看編輯商品(資訊(名稱、價錢、規格、狀態、說明)+圖片)
    ResultResponse<ProductRes> getProduct(Integer PdNo);

    //後台 修改編輯商品(資訊+圖片)
    // 更新成功 | *!=null
//    ResultResponse updateProduct(ProductPicData productPicData, List<ProductPic> pics);
    ResultResponse updateProduct(ProductUpdate productUpdate, List<ProductPic> pics);


    //後台 新增商品(資訊+圖片)
    ResultResponse insertProduct(ProductInfo productInfo, List<ProductPic> pics);


    //後台 編輯時新增商品圖片
    ResultResponse insertProductPic(Integer pdNo, ProductPic productPic);

    //後台 編輯時刪除商品圖片
    ResultResponse deleteProductPic(Integer pdPicNo);

    @Transactional
        //後台 編輯時更換商品圖片
    ResultResponse changeProductPic(Integer pdNo, Integer pdPicNo,Integer pdOrderList, ProductPic productPic);
}



