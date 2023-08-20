package project_pet_backEnd.groomer.groomerleave.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.groomer.appointment.dao.GroomerAppointmentDao;
import project_pet_backEnd.groomer.groomerleave.dao.GroomerLeaveDao;
import project_pet_backEnd.groomer.groomerleave.dao.GroomerLeaveRepository;
import project_pet_backEnd.groomer.groomerleave.dto.response.LeaveAllRes;
import project_pet_backEnd.groomer.groomerleave.dto.response.PGLeaveSearchRes;
import project_pet_backEnd.groomer.groomerleave.dto.request.ChangeLeaveReq;
import project_pet_backEnd.groomer.groomerleave.service.GroomerLeaveService;
import project_pet_backEnd.groomer.groomerleave.vo.GroomerLeave;
import project_pet_backEnd.groomer.petgroomerschedule.dao.PetScheduleRepository;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroomerLeaveServiceImp implements GroomerLeaveService {

    @Autowired
    GroomerLeaveRepository groomerLeaveRepository;

    @Autowired
    GroomerLeaveDao groomerLeaveDao;

    @Autowired
    PetScheduleRepository petScheduleRepository;

    @Autowired
    GroomerAppointmentDao groomerAppointmentDao;

    @Override
    public ResultResponse<List<LeaveAllRes>> getAllLeave() {
        List<PGLeaveSearchRes> all = groomerLeaveDao.getAllGroomerLeave();

        List<LeaveAllRes> convertedList = all.stream().map(pgLeaveSearchRes -> {
            LeaveAllRes leaveAllRes = new LeaveAllRes();
            leaveAllRes.setLeaveNo(pgLeaveSearchRes.getLeaveNo());
            leaveAllRes.setPgId(pgLeaveSearchRes.getPgId());
            leaveAllRes.setPgName(pgLeaveSearchRes.getPgName());
            leaveAllRes.setLeaveCreated(AllDogCatUtils.timestampToSqlDateFormat(pgLeaveSearchRes.getLeaveCreated()));
            leaveAllRes.setLeaveDate(AllDogCatUtils.timestampToSqlDateFormat(pgLeaveSearchRes.getLeaveDate()));
            leaveAllRes.setLeaveTime(pgLeaveSearchRes.getLeaveTime());
            leaveAllRes.setLeaveState(pgLeaveSearchRes.getLeaveState());
            return leaveAllRes;
        }).toList();

        ResultResponse<List<LeaveAllRes>> resultResponse = new ResultResponse<>();
        resultResponse.setMessage(convertedList);
        return resultResponse;
    }
    //
    @Override
    public ResultResponse<String> changeLeave(ChangeLeaveReq changeLeaveReq) {
        GroomerLeave byLeaveNo = groomerLeaveRepository.findByLeaveNo(changeLeaveReq.getLeaveNo());
        if(byLeaveNo==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "無此請假單");
        if(changeLeaveReq.getLeaveState().equals(1)){



        }


//        groomerLeaveRepository.save();
        return null;
    }

    @Override
    public ResultResponse<List<GroomerLeave>> getLeaveByPgIdForPg(Integer manId) {
        return null;
    }

    @Override
    public ResultResponse<List<GroomerLeave>> insertLeaveForPg() {
        return null;
    }
}
