package project_pet_backEnd.groomer.petgroomercollection.service;

import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.response.ChatRes;
import project_pet_backEnd.groomer.petgroomer.dto.response.PortfolioRes;
import project_pet_backEnd.groomer.petgroomercollection.vo.Chat;
import project_pet_backEnd.groomer.petgroomercollection.vo.Portfolio;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PortfolioService {

    /**
     * 获取当前用户
     *
     * @param request
     * @return
     */
    Integer currentUser(HttpServletRequest request);

    /**
     * 新增
     */
    ResultResponse insert(Portfolio rest, HttpServletRequest request);

    /**
     * 更新
     */
    ResultResponse update(Portfolio rest, HttpServletRequest request);

    /**
     * 删除
     */
    ResultResponse delete(Portfolio rest, HttpServletRequest request);

    /**
     * 查询
     */
    PortfolioRes detail(Portfolio rest, HttpServletRequest request);

    /**
     * 列表
     */
    Page<List<PortfolioRes>> list(PGQueryParameter PGQueryParameter, HttpServletRequest request);
}
