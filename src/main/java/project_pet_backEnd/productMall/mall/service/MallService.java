package project_pet_backEnd.productMall.mall.service;


import project_pet_backEnd.productMall.mall.dto.GetAllMall;
import project_pet_backEnd.productMall.mall.dto.MallQueryParameter;
import project_pet_backEnd.utils.commonDto.Page;

import java.util.List;
import java.util.Map;

public interface MallService {

    /*
     * 列表
     */
    Page<List<Map<String, Object>>> list(MallQueryParameter mallQueryParameter);
}
