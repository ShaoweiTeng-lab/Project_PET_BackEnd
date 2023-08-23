package project_pet_backEnd.groomer.groomerworkmanager.service;

import project_pet_backEnd.groomer.groomerworkmanager.vo.PictureInfo;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.response.PictureInfoRes;
import project_pet_backEnd.groomer.petgroomer.dto.response.PortfolioCollectRes;
import project_pet_backEnd.groomer.petgroomercollection.vo.PortfolioCollect;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

public interface PictureInfoService {

    /**
     * 新增
     */
    ResultResponse insert(PictureInfo rest);

    /**
     * 更新
     */
    ResultResponse update(PictureInfo rest);

    /**
     * 删除
     */
    ResultResponse delete(PictureInfo rest);

    /**
     * 查询
     */
    PictureInfoRes findById(PictureInfo rest);

    /**
     * 列表
     */
    Page<List<PictureInfoRes>> list(PGQueryParameter PGQueryParameter);
}
