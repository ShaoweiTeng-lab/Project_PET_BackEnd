package project_pet_backEnd.groomer.petgroomercollection.dao;

import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomercollection.vo.PortfolioCollect;

import java.util.List;

public interface PortfolioCollectDao {

    /**
     * 新增作品收藏
     * @param rest
     */
    void insert(PortfolioCollect rest);

    /**
     * 更新作品收藏
     * @param rest
     */
    void update(PortfolioCollect rest);

    /**
     * 删除作品收藏
     * @param rest
     */
    void delete(PortfolioCollect rest);

    /**
     * 查詢詳情
     * @param rest
     * @return
     */
    PortfolioCollect findById(PortfolioCollect rest);

    /**
     * 作品收藏列表
     * @param PGQueryParameter
     * @return
     */
    List<PortfolioCollect> list(PGQueryParameter PGQueryParameter);

    /**
     * 統計數量
     * @param PGQueryParameter
     * @return
     */
    Integer count(PGQueryParameter PGQueryParameter);

}
