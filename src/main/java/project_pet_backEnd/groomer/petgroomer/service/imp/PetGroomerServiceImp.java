package project_pet_backEnd.groomer.petgroomer.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.groomer.petgroomer.dto.GetAllGroomers;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.response.GetAllGroomerListSortResForUser;
import project_pet_backEnd.groomer.petgroomer.dto.response.ManagerGetByFunctionIdRes;
import project_pet_backEnd.groomer.petgroomer.dto.response.GetAllGroomerListSortRes;
import project_pet_backEnd.groomer.petgroomer.vo.PetGroomer;
import project_pet_backEnd.groomer.petgroomer.dao.PetGroomerDao;
import project_pet_backEnd.groomer.petgroomer.dto.request.PGInsertReq;
import project_pet_backEnd.groomer.petgroomer.dto.request.GetAllGroomerListReq;
import project_pet_backEnd.groomer.petgroomer.service.PetGroomerService;
import project_pet_backEnd.utils.commonDto.ResultResponse;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.Page;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetGroomerServiceImp implements PetGroomerService {

    @Autowired
    PetGroomerDao petGroomerDao;

    /**
     * 獲取擁有美容師個人管理權限的管理員列表，供新增美容師使用。 for 管理員
     *
     * @param functionId 美容師個人管理功能的ID
     * @return rs 包含結果的回應對象
     * @throws ResponseStatusException 如果找不到擁有美容師個人管理權限之管理員，將拋出此異常
     */
    @Override
    public ResultResponse<List<ManagerGetByFunctionIdRes>> getManagerByFunctionId(Integer functionId) {
        ResultResponse<List<ManagerGetByFunctionIdRes>> rs = new ResultResponse<>();
        List<ManagerGetByFunctionIdRes> managerGetByFunctionIdResList = petGroomerDao.getManagerByFunctionId(functionId);
        if (managerGetByFunctionIdResList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "未找到擁有美容師個人管理權限之管理員，請至權限管理新增擁有美容師個人管理權限之管理員");
        }
        rs.setMessage(managerGetByFunctionIdResList);
        return rs;
    }

    /**
     * 新增美容師，for 管理員使用。
     *
     * @param pgInsertReq 美容師的新增請求對象
     * @return rs 包含結果的回應對象
     * @throws ResponseStatusException 如果新增失敗，將拋出此異常
     */
    @Override
    public ResultResponse<String> insertGroomer(PGInsertReq pgInsertReq) {

        List<PetGroomer> allGroomer = petGroomerDao.getAllGroomer();
        for (PetGroomer existingGroomer : allGroomer) {
            if (existingGroomer.getManId() == pgInsertReq.getManId()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "新增失敗，此管理員已是美容師(管理員ID重複)");
            }
        }
        PetGroomer petGroomer = new PetGroomer();
        if(pgInsertReq.getManId()!=null){
            petGroomer.setManId(pgInsertReq.getManId());
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "新增失敗，請提供管理員ID");
        }
        if(!pgInsertReq.getPgName().isBlank())
            petGroomer.setPgName(pgInsertReq.getPgName());
        if(pgInsertReq.getPgGender()!=null)
            petGroomer.setPgGender(pgInsertReq.getPgGender());
        if(pgInsertReq.getPgPic()!=null)
            petGroomer.setPgPic(pgInsertReq.getPgPic());
        if(!pgInsertReq.getPgEmail().isBlank())
            petGroomer.setPgEmail(pgInsertReq.getPgEmail());
        if(!pgInsertReq.getPgPh().isBlank())
            petGroomer.setPgPh(pgInsertReq.getPgPh());
        if(!pgInsertReq.getPgAddress().isBlank())
            petGroomer.setPgAddress(pgInsertReq.getPgAddress());
        if(pgInsertReq.getPgBirthday()!=null){
            petGroomer.setPgBirthday(pgInsertReq.getPgBirthday());
        }

        try {
            petGroomerDao.insertGroomer(petGroomer);
            ResultResponse<String> rs = new ResultResponse<>();
            rs.setMessage("新增美容師成功");
            return rs;
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "新增失敗，請稍後重試", e);
        }
    }

    /**
     * 取得美容師列表，for 管理員使用。
     *
     * @param PGQueryParameter 分頁查詢參數
     * @return page 包含分頁結果的對象
     * @throws ResponseStatusException 如果找不到美容師，將拋出此異常
     */
    @Override
    public Page<List<GetAllGroomerListSortRes>> getAllGroomersForMan(PGQueryParameter PGQueryParameter) {
        List<GetAllGroomers> allGroomersList = petGroomerDao.getAllGroomersWithSearch(PGQueryParameter);
        List<GetAllGroomerListSortRes> rsList = new ArrayList<>();
        if (allGroomersList == null || allGroomersList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "找不到寵物美容師");
        }
        for (GetAllGroomers groomers : allGroomersList) {
            GetAllGroomerListSortRes getAllGroomerListSortRes = new GetAllGroomerListSortRes();
            getAllGroomerListSortRes.setManId(groomers.getManId());
            getAllGroomerListSortRes.setPgId(groomers.getPgId());
            getAllGroomerListSortRes.setPgName(groomers.getPgName());
            int gender = groomers.getPgGender();
            switch (gender) {
                case 0:
                    getAllGroomerListSortRes.setPgGender("女");
                    break;
                case 1:
                    getAllGroomerListSortRes.setPgGender("男");
                    break;
            }
            getAllGroomerListSortRes.setPgPic(AllDogCatUtils.base64Encode(groomers.getPgPic()));
            getAllGroomerListSortRes.setPgEmail(groomers.getPgEmail());
            getAllGroomerListSortRes.setPgPh(groomers.getPgPh());
            getAllGroomerListSortRes.setPgAddress(groomers.getPgAddress());
            getAllGroomerListSortRes.setPgBirthday(AllDogCatUtils.timestampToSqlDateFormat(groomers.getPgBirthday()));
            getAllGroomerListSortRes.setNumAppointments(groomers.getNumAppointments());
            rsList.add(getAllGroomerListSortRes);
        }
        Page page = new Page<>();
        page.setLimit(PGQueryParameter.getLimit());
        page.setOffset(PGQueryParameter.getOffset());
        //得到總筆數，方便實作頁數
        Integer total = petGroomerDao.countPetGroomer(PGQueryParameter);
        page.setTotal(total);
        page.setRs(rsList);
        return page;
    }

    /**
     * 取得美容師列表，for 使用者&訪客使用。
     *
     * @param PGQueryParameter 分頁查詢參數
     * @return page 包含分頁結果的對象
     * @throws ResponseStatusException 如果找不到寵物美容師，將拋出此異常
     */
    @Override
    public Page<List<GetAllGroomerListSortResForUser>> getAllGroomersForUser(PGQueryParameter PGQueryParameter) {
        List<GetAllGroomers> allGroomersList = petGroomerDao.getAllGroomersWithSearch(PGQueryParameter);
        List<GetAllGroomerListSortResForUser> rsList = new ArrayList<>();
        if (allGroomersList .isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "找不到寵物美容師");
        }
        for (GetAllGroomers groomers : allGroomersList) {
            GetAllGroomerListSortResForUser getAllGroomerListSortResForUser = new GetAllGroomerListSortResForUser();
            getAllGroomerListSortResForUser.setPgId(groomers.getPgId());
            getAllGroomerListSortResForUser.setPgName(groomers.getPgName());
            int gender = groomers.getPgGender();
            switch (gender) {
                case 0:
                    getAllGroomerListSortResForUser.setPgGender("女性");
                    break;
                case 1:
                    getAllGroomerListSortResForUser.setPgGender("男性");
                    break;
            }
            getAllGroomerListSortResForUser.setPgPic(AllDogCatUtils.base64Encode(groomers.getPgPic()));
            getAllGroomerListSortResForUser.setNumAppointments(groomers.getNumAppointments());
            rsList.add(getAllGroomerListSortResForUser);
        }
        Page page = new Page<>();
        page.setLimit(PGQueryParameter.getLimit());
        page.setOffset(PGQueryParameter.getOffset());
        //得到總筆數，方便實作頁數
        Integer total = petGroomerDao.countPetGroomer(PGQueryParameter);
        page.setTotal(total);
        page.setRs(rsList);
        return page;
    }

    /**
     * 修改美容師資料，for 管理員使用。
     *
     * @param getAllGroomerListReq 包含美容師信息的對象
     * @return rs 包含結果的回應對象
     * @throws ResponseStatusException 如果找不到指定ID的美容師，將拋出此異常
     */
    @Override
    public ResultResponse<String> updateGroomerByIdForMan(GetAllGroomerListReq getAllGroomerListReq) {
        ResultResponse<String> rs = new ResultResponse<>();
        try {
            // 檢查是否存在該美容師
            PetGroomer existingGroomer = petGroomerDao.getPetGroomerByManId(getAllGroomerListReq.getManId());

            if (getAllGroomerListReq.getPgId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "美容師ID尚未輸入");
            }
            if (getAllGroomerListReq.getManId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "管理員ID尚未輸入");
            }

            if(existingGroomer==null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到對應的美容師");
            }

            if (!getAllGroomerListReq.getManId().equals(existingGroomer.getManId()) ||
                    !getAllGroomerListReq.getPgId().equals(existingGroomer.getPgId())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到對應的美容師");
            }

            // 更新美容師信息

            if (!getAllGroomerListReq.getPgName().isBlank()) {
                existingGroomer.setPgName(getAllGroomerListReq.getPgName());
            }
            if (getAllGroomerListReq.getPgGender() != null) {
                existingGroomer.setPgGender(getAllGroomerListReq.getPgGender());
            }
            if (getAllGroomerListReq.getPgPic() != null) {
                existingGroomer.setPgPic(getAllGroomerListReq.getPgPic());
            }
            if (!getAllGroomerListReq.getPgEmail().isBlank()) {
                existingGroomer.setPgEmail(getAllGroomerListReq.getPgEmail());
            }
            if (!getAllGroomerListReq.getPgPh().isBlank()) {
                existingGroomer.setPgPh(getAllGroomerListReq.getPgPh());
            }
            if (!getAllGroomerListReq.getPgAddress().isBlank()) {
                existingGroomer.setPgAddress(getAllGroomerListReq.getPgAddress());
            }
            if (getAllGroomerListReq.getPgBirthday() != null) {
                existingGroomer.setPgBirthday(getAllGroomerListReq.getPgBirthday());
            }

            petGroomerDao.updateGroomerByPgId(existingGroomer);
            rs.setMessage("美容師信息更新成功");
            return rs;
        } catch (DataAccessException e) {
            // 出現異常，可以拋出異常或返回錯誤提示信息
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "更新美容師信息失敗，請稍後重試", e);
        }
    }

    @Override
    public ResultResponse<GetAllGroomerListSortRes> getPgInfoByManIdForPg(Integer manId) {
        GetAllGroomers groomer = petGroomerDao.getGroomerByManId(manId);
        if(groomer == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "您尚未被新增為美容師。請通知管理員新增。");

        GetAllGroomerListSortRes resPg = new GetAllGroomerListSortRes();
        resPg.setPgId(groomer.getPgId());
        resPg.setManId(groomer.getManId());
        resPg.setPgName(groomer.getPgName());
        switch (groomer.getPgGender()) {
            case 0 -> resPg.setPgGender("女性");
            case 1 -> resPg.setPgGender("男性");
        }
        resPg.setPgPic(AllDogCatUtils.base64Encode(groomer.getPgPic()));
        resPg.setPgEmail(groomer.getPgEmail());
        resPg.setPgPh(groomer.getPgPh());
        resPg.setPgAddress(groomer.getPgAddress());
        resPg.setPgBirthday(AllDogCatUtils.timestampToSqlDateFormat(groomer.getPgBirthday()));
        resPg.setNumAppointments(groomer.getNumAppointments());
        ResultResponse<GetAllGroomerListSortRes> resultResponse = new ResultResponse<>();
        resultResponse.setMessage(resPg);
        return resultResponse;
    }
}

