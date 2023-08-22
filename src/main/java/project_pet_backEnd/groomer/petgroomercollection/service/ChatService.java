package project_pet_backEnd.groomer.petgroomercollection.service;

import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.response.ChatRes;
import project_pet_backEnd.groomer.petgroomer.dto.response.GetAllGroomerListSortRes;
import project_pet_backEnd.groomer.petgroomercollection.vo.Chat;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

public interface ChatService {

    /**
     * 新增
     */
    ResultResponse insert(Chat rest);

    /**
     * 更新
     */
    ResultResponse update(Chat rest);

    /**
     * 删除
     */
    ResultResponse delete(Chat rest);

    /**
     * 取得美容師列表 for 管理員
     */
    Page<List<ChatRes>> list(PGQueryParameter PGQueryParameter);
}