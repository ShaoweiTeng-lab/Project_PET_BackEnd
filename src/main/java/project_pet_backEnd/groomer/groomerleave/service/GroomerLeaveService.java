package project_pet_backEnd.groomer.groomerleave.service;

import project_pet_backEnd.groomer.groomerleave.dto.request.ChangeLeaveReq;
import project_pet_backEnd.groomer.groomerleave.dto.response.LeaveAllRes;
import project_pet_backEnd.groomer.groomerleave.vo.GroomerLeave;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

public interface GroomerLeaveService {

    //dataTable?
    //查詢請假申請 for Man
    ResultResponse<List<LeaveAllRes>> getAllLeave();
    //審核假單 for Man
    ResultResponse<String> changeLeave(ChangeLeaveReq changeLeaveReq);

    //查看自己假單
    ResultResponse<List<GroomerLeave>> getLeaveByPgIdForPg(Integer manId);

    //新增假單
    ResultResponse<List<GroomerLeave>> insertLeaveForPg();

}
