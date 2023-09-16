package project_pet_backEnd.groomer.petgroomercollection.service;

import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.response.PortfolioCollectRes;
import project_pet_backEnd.groomer.petgroomer.dto.response.PortfolioRes;
import project_pet_backEnd.groomer.petgroomercollection.vo.PortfolioCollect;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

public interface PortfolioCollectService {

    /**
     * 新增
     */
    ResultResponse insert(PortfolioCollect rest);

    /**
     * 更新
     */
    ResultResponse update(PortfolioCollect rest);

    /**
     * 删除
     */
    ResultResponse delete(PortfolioCollect rest);

    /**
     * 查询
     */
    PortfolioCollectRes detail(PortfolioCollect rest);

    /**
     * 列表
     */
    Page<List<PortfolioCollectRes>> list(PGQueryParameter PGQueryParameter);
}

