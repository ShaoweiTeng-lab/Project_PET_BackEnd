package project_pet_backEnd.groomer.groomerleave.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.groomer.appointment.dao.GroomerAppointmentDao;
import project_pet_backEnd.groomer.appointment.vo.PetGroomerAppointment;
import project_pet_backEnd.groomer.groomerleave.dao.GroomerLeaveDao;
import project_pet_backEnd.groomer.groomerleave.dao.GroomerLeaveRepository;
import project_pet_backEnd.groomer.groomerleave.dto.request.InsertLeaveReq;
import project_pet_backEnd.groomer.groomerleave.dto.response.LeaveAllRes;
import project_pet_backEnd.groomer.groomerleave.dto.response.PGLeaveSearchRes;
import project_pet_backEnd.groomer.groomerleave.dto.request.ChangeLeaveReq;
import project_pet_backEnd.groomer.groomerleave.service.GroomerLeaveService;
import project_pet_backEnd.groomer.groomerleave.vo.GroomerLeave;
import project_pet_backEnd.groomer.petgroomer.dao.PetGroomerDao;
import project_pet_backEnd.groomer.petgroomer.vo.PetGroomer;
import project_pet_backEnd.groomer.petgroomerschedule.dao.PetGroomerScheduleDao;
import project_pet_backEnd.groomer.petgroomerschedule.dao.PetScheduleRepository;
import project_pet_backEnd.groomer.petgroomerschedule.vo.PetGroomerSchedule;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroomerLeaveServiceImp implements GroomerLeaveService {

    @Autowired
    GroomerLeaveRepository groomerLeaveRepository;

    @Autowired
    GroomerLeaveDao groomerLeaveDao;

    @Autowired
    PetGroomerScheduleDao petGroomerScheduleDao;

    @Autowired
    PetScheduleRepository petScheduleRepository;

    @Autowired
    GroomerAppointmentDao groomerAppointmentDao;

    @Autowired
    PetGroomerDao petGroomerDao;

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
            String state;
            switch (pgLeaveSearchRes.getLeaveState()){//審核狀態 0:未審核 1:審核通過 2:審核未通過
                case 0-> leaveAllRes.setLeaveState("未審核");
                case 1-> leaveAllRes.setLeaveState("審核通過");
                case 2-> leaveAllRes.setLeaveState("審核未通過");
            }
            return leaveAllRes;
        }).collect(Collectors.toList());

        ResultResponse<List<LeaveAllRes>> resultResponse = new ResultResponse<>();
        resultResponse.setMessage(convertedList);
        return resultResponse;
    }
    //審核請假單 (改變假單狀態) ，前端需提示修改預約單等
    @Transactional
    @Override
    public ResultResponse<String> changeLeave(ChangeLeaveReq changeLeaveReq) {
        GroomerLeave byLeaveNo = groomerLeaveRepository.findByLeaveNo(changeLeaveReq.getLeaveNo());
        if(byLeaveNo==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "無此請假單");
        if (byLeaveNo.getLeaveState().equals(1)){
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "此請假單狀態為審核通過，不可修改。");
        }

        //審核通過情況
        if(changeLeaveReq.getLeaveState().equals(1)){
            //查詢對應的預約單
            List<PetGroomerAppointment> appointmentList = groomerAppointmentDao.getAppointmentByPgIdAndDate(byLeaveNo.getPgId(), byLeaveNo.getLeaveDate());
            //查詢對應的當天班表
            PetGroomerSchedule pgSchedule = petGroomerScheduleDao.getPgScheduleByPgIdAndPgsDate(byLeaveNo.getPgId(), byLeaveNo.getLeaveDate());
            if(pgSchedule==null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "無對應班表，申請有誤!");

            //1.無預約單情況，直接改變班表(覆蓋班表)
            if(appointmentList==null){
                //覆蓋班表
                pgSchedule.setPgsState(byLeaveNo.getLeaveTime());
                petScheduleRepository.save(pgSchedule);
                //改變請假單狀態，覆蓋請假單狀態(審核通過)
                byLeaveNo.setLeaveState(1);
                groomerLeaveRepository.save(byLeaveNo);
                ResultResponse<String> rs=new ResultResponse<>();
                rs.setMessage("請假單審核完成!修改班表無已預約單，修改成功!");
                return rs;
            }else if(!appointmentList.isEmpty()){
                //2.有預約單情況，取消預約單
                char[] leaveTime = byLeaveNo.getLeaveTime().toCharArray();//24 休假時間
                for(PetGroomerAppointment appointment : appointmentList) {
                    //當預約單為
                    char[] appArray = appointment.getPgaTime().toCharArray();
                    if(appointment.getPgaState()==0){//預約單狀態 0:未完成(預約單尚未完成)
                        for(int i = 0; i < appArray.length; i++){
                            if(appArray[i]=='1'&& leaveTime[i]=='1'){//預約單'1'預約，並且假單時為不可預約'1'
                                appointment.setPgaState(2);
                                groomerAppointmentDao.updateAppointmentByPgaNo(appointment);
                            }

                        }
                    }
                }
                //假單狀態改為審核通過'1'
                byLeaveNo.setLeaveState(1);
                groomerLeaveRepository.save(byLeaveNo);
                //覆蓋班表
                pgSchedule.setPgsState(byLeaveNo.getLeaveTime());
                petScheduleRepository.save(pgSchedule);
                ResultResponse<String> rs=new ResultResponse<>();
                rs.setMessage("該請假單審核已通過!，當日班表已更新。 " +
                        "因該請假單編號:["+byLeaveNo.getLeaveNo()+"]當日已有預約單。" +
                        "如有未完成之預約單，會將將預約單進行取消。");
                //推播取消...<<
                return rs;
            }
        }else if(changeLeaveReq.getLeaveState().equals(2)){
            //審核不通過情況
            byLeaveNo.setLeaveState(2);
            groomerLeaveRepository.save(byLeaveNo);
            ResultResponse<String> resultResponse = new ResultResponse<>();
            resultResponse.setMessage("審核完成!，假單編號:("+changeLeaveReq.getLeaveNo()+")的審核為未通過。");
            return resultResponse;
        }
        return null;
    }

    @Override
    public ResultResponse<List<LeaveAllRes>> getLeaveForPg(Integer manId) {
        PetGroomer petGroomerByManId = petGroomerDao.getPetGroomerByManId(manId);
        if (petGroomerByManId==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "您非美容師，或已被停權!");

        List<PGLeaveSearchRes> all = groomerLeaveDao.getGroomerLeaveByPgId(petGroomerByManId.getPgId());
        if (all==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "您沒有任何請假單紀錄。");

        List<LeaveAllRes> convertedList = all.stream().map(pgLeaveSearchRes -> {
            LeaveAllRes leaveAllRes = new LeaveAllRes();
            leaveAllRes.setLeaveNo(pgLeaveSearchRes.getLeaveNo());
            leaveAllRes.setPgId(pgLeaveSearchRes.getPgId());
            leaveAllRes.setPgName(pgLeaveSearchRes.getPgName());
            leaveAllRes.setLeaveCreated(AllDogCatUtils.timestampToSqlDateFormat(pgLeaveSearchRes.getLeaveCreated()));
            leaveAllRes.setLeaveDate(AllDogCatUtils.timestampToSqlDateFormat(pgLeaveSearchRes.getLeaveDate()));
            leaveAllRes.setLeaveTime(pgLeaveSearchRes.getLeaveTime());
            String state;
            switch (pgLeaveSearchRes.getLeaveState()){//審核狀態 0:未審核 1:審核通過 2:審核未通過
                case 0-> leaveAllRes.setLeaveState("未審核");
                case 1-> leaveAllRes.setLeaveState("審核通過");
                case 2-> leaveAllRes.setLeaveState("審核未通過");
            }
            return leaveAllRes;
        }).collect(Collectors.toList());
        ResultResponse<List<LeaveAllRes>> resultResponse = new ResultResponse<>();
        resultResponse.setMessage(convertedList);
        return resultResponse;
    }

    @Override
    public ResultResponse<String> insertLeaveForPg(Integer manId, InsertLeaveReq insertLeaveReq) {
        PetGroomer petGroomerByManId = petGroomerDao.getPetGroomerByManId(manId);
        if (petGroomerByManId==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "您非美容師，或已被停權!");
        Date date;
        try {
            date = AllDogCatUtils.dateFormatToSqlDate(insertLeaveReq.getLeaveDate());
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "請假日期格式有誤!");
        }
        //查詢對應的當天班表
        PetGroomerSchedule pgSchedule = petGroomerScheduleDao.getPgScheduleByPgIdAndPgsDate(petGroomerByManId.getPgId(), date);
        if(pgSchedule==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "無對應當日班表，申請有誤!");

        GroomerLeave groomerLeave = new GroomerLeave();
        groomerLeave.setLeaveState(0);
        groomerLeave.setLeaveDate(date);
        groomerLeave.setLeaveTime(insertLeaveReq.getLeaveTime());//24 time String
        groomerLeave.setPgId(petGroomerByManId.getPgId());
        groomerLeaveRepository.save(groomerLeave);
        ResultResponse<String> resultResponse = new ResultResponse<>();
        resultResponse.setMessage("假單申請成功!");
        return resultResponse;
    }
}
