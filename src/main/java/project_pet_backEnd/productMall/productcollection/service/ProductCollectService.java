package project_pet_backEnd.productMall.productcollection.service;


import project_pet_backEnd.productMall.productcollection.dto.EditProductCollect;
import project_pet_backEnd.productMall.productcollection.dto.ProductCollectList;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;


public interface ProductCollectService {
    // 新增商品收藏
    ResultResponse createProductCollect(EditProductCollect editProductCollect);

    // 刪除商品收藏
    ResultResponse deleteProductCollect(Integer userId, Integer pdNo);

    // 瀏覽商品收藏
    List<ProductCollectList> getAllCollect(Integer userId);

}
