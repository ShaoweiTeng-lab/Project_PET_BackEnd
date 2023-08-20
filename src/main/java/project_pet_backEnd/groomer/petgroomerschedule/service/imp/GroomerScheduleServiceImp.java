package project_pet_backEnd.groomer.petgroomerschedule.service.imp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.groomer.appointment.dao.GroomerAppointmentDao;
import project_pet_backEnd.groomer.appointment.vo.PetGroomerAppointment;
import project_pet_backEnd.groomer.petgroomer.dao.PetGroomerDao;
import project_pet_backEnd.groomer.petgroomer.vo.PetGroomer;
import project_pet_backEnd.groomer.petgroomerschedule.dao.PetScheduleRepository;
import project_pet_backEnd.groomer.petgroomerschedule.dto.request.ScheduleModifyReq;
import project_pet_backEnd.groomer.petgroomerschedule.dto.response.GetScheduleRes;
import project_pet_backEnd.groomer.petgroomerschedule.dto.response.ListForScheduleRes;
import project_pet_backEnd.groomer.petgroomerschedule.service.GroomerScheduleService;
import project_pet_backEnd.groomer.petgroomerschedule.vo.PetGroomerSchedule;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroomerScheduleServiceImp implements GroomerScheduleService {
    @Autowired
    PetScheduleRepository petScheduleRepository;

    @Autowired
    GroomerAppointmentDao groomerAppointmentDao;

    @Autowired
    PetGroomerDao petGroomerDao;

    //取得美容師列表供選擇查看班表
    @Override
    public ResultResponse<List<ListForScheduleRes>> getAllGroomerForSchedule() {
        List<PetGroomer> allGroomer = petGroomerDao.getAllGroomer();
        if(allGroomer==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "未找到任一美容師");

        List<ListForScheduleRes> getAllGroomerList = new ArrayList<>();

        for(PetGroomer groomer : allGroomer){
            ListForScheduleRes resList = new ListForScheduleRes();

            resList.setPgId(groomer.getPgId());
            resList.setPgName(groomer.getPgName());
            resList.setPgPic(AllDogCatUtils.base64Encode(groomer.getPgPic()));
            getAllGroomerList.add(resList);
        }
        ResultResponse<List<ListForScheduleRes>> resultResponse = new ResultResponse<>();
        resultResponse.setMessage(getAllGroomerList);
        return resultResponse;
    }
    //取得該美容師該月的班表回傳。
    @Override
    public ResultResponse<List<GetScheduleRes>>getMonthScheduleForMan(Integer year, Integer pgId, Integer month) {
        List<String> dataByYearMonthAndPgId = petScheduleRepository.findDataByYearMonthAndPgId(year, month, pgId);

        if(dataByYearMonthAndPgId.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "未找到該美容師之班表");
        }
        List<GetScheduleRes> scheduleResList = new ArrayList<>();
        for (String data : dataByYearMonthAndPgId) {
            String[] parts = data.split(",");

            if (parts.length == 5) {
                GetScheduleRes scheduleRes = new GetScheduleRes();
                scheduleRes.setPgsId(Integer.parseInt(parts[0]));
                scheduleRes.setPgName(parts[1]);
                scheduleRes.setPgId(Integer.parseInt(parts[2]));
                scheduleRes.setPgsDate(parts[3]);
                scheduleRes.setPgsState(parts[4]);

                scheduleResList.add(scheduleRes);
            }
        }
        ResultResponse<List<GetScheduleRes>> resultResponse = new ResultResponse<>();
        resultResponse.setMessage(scheduleResList);
        return resultResponse;
    }

    //修改班表，前端需顯示可能會取消已預約的預約單，讓使用者確認。
    @Transactional
    @Override
    public ResultResponse<String> modifySchedule(ScheduleModifyReq scheduleModifyReq) {
        Date date;
        try {
            date = AllDogCatUtils.dateFormatToSqlDate(scheduleModifyReq.getPgsDate());//yyyy->sql.date
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "修改班表日期格式有誤");
        }

        List<PetGroomerAppointment> appointmentByPgIdAndDate = groomerAppointmentDao.getAppointmentByPgIdAndDate(scheduleModifyReq.getPgId(), date);

        if(appointmentByPgIdAndDate==null){
            petScheduleRepository.save(new PetGroomerSchedule(
                    scheduleModifyReq.getPgsId(),
                    scheduleModifyReq.getPgId(),
                    date,
                    scheduleModifyReq.getPgsState()));
            ResultResponse<String> rs=new ResultResponse<>();
            rs.setMessage("您欲修改之班表無預約單，修改成功!");
            return rs;
        }
            char[] modifyArray = scheduleModifyReq.getPgsState().toCharArray();

            ResultResponse<String> rs=new ResultResponse<>();

            for(PetGroomerAppointment petGroomerAppointment:appointmentByPgIdAndDate){
                char[] appArray = petGroomerAppointment.getPgaTime().toCharArray();
                if(petGroomerAppointment.getPgaState()==0){
                    for(int i = 0; i < appArray.length; i++){
                    //如果欲修改的班表改為不可預約1 , 預約單已經預約1，並且預約單狀態為0(未完成)
                        if(modifyArray[i]=='1'&& appArray[i]=='1' && petGroomerAppointment.getPgaState()==0){
                            petGroomerAppointment.setPgaState(2);
                            groomerAppointmentDao.updateAppointmentByPgaNo(petGroomerAppointment);
                        }
                    }
                }
            }
            petScheduleRepository.save(new PetGroomerSchedule(
                    scheduleModifyReq.getPgsId(),
                    scheduleModifyReq.getPgId(),
                    date,
                    scheduleModifyReq.getPgsState()));

            rs.setMessage("您欲修改之班表已有預約單預約中，已將預約中之預約單取消，班表修改成功!");
            //<<需推播給User 預約單被取消。
        return rs;
    }
}
