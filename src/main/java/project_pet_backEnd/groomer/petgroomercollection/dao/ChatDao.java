package project_pet_backEnd.groomer.petgroomercollection.dao;

import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomercollection.vo.Chat;
import project_pet_backEnd.groomer.petgroomercollection.vo.Portfolio;
import project_pet_backEnd.user.vo.User;

import java.util.List;

public interface ChatDao {

    /**
     * 新增聊天记录
     * @param rest
     */
    void insert(Chat rest);

    /**
     * 更新聊天记录
     * @param rest
     */
    void update(Chat rest);

    /**
     * 删除聊天记录
     * @param rest
     */
    void delete(Chat rest);

    /**
     * 聊天记录列表
     * @param PGQueryParameter
     * @return
     */
    List<Chat> list(PGQueryParameter PGQueryParameter);

    /**
     * 统计数量，方便進行分頁查詢。
     * @param PGQueryParameter 分頁查詢參數
     * @return
     */
    Integer count(PGQueryParameter PGQueryParameter);//取得筆數，方便分頁查詢

    User getUserById(Integer id);

}
