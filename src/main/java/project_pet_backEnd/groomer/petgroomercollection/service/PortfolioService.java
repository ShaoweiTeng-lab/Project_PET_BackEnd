package project_pet_backEnd.groomer.petgroomercollection.service;

import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.response.PortfolioRes;
import project_pet_backEnd.groomer.petgroomercollection.vo.Portfolio;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

public interface PortfolioService {

    /**
     * 新增
     */
    ResultResponse insert(Portfolio rest);

    /**
     * 更新
     */
    ResultResponse update(Portfolio rest);

    /**
     * 删除
     */
    ResultResponse delete(Portfolio rest);

    /**
     * 查询
     */
    PortfolioRes findById(Portfolio rest);

    /**
     * 列表
     */
    Page<List<PortfolioRes>> list(PGQueryParameter PGQueryParameter);
}
