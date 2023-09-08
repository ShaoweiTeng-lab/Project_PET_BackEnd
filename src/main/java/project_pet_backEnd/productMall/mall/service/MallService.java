package project_pet_backEnd.productMall.mall.service;


import project_pet_backEnd.productMall.mall.dto.GetAllMall;
import project_pet_backEnd.productMall.mall.dto.MallQueryParameter;
import project_pet_backEnd.productMall.mall.dto.ProductPage;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;


public interface MallService {

    /*
     * 列表
     */
    Page<List<GetAllMall>> getMallProducts(MallQueryParameter mallQueryParameter);


    ResultResponse<ProductPage> getProductPage(Integer pdNo);
}
