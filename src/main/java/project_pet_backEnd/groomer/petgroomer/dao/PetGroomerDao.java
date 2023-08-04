package project_pet_backEnd.groomer.petgroomer.dao;

import project_pet_backEnd.groomer.petgroomer.dto.GetAllGroomers;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.response.ManagerGetByFunctionIdRes;
import project_pet_backEnd.groomer.petgroomer.vo.PetGroomer;

import java.util.List;

public interface PetGroomerDao {

    /**
     * 取得擁有美容師個人管理權限的管理員列表。
     * 進入美容師管理時自動查詢FUNCTION_ID=3的管理員(有美容師個人管理權限的管理員)
     * @param functionId 管理員功能ID
     * @return 擁有美容師個人管理權限的管理員列表
     */
    public List<ManagerGetByFunctionIdRes> getManagerByFunctionId(Integer functionId);

    /**
     * 供美容師管理員新增美容師。
     * @param petGroomer 美容師對象
     */
    public void insertGroomer(PetGroomer petGroomer);

    /**
     * 根據ManID取得對應的美容師。
     * @param manId 管理員ID
     * @return 美容師對象
     */
    public PetGroomer getPetGroomerByManId(Integer manId);

    /**
     * 取得所有美容師列表。
     * @return 所有美容師列表
     */
    public List<PetGroomer> getAllGroomer();

    /**
     * 列出所有美容師資料，並根據分頁查詢參數進行限制。
     * @param PGQueryParameter 分頁查詢參數
     * @return 美容師資料列表
     */
    public List<GetAllGroomers> getAllGroomersLimit(PGQueryParameter PGQueryParameter);

    /**
     * 根據ManID更新美容師資料。後台先使用getAllGroomer查找對應美容師後修改該美容師 by pgId
     * @param petGroomer 美容師對象
     */
    public void updateGroomerById(PetGroomer petGroomer);

    /**
     * 取得美容師總筆數，方便進行分頁查詢。
     * @param PGQueryParameter 分頁查詢參數
     * @return 美容師總筆數
     */
    public Integer countPetGroomer(PGQueryParameter PGQueryParameter);//取得筆數，方便分頁查詢


//    public List<PetGroomer> getGroomerByPgName(String petGroomerName);

}
