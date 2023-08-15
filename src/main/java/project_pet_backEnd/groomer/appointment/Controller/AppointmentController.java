package project_pet_backEnd.groomer.appointment.Controller;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.groomer.appointment.dto.*;
import project_pet_backEnd.groomer.appointment.dto.request.AppointmentCompleteOrCancelReq;
import project_pet_backEnd.groomer.appointment.dto.request.AppointmentModifyReq;
import project_pet_backEnd.groomer.appointment.dto.request.InsertAppointmentForUserReq;
import project_pet_backEnd.groomer.appointment.dto.response.AppoForMan;
import project_pet_backEnd.groomer.appointment.dto.response.AppoForUserListByUserIdRes;
import project_pet_backEnd.groomer.appointment.dto.response.GetAllGroomersForAppointmentRes;
import project_pet_backEnd.groomer.appointment.service.GroomerAppointmentService;
import project_pet_backEnd.groomer.petgroomer.dto.orderby.PGOrderBy;
import project_pet_backEnd.groomer.petgroomerschedule.dto.PetGroomerScheduleForAppointment;
import project_pet_backEnd.utils.commonDto.ResultResponse;
import project_pet_backEnd.userManager.dto.Sort;
import project_pet_backEnd.utils.commonDto.Page;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
public class AppointmentController {

    @Autowired
    GroomerAppointmentService groomerAppointmentService;

    /*
     * 前台 for User 進入頁面提供選擇美容師List 並且藉由userId拿到 userPh & user姓名
     */
    @GetMapping("/user/appointmentPage")
    public ResponseEntity<PageForAppointment<List<GetAllGroomersForAppointmentRes>>> getAllGroomersListForAppointmentPageForUser(@ApiParam(hidden = true)@RequestAttribute(name = "userId") Integer userId){
        PageForAppointment<List<GetAllGroomersForAppointmentRes>> allGroomersForAppointment = groomerAppointmentService.getAllGroomersForAppointment(userId);
        return ResponseEntity.status(HttpStatus.OK).body(allGroomersForAppointment);
    }
    /*
     * 前台 for User 選擇美容師後列出該美容師含當日至一個月內的班表
     */
    @GetMapping("/user/pgScheduleForA")
    public ResponseEntity<?> chosePgGetScheduleByPgIdForUser(@RequestParam Integer pgId){
        List<PetGroomerScheduleForAppointment> groomerScheduleByPgId = groomerAppointmentService.getGroomerScheduleByPgId(pgId);
        return ResponseEntity.status(HttpStatus.OK).body(groomerScheduleByPgId);
    }
    /*
     * 前台 for User 預約美容師(新增預約單)
     */
    @PostMapping("/user/newAppointment")
    public ResponseEntity<?> insertNewAppointmentAndUpdateSchedule(@RequestAttribute(name = "userId") Integer userId,
                                                                   @RequestBody @Valid InsertAppointmentForUserReq insertAppointmentForUserReq){
        if (insertAppointmentForUserReq.getPgaState() == null) {
            insertAppointmentForUserReq.setPgaState(0);
        }

        ResultResponse resultResponse = groomerAppointmentService.insertNewAppointmentAndUpdateSchedule(userId,insertAppointmentForUserReq);
        return ResponseEntity.status(200).body(resultResponse);
    }

    /*
     * 前台 for User 查詢美容師預約
     */
    @GetMapping("/user/appointmentList")
    public  ResponseEntity<Page<List<AppoForUserListByUserIdRes>>> getAllAppointmentList(
            @RequestAttribute(name = "userId") Integer userId,
            @RequestParam(value = "orderBy",required = false, defaultValue = "PGA_DATE") UserAppoOrderBy orderBy,
            @RequestParam(value = "sort",required = false,defaultValue = "desc") Sort sort,
            @RequestParam(value = "limit",defaultValue = "10")@Max(50) @Min(0) Integer limit,
            @RequestParam(value = "offset",defaultValue = "0")@Min(0)Integer offset
    ){
        UserAppoQueryParameter userAppoQueryParameter = new UserAppoQueryParameter();
        userAppoQueryParameter.setOrder(orderBy);
        userAppoQueryParameter.setSort(sort);
        userAppoQueryParameter.setLimit(limit);
        userAppoQueryParameter.setOffset(offset);
        Page<List<AppoForUserListByUserIdRes>> appointment = groomerAppointmentService.getUserAppointmentByUserId(userId,userAppoQueryParameter);

        return ResponseEntity.status(200).body(appointment);
    }
    /*
     * 前台 for User 修改預約單
     */
    @PostMapping("/user/modifyAppointment")
    public ResponseEntity<?> modifyAppointment(@RequestBody @Valid AppointmentModifyReq appointmentModifyReq){
        ResultResponse rs = groomerAppointmentService.modifyAppointmentByByPgaNo(appointmentModifyReq);
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }
    //完成或取消訂單 for User
    @PostMapping("/user/CompleteOrCancel")
    public ResponseEntity<?> appointmentCompleteOrCancel(@RequestBody @Valid AppointmentCompleteOrCancelReq appointmentCompleteOrCancelReq){
        ResultResponse resultResponse = groomerAppointmentService.AppointmentCompleteOrCancel(appointmentCompleteOrCancelReq);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    //----------------------------美容師後台管理(預約管理)------------------------------------------------------

    //查詢預約 for Man
    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @GetMapping("/manager/allAppointmentSearch")
    public ResponseEntity<?> AllAppointmentSearchForMan(
            @RequestParam(value = "search",required = false) String search,
            @RequestParam(value = "orderBy",required = false, defaultValue = "PGA_NO") AppointmentOrderBy orderBy,
            @RequestParam(value = "sort",required = false,defaultValue = "desc") Sort sort,
            @RequestParam(value = "limit",defaultValue = "10")@Max(50) @Min(0) Integer limit,
            @RequestParam(value = "offset",defaultValue = "0")@Min(0)Integer offset
    ){
        GroomerAppointmentQueryParameter groomerAppointmentQueryParameter = new GroomerAppointmentQueryParameter();
        groomerAppointmentQueryParameter.setSearch(search);
        groomerAppointmentQueryParameter.setOrder(orderBy);
        groomerAppointmentQueryParameter.setSort(sort);
        groomerAppointmentQueryParameter.setLimit(limit);
        groomerAppointmentQueryParameter.setOffset(offset);
        Page<List<AppoForMan>> allAppointmentWithSearch = groomerAppointmentService.getAllAppointmentWithSearch(groomerAppointmentQueryParameter);
        return ResponseEntity.status(200).body(allAppointmentWithSearch);
    }

    //修改預約  for Man
    @PostMapping("/manager/modifyAppointment")
    public ResponseEntity<?> modifyAppointmentForMan(@RequestBody @Valid AppointmentModifyReq appointmentModifyReq){
        ResultResponse rs = groomerAppointmentService.modifyAppointmentByByPgaNo(appointmentModifyReq);
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }
    //取消預約單or完成訂單。for Man
    @PostMapping("/manager/CompleteOrCancel")
    public ResponseEntity<?> appointmentCompleteOrCancelForMan(@RequestBody @Valid AppointmentCompleteOrCancelReq appointmentCompleteOrCancelReq){
        ResultResponse resultResponse = groomerAppointmentService.AppointmentCompleteOrCancelForMan(appointmentCompleteOrCancelReq);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }


}
