package project_pet_backEnd.groomer.dao;

import project_pet_backEnd.groomer.dto.ManagerGetByFunctionIdRequest;
import project_pet_backEnd.groomer.vo.PetGroomer;

import java.util.List;

public interface PetGroomerDao {


    public List<ManagerGetByFunctionIdRequest> getManagerByFunctionId(Integer functionId);
    //進入美容師管理時自動查詢FUNCTION_ID=3的管理員(有美容師個人管理權限的管理員)
    public void insertGroomer(PetGroomer petGroomer);//供美容師管理員 新增美容師

    public PetGroomer getPetGroomerByManId(Integer manId);//取得美容師by管理員Id，

    public List<PetGroomer> getAllGroomer();//列出所有資料

    public void updateGroomerById(PetGroomer petGroomer);//後台先使用getAllGroomer後修改美容師 by pgId

    public List<PetGroomer> getGroomerByPgName(String petGroomerName);


}
