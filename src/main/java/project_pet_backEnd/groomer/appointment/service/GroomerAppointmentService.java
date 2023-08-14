package project_pet_backEnd.groomer.appointment.service;

import project_pet_backEnd.groomer.appointment.dto.AppointmentListForUser;
import project_pet_backEnd.groomer.appointment.dto.PageForAppointment;
import project_pet_backEnd.groomer.appointment.dto.UserAppoQueryParameter;
import project_pet_backEnd.groomer.appointment.dto.request.AppointmentCompleteOrCancelReq;
import project_pet_backEnd.groomer.appointment.dto.request.AppointmentModifyReq;
import project_pet_backEnd.groomer.appointment.dto.request.InsertAppointmentForUserReq;
import project_pet_backEnd.groomer.appointment.dto.response.AppoForUserListByUserIdRes;
import project_pet_backEnd.groomer.appointment.dto.response.GetAllGroomersForAppointmentRes;
import project_pet_backEnd.groomer.appointment.vo.PetGroomerAppointment;
import project_pet_backEnd.groomer.petgroomerschedule.dto.PetGroomerScheduleForAppointment;
import project_pet_backEnd.user.dto.ResultResponse;
import project_pet_backEnd.utils.commonDto.Page;

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
    List<PetGroomerScheduleForAppointment> getGroomerScheduleByPgId(Integer pgId);

    /*
     * 前台 for User 預約美容師(新增預約單)
     */
    ResultResponse insertNewAppointmentAndUpdateSchedule(InsertAppointmentForUserReq insertNewAppointmentUpdateSchedule);


    /*
     * 前台 for User 查詢美容師預約
     */
    Page<List<AppoForUserListByUserIdRes>> getUserAppointmentByUserId(Integer userId, UserAppoQueryParameter userAppoQueryParameter);

    /*
     * 修改預約單 for User
     */
    ResultResponse modifyAppointmentByByPgaNo(AppointmentModifyReq appointmentModifyReq);

    ResultResponse AppointmentCompleteOrCancel (AppointmentCompleteOrCancelReq appointmentCompleteOrCancelReq);
}

