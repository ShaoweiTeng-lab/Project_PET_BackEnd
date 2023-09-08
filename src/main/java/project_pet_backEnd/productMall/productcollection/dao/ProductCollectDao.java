package project_pet_backEnd.productMall.productcollection.dao;


import project_pet_backEnd.productMall.productcollection.dto.EditProductCollect;
import project_pet_backEnd.productMall.productcollection.dto.ProductCollectList;

import java.util.List;


public interface ProductCollectDao {
    // 新增商品收藏
//    public void insertProductCollect(EditProductCollect editProductCollect);
//    public void insertProductCollect(Integer pdNo);

    // 刪除商品收藏
//    public void deleteProductCollect(EditProductCollect editProductCollect);
//    public void deleteProductCollect(Integer pdNo);

    // 瀏覽商品收藏
    public List<ProductCollectList> getAllCollect(Integer userId);

}
