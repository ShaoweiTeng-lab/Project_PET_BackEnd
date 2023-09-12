package project_pet_backEnd.productMall.productcollection.dao;


import project_pet_backEnd.productMall.productcollection.dto.ProductCollectList;

import java.util.List;


public interface ProductCollectDao {

    // 瀏覽商品收藏
    List<ProductCollectList> getAllCollect(Integer userId);

}
