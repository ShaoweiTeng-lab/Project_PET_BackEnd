package project_pet_backEnd.productMall.productcollection.service;


import org.springframework.data.domain.Pageable;
import project_pet_backEnd.productMall.productcollection.dto.EditProductCollect;
import project_pet_backEnd.productMall.productcollection.dto.ProductCollectList;
import project_pet_backEnd.productMall.productcollection.vo.ProductCollect;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;
import java.util.Map;

public interface ProductCollectService {
    // 新增商品收藏
//    ResultResponse insertProductCollect(EditProductCollect editProductCollect);
    ResultResponse createProductCollect(EditProductCollect editProductCollect);

    // 刪除商品收藏
//    ResultResponse deleteProductCollect(EditProductCollect editProductCollect);
    ResultResponse deleteProductCollect(Integer userId, Integer pdNo);

    // 瀏覽商品收藏
    List<Map<String, Object>> getAllCollect(Integer userId);


}
