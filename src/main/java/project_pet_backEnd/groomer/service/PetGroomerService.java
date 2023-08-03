package project_pet_backEnd.groomer.service;

import project_pet_backEnd.groomer.dto.GetAllGroomers;
import project_pet_backEnd.groomer.dto.PetGroomerInsertRequest;
import project_pet_backEnd.groomer.dto.PetGroomerQueryParameter;
import project_pet_backEnd.groomer.vo.PetGroomer;
import project_pet_backEnd.user.dto.ResultResponse;
import project_pet_backEnd.utils.commonDto.Page;

import java.util.List;

public interface PetGroomerService {
    /**
     * get管理員權限為美容師個人管理 的 管理員List 。給新增美容師使用 for 管理員
     */
    ResultResponse getManagerByFunctionId(Integer functionId);
    /**
     * 新增美容師 for 管理員
     */
    ResultResponse insertGroomer(PetGroomerInsertRequest petGroomerInsertRequest);
    /**
     * 取得美容師列表 by ManId for 管理員
     */
    ResultResponse getPetGroomerByManId(Integer manId);
    /**
     * w取得美容師列表 for 管理員
     */
    Page<List<GetAllGroomers>> getAllGroomersForMan(PetGroomerQueryParameter petGroomerQueryParameter);
    /**
     * w取得美容師列表 for User and guest
     */
    ResultResponse getAllGroomersForUser();
    /**
     * 修改美容師資料 by Id for 管理員
     */
    ResultResponse updateGroomerByIdForMan(PetGroomer petGroomer);
    /**
     * 取得美容師列表 By PgName for 管理員
     */

//    ResultResponse getGroomerByPgNameForMan(String PgName);

    /**
     * 取得美容師列表 By PgName for User
     */
//    ResultResponse getGroomerByPgNameForUser (String PgName);
}
