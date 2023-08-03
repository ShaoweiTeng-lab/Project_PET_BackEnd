package project_pet_backEnd.groomer.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.groomer.dao.PetGroomerDao;
import project_pet_backEnd.groomer.dto.*;
import project_pet_backEnd.groomer.service.PetGroomerService;
import project_pet_backEnd.groomer.vo.PetGroomer;
import project_pet_backEnd.user.dto.ResultResponse;
import project_pet_backEnd.utils.AllDogCatUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetGroomerServiceImp implements PetGroomerService {

    @Autowired
    PetGroomerDao petGroomerDao;

    /**
     * get管理員權限為美容師個人管理 的 管理員List 。給新增美容師使用 for 管理員
     */
    public ResultResponse getManagerByFunctionId(Integer functionId) {
        ResultResponse rs = new ResultResponse();
        List<ManagerGetByFunctionIdRequest> managerGetByFunctionIdRequestList = petGroomerDao.getManagerByFunctionId(functionId);
        if (managerGetByFunctionIdRequestList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "未找到擁有美容師個人管理權限之管理員，請至權限管理新增擁有美容師個人管理權限之管理員");
        }
        rs.setMessage(managerGetByFunctionIdRequestList);
        return rs;
    }

    /**
     * 新增美容師 for 管理員
     */
    public ResultResponse insertGroomer(PetGroomerInsertRequest petGroomerInsertRequest, PetGroomerQueryParameter petGroomerQueryParameter) {

        List<GetAllGroomers> allGroomer = petGroomerDao.getAllGroomers(petGroomerQueryParameter);
        for (GetAllGroomers existingGroomer : allGroomer) {
            if (existingGroomer.getManId() == petGroomerInsertRequest.getManId()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "新增失敗，管理員ID重複");
            }
        }
        PetGroomer petGroomer = new PetGroomer();
        petGroomer.setManId(petGroomerInsertRequest.getManId());
        petGroomer.setPgName(petGroomerInsertRequest.getPgName());
        petGroomer.setPgGender(petGroomerInsertRequest.getPgGender());
        petGroomer.setPgPic(AllDogCatUtils.base64Decode(petGroomerInsertRequest.getPgPic()));
        petGroomer.setPgEmail(petGroomerInsertRequest.getPgEmail());
        petGroomer.setPgPh(petGroomerInsertRequest.getPgPh());
        petGroomer.setPgAddress(petGroomerInsertRequest.getPgAddress());
        petGroomer.setPgBirthday(petGroomerInsertRequest.getPgBirthday());

        try {
            petGroomerDao.insertGroomer(petGroomer);
            ResultResponse rs = new ResultResponse();
            rs.setMessage("新增美容師成功");
            return rs;
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "新增失敗，請稍後重試", e);
        }
    }

    /**
     * 取得美容師列表 by ManId for 管理員
     */
    public ResultResponse getPetGroomerByManId(Integer manId) {
        ResultResponse rs = new ResultResponse();
        PetGroomer petGroomer = petGroomerDao.getPetGroomerByManId(manId);
        if (petGroomer == null) {
            // 沒有找到對應美容師
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "查無此寵物美容師");
        }
        rs.setMessage(petGroomer);
        return rs;
    }

    /**
     * 取得美容師列表 for 管理員
     */
    public ResultResponse getAllGroomerForMan() {
        ResultResponse rs = new ResultResponse();
        List<PetGroomer> allGroomer;
        List<PetGroomerInsertRequest> PetGroomerInsertRequestList = new ArrayList<>();
        try {
            allGroomer = petGroomerDao.getAllGroomer();

        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "獲取寵物美容師列表失敗，請稍後重試", e);
        }

        if (allGroomer == null || allGroomer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "目前無美容師資料");
        }

        for (PetGroomer existingGroomer : allGroomer) {
            PetGroomerInsertRequest petGroomerInsertRequest = new PetGroomerInsertRequest();
            petGroomerInsertRequest.setManId(existingGroomer.getManId());
            petGroomerInsertRequest.setPgName(existingGroomer.getPgName());
            petGroomerInsertRequest.setPgGender(existingGroomer.getPgGender());
            petGroomerInsertRequest.setPgPic(AllDogCatUtils.base64Encode(existingGroomer.getPgPic()));
            petGroomerInsertRequest.setPgEmail(existingGroomer.getPgEmail());
            petGroomerInsertRequest.setPgPh(existingGroomer.getPgPh());
            petGroomerInsertRequest.setPgAddress(existingGroomer.getPgAddress());
            petGroomerInsertRequest.setPgBirthday(existingGroomer.getPgBirthday());
            PetGroomerInsertRequestList.add(petGroomerInsertRequest);
        }

        rs.setMessage(PetGroomerInsertRequestList);
        return rs;
    }

    /**
     * 取得美容師列表 for User and guest
     */
    public ResultResponse getAllGroomerForUser() {
        ResultResponse rs = new ResultResponse();
        List<PetGroomer> allGroomer;
        List<GetAllGroomerResponse> allGroomerResponses = new ArrayList<>(); // Create a new list for GetAllGroomerResponse

        try {
            allGroomer = petGroomerDao.getAllGroomer();
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "獲取寵物美容師列表失敗，請稍後重試", e);
        }

        if (allGroomer == null || allGroomer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "目前無美容師資料");
        }

        for (PetGroomer petGroomer : allGroomer) {
            GetAllGroomerResponse response = new GetAllGroomerResponse();
            response.setPgName(petGroomer.getPgName());

            String base64Pic = AllDogCatUtils.base64Encode(petGroomer.getPgPic());
            response.setPgPic(base64Pic);

            allGroomerResponses.add(response);
        }

        rs.setMessage(allGroomerResponses);
        return rs;
    }

    /**
     * 修改美容師資料 by Id for 管理員
     */
    public ResultResponse updateGroomerByIdForMan(PetGroomer petGroomer) {
        ResultResponse rs = new ResultResponse();
        boolean found = false;
        try {
            // 檢查是否存在該美容師
            List<PetGroomer> allGroomer = petGroomerDao.getAllGroomer();
            for (PetGroomer existingGroomer : allGroomer) {
                if (existingGroomer.getPgId() == petGroomer.getPgId()) {

                    petGroomerDao.updateGroomerById(petGroomer);
                    rs.setMessage("美容師信息更新成功");
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到ID為" + petGroomer.getPgId() + "的美容師");
            }
            return rs;
        } catch (DataAccessException e) {
            // 出現異常，可以拋出異常或返回錯誤提示信息
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "更新美容師信息失敗，請稍後重試", e);
        }
    }

    /**
     * 取得美容師列表 By PgName for 管理員
     */
    public ResultResponse getGroomerByPgNameForMan(String PgName) {
        ResultResponse rs = new ResultResponse();
        try {
            List<PetGroomer> groomerByPgNameList = petGroomerDao.getGroomerByPgName(PgName);
            if (groomerByPgNameList.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到符合條件的美容師");
            } else {
                rs.setMessage(groomerByPgNameList);
            }
        } catch (DataAccessException e) {
            // If there's an exception, set an error status code (e.g., 500 for Internal Server Error)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "查詢美容師信息失敗，請稍後重試", e);
        }
        return rs;
    }
    /**
     * 取得美容師列表 By PgName for User
     */
    public ResultResponse getGroomerByPgNameForUser(String PgName) {
        ResultResponse rs = new ResultResponse();
        List<GetAllGroomerResponse> allGroomerResponses = new ArrayList<>();
        try {
            List<PetGroomer> groomerByPgNameList = petGroomerDao.getGroomerByPgName(PgName);

            if (groomerByPgNameList.isEmpty()||groomerByPgNameList==null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到符合條件的美容師");
            }
            for(PetGroomer petGroomer:groomerByPgNameList){
                GetAllGroomerResponse getAllGroomerResponse = new GetAllGroomerResponse();
                getAllGroomerResponse.setPgName(petGroomer.getPgName());
                getAllGroomerResponse.setPgPic(AllDogCatUtils.base64Encode(petGroomer.getPgPic()));
                allGroomerResponses.add(getAllGroomerResponse);
            }
            rs.setMessage(allGroomerResponses);

        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "查詢美容師信息失敗，請稍後重試", e);
        }
        return rs;
    }
}

