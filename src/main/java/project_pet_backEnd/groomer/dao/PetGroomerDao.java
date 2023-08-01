package project_pet_backEnd.groomer.dao;

import project_pet_backEnd.groomer.vo.ManagerGetByFunctionIdRequest;
import project_pet_backEnd.groomer.dto.PetGroomerInsertRequest;
import project_pet_backEnd.groomer.vo.PetGroomer;

import java.util.List;

public interface PetGroomerDao {


    public List<ManagerGetByFunctionIdRequest> getManagerByFunctionId(Integer functionId);
    //進入美容師管理時自動查詢FUNCTION_ID=3的管理員
    public void insert(PetGroomer petGroomer);//供美容師管理員 新增美容師

    public List<PetGroomer> getAllGroomer();//列出所有資料

    public void updateByPgIdOrPgName(Integer petGroomerId , String petGroomerName);//修改美容師 by Id or Name

    public void getGroomerByPgIdOrPgName(Integer petGroomerId , String petGroomerName);


}
