package project_pet_backEnd.groomer.petgroomercollection.dao;

import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomercollection.vo.Portfolio;

import java.util.List;

public interface PortfolioDao {

    /**
     * 新增作品
     * @param rest 作品
     */
    void insert(Portfolio rest);

    /**
     * 更新作品
     * @param rest
     */
    void update(Portfolio rest);

    void delete(Portfolio rest);

    /**
     * 查询詳情
     * @param rest
     * @return
     */
    Portfolio findById(Portfolio rest);

    /**
     * 列出所有作品資料，並根據分頁查詢參數進行限制。
     * @param PGQueryParameter 分頁查詢參數
     * @return 作品資料列表
     */
    List<Portfolio> list(PGQueryParameter PGQueryParameter);

    /**
     * 統計數量
     * @param PGQueryParameter
     * @return
     */
    Integer count(PGQueryParameter PGQueryParameter);
}
