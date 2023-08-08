package project_pet_backEnd.groomer.appointment.service;

import project_pet_backEnd.groomer.appointment.dto.PageForAppointment;
import project_pet_backEnd.groomer.appointment.dto.request.insertAppointmentForUserReq;
import project_pet_backEnd.groomer.appointment.dto.response.GetAllGroomersForAppointmentRes;
import project_pet_backEnd.groomer.petgroomerschedule.vo.PetGroomerSchedule;
import project_pet_backEnd.user.dto.ResultResponse;

import java.util.List;

public interface GroomerAppointmentService {

    /*
     * 進入頁面後。取得全美容師給使用者選擇
     */
    PageForAppointment<List<GetAllGroomersForAppointmentRes>> getAllGroomersForAppointment(Integer userId);

    /*
     *配合getAllGroomersForAppointment 需在前端自帶隱含input 有pgId來使用
     * 取得當前伺服器(含當天)一個月內的該pg班表
     */
    ResultResponse getGroomerScheduleByPgId(Integer pgId);


    ResultResponse insertNewAppointmentUpdateScheduleByPgId();

}
