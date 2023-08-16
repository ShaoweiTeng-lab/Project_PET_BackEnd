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
import project_pet_backEnd.groomer.petgroomer.dto.response.GetAllGroomerListSortRes;
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
    public ResultResponse<PageForAppointment<List<GetAllGroomersForAppointmentRes>>> getAllGroomersListForAppointmentPageForUser(@ApiParam(hidden = true)@RequestAttribute(name = "userId") Integer userId){
        PageForAppointment<List<GetAllGroomersForAppointmentRes>> allGroomersForAppointment = groomerAppointmentService.getAllGroomersForAppointment(userId);

        ResultResponse<PageForAppointment<List<GetAllGroomersForAppointmentRes>>> rs =new ResultResponse<>();
        rs.setMessage(allGroomersForAppointment);
        return rs;
    }
    /*
     * 前台 for User 選擇美容師後列出該美容師含當日至一個月內的班表
     */
    @GetMapping("/user/pgScheduleForA")
    public ResultResponse<List<PetGroomerScheduleForAppointment>> chosePgGetScheduleByPgIdForUser(@RequestParam Integer pgId){
        List<PetGroomerScheduleForAppointment> groomerScheduleByPgId = groomerAppointmentService.getGroomerScheduleByPgId(pgId);

        ResultResponse<List<PetGroomerScheduleForAppointment>> rs =new ResultResponse<>();
        rs.setMessage(groomerScheduleByPgId);
        return rs;
    }
    /*
     * 前台 for User 預約美容師(新增預約單)
     */
    @PostMapping("/user/newAppointment")
    public ResultResponse<String> insertNewAppointmentAndUpdateSchedule(
            @RequestAttribute(name = "userId") Integer userId,
            @RequestBody @Valid InsertAppointmentForUserReq insertAppointmentForUserReq){
        if (insertAppointmentForUserReq.getPgaState() == null) {
            insertAppointmentForUserReq.setPgaState(0);
        }
        return groomerAppointmentService.insertNewAppointmentAndUpdateSchedule(userId,insertAppointmentForUserReq);
    }

    /*
     * 前台 for User 查詢美容師預約
     */
    @GetMapping("/user/appointmentList")
    public  ResultResponse<Page<List<AppoForUserListByUserIdRes>>> getAllAppointmentList(
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

        ResultResponse<Page<List<AppoForUserListByUserIdRes>>> rs =new ResultResponse<>();
        rs.setMessage(appointment);
        return rs;
    }
    /*
     * 前台 for User 修改預約單
     */
    @PostMapping("/user/modifyAppointment")
    public ResultResponse<String>modifyAppointment(@RequestBody @Valid AppointmentModifyReq appointmentModifyReq){
        return groomerAppointmentService.modifyAppointmentByByPgaNo(appointmentModifyReq);
    }
    //完成或取消訂單 for User
    @PostMapping("/user/CompleteOrCancel")
    public ResultResponse<String> appointmentCompleteOrCancel(@RequestBody @Valid AppointmentCompleteOrCancelReq appointmentCompleteOrCancelReq){
        return groomerAppointmentService.AppointmentCompleteOrCancel(appointmentCompleteOrCancelReq);
    }

    //----------------------------美容師後台管理(預約管理)------------------------------------------------------

    //查詢預約 for Man
    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @GetMapping("/manager/allAppointmentSearch")
    public ResultResponse<Page<List<AppoForMan>>> AllAppointmentSearchForMan(
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

        ResultResponse<Page<List<AppoForMan>>> resultResponse =new ResultResponse<>();
        resultResponse.setMessage(allAppointmentWithSearch);
        return resultResponse;
    }

    //修改預約  for Man
    @PostMapping("/manager/modifyAppointment")
    public ResultResponse<String> modifyAppointmentForMan(@RequestBody @Valid AppointmentModifyReq appointmentModifyReq){
        return groomerAppointmentService.modifyAppointmentByByPgaNo(appointmentModifyReq);
    }
    //取消預約單or完成訂單。for Man
    @PostMapping("/manager/CompleteOrCancel")
    public ResultResponse<String> appointmentCompleteOrCancelForMan(@RequestBody @Valid AppointmentCompleteOrCancelReq appointmentCompleteOrCancelReq){
        return groomerAppointmentService.AppointmentCompleteOrCancelForMan(appointmentCompleteOrCancelReq);
    }

    //----------------------------美容師個人管理------------------------------------------------------

    //查詢預約 for PG
    @PreAuthorize("hasAnyAuthority('美容師個人管理')")
    @GetMapping("/manager/PgAppointmentSearch")
    public ResultResponse<Page<List<AppoForMan>>> AllAppointmentSearchForPg(
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
        Page<List<AppoForMan>> allAppointmentSearch = groomerAppointmentService.getAllAppointmentWithSearch(groomerAppointmentQueryParameter);

        ResultResponse<Page<List<AppoForMan>>> resultResponse = new ResultResponse<>();
        resultResponse.setMessage(allAppointmentSearch);

        return resultResponse;
    }


}
