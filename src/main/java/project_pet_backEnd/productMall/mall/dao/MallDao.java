package project_pet_backEnd.productMall.mall.dao;


import project_pet_backEnd.productMall.mall.dto.GetAllMall;
import project_pet_backEnd.productMall.mall.dto.MallQueryParameter;
import project_pet_backEnd.productMall.mall.dto.ProductPage;


import java.util.List;

public interface MallDao {

    /*
     * 列出所有商品資料，並根據分頁查詢參數進行限制。
     * @param  MallQueryParameter分頁查詢參數
     * @return 所有商品
     */
    List<GetAllMall> getMallProducts(MallQueryParameter mallQueryParameter);

    /*
     * 統計數量
     * @param MallQueryParameter
     * @return
     */
    Integer countMallProducts(MallQueryParameter mallQueryParameter);


}
