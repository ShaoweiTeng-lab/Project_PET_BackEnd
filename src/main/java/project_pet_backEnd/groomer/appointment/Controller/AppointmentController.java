package project_pet_backEnd.groomer.appointment.Controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.groomer.appointment.dto.*;
import project_pet_backEnd.groomer.appointment.dto.request.AppointmentCompleteOrCancelReq;
import project_pet_backEnd.groomer.appointment.dto.request.AppointmentModifyReq;
import project_pet_backEnd.groomer.appointment.dto.request.InsertAppointmentForUserReq;
import project_pet_backEnd.groomer.appointment.dto.response.AppoForMan;
import project_pet_backEnd.groomer.appointment.dto.response.AppoForUserListByUserIdRes;
import project_pet_backEnd.groomer.appointment.dto.response.GetAllGroomersForAppointmentRes;
import project_pet_backEnd.groomer.appointment.service.GroomerAppointmentService;
import project_pet_backEnd.groomer.petgroomerschedule.dto.PetGroomerScheduleForAppointment;
import project_pet_backEnd.utils.commonDto.ResultResponse;
import project_pet_backEnd.userManager.dto.Sort;
import project_pet_backEnd.utils.commonDto.Page;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Api(tags = "美容師預約功能")
@RestController
@Validated
public class AppointmentController {

    @Autowired
    GroomerAppointmentService groomerAppointmentService;

    /*
     * 前台 for User 進入頁面提供選擇美容師List 並且藉由userId拿到 userPh & user姓名V
     */
    @ApiOperation("User預約頁面查詢美容師")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/user/appointmentPage")
    public ResultResponse<PageForAppointment<List<GetAllGroomersForAppointmentRes>>> getAllGroomersListForAppointmentPageForUser(@RequestAttribute(name = "userId") Integer userId){
        PageForAppointment<List<GetAllGroomersForAppointmentRes>> allGroomersForAppointment = groomerAppointmentService.getAllGroomersForAppointment(userId);

        ResultResponse<PageForAppointment<List<GetAllGroomersForAppointmentRes>>> resultResponse =new ResultResponse<>();
        resultResponse.setMessage(allGroomersForAppointment);
        return resultResponse;
    }
    /*
     * 前台 for User 選擇美容師後列出該美容師含當日至一個月內的班表V
     */
    @ApiOperation("User預約頁面查詢美容師班表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/user/pgScheduleForA")
    public ResultResponse<List<PetGroomerScheduleForAppointment>> chosePgGetScheduleByPgIdForUser(@RequestParam Integer pgId){
        List<PetGroomerScheduleForAppointment> groomerScheduleByPgId = groomerAppointmentService.getGroomerScheduleByPgId(pgId);

        ResultResponse<List<PetGroomerScheduleForAppointment>> resultResponse =new ResultResponse<>();
        resultResponse.setMessage(groomerScheduleByPgId);
        return resultResponse;
    }
    /*
     * 前台 for User 預約美容師(新增預約單)V
     */
    @ApiOperation("User預約美容師")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
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
     * 前台 for User 查詢美容師預約V
     */
    @ApiOperation("User查詢已預約單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
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

        ResultResponse<Page<List<AppoForUserListByUserIdRes>>> resultResponse =new ResultResponse<>();
        resultResponse.setMessage(appointment);
        return resultResponse;
    }
    /*
     * 前台 for User 修改預約單V
     */
    @ApiOperation("User修改預約單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/user/modifyAppointment")
    public ResultResponse<String>modifyAppointment(@RequestBody @Valid AppointmentModifyReq appointmentModifyReq){

        // 正則表達式驗證手機號碼格式 (台灣 10 位數字)
        if(appointmentModifyReq.getPgaPhone() != null && !appointmentModifyReq.getPgaPhone().isEmpty()){
            String phoneRegex = "^09[0-9]{8}$";
            Pattern pattern = Pattern.compile(phoneRegex);
            Matcher matcher = pattern.matcher(appointmentModifyReq.getPgaPhone());
            if (!matcher.matches()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "手機號碼格式有誤 !，請輸入有效的台灣手機號碼（10位數字）");
            }
        }

        return groomerAppointmentService.modifyAppointmentByByPgaNo(appointmentModifyReq);
    }
    //完成或取消訂單 for UserV
    @ApiOperation("User修改預約單狀態")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/user/CompleteOrCancel")
    public ResultResponse<String> appointmentCompleteOrCancel(@RequestBody @Valid AppointmentCompleteOrCancelReq appointmentCompleteOrCancelReq){
        return groomerAppointmentService.AppointmentCompleteOrCancel(appointmentCompleteOrCancelReq);
    }

    //----------------------------美容師後台管理(預約管理)------------------------------------------------------

    //查詢預約 for Man V
    @ApiOperation("Man預約單查詢")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @GetMapping("/manager/allAppointmentSearch")
    public ResultResponse<Page<List<AppoForMan>>> AllAppointmentSearchForMan(
            @RequestParam(value = "search",required = false, defaultValue = "") String search,
            @RequestParam(value = "orderBy",required = false, defaultValue = "PGA_NO") AppointmentOrderBy orderBy,
            @RequestParam(value = "sort",required = false,defaultValue = "desc") Sort sort,
            @RequestParam(value = "limit",defaultValue = "10")@Max(50) @Min(0) Integer limit,
            @RequestParam(value = "offset",defaultValue = "0")@Min(0)Integer offset
    ){
        GroomerAppointmentQueryParameter groomerAppointmentQueryParameter = new GroomerAppointmentQueryParameter();
        if(search.isBlank()){
            groomerAppointmentQueryParameter.setSearch(null);
        }else {
            groomerAppointmentQueryParameter.setSearch(search);
        }
        groomerAppointmentQueryParameter.setOrder(orderBy);
        groomerAppointmentQueryParameter.setSort(sort);
        groomerAppointmentQueryParameter.setLimit(limit);
        groomerAppointmentQueryParameter.setOffset(offset);
        Page<List<AppoForMan>> allAppointmentWithSearch = groomerAppointmentService.getAllAppointmentWithSearch(groomerAppointmentQueryParameter);

        ResultResponse<Page<List<AppoForMan>>> resultResponse =new ResultResponse<>();
        resultResponse.setMessage(allAppointmentWithSearch);
        return resultResponse;
    }
    /*
     * 前台 for Man 選擇美容師後列出該美容師含當日至一個月內的班表V
     */
    @ApiOperation("Man修改預約頁面查詢PG班表")
    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/manager/pgScheduleForA")
    public ResultResponse<List<PetGroomerScheduleForAppointment>> chosePgGetScheduleByPgIdForMan(@RequestParam Integer pgId){
        List<PetGroomerScheduleForAppointment> groomerScheduleByPgId = groomerAppointmentService.getGroomerScheduleByPgId(pgId);

        ResultResponse<List<PetGroomerScheduleForAppointment>> resultResponse =new ResultResponse<>();
        resultResponse.setMessage(groomerScheduleByPgId);
        return resultResponse;
    }

    //修改預約  for Man
    @ApiOperation("Man修改預約單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/manager/modifyAppointment")
    public ResultResponse<String> modifyAppointmentForMan(@RequestBody @Valid AppointmentModifyReq appointmentModifyReq){
        // 正則表達式驗證手機號碼格式 (台灣 10 位數字)
        if(appointmentModifyReq.getPgaPhone() != null && !appointmentModifyReq.getPgaPhone().isEmpty()){
            String phoneRegex = "^09[0-9]{8}$";
            Pattern pattern = Pattern.compile(phoneRegex);
            Matcher matcher = pattern.matcher(appointmentModifyReq.getPgaPhone());
            if (!matcher.matches()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "手機號碼格式有誤 !，請輸入有效的台灣手機號碼（10位數字）");
            }
        }

        // 驗證日期格式 (yyyy-MM-dd)
        String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$";
        if (appointmentModifyReq.getPgaNewDate() != null && !appointmentModifyReq.getPgaNewDate().isEmpty() &&
                !Pattern.matches(dateRegex, appointmentModifyReq.getPgaNewDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "日期格式有誤，請使用 yyyy-MM-dd 格式");
        }

        return groomerAppointmentService.modifyAppointmentByByPgaNo(appointmentModifyReq);
    }
    //取消預約單or完成訂單。for Man v
    @ApiOperation("Man修改預約單狀態")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/manager/CompleteOrCancel")
    public ResultResponse<String> appointmentCompleteOrCancelForMan(@RequestBody @Valid AppointmentCompleteOrCancelReq appointmentCompleteOrCancelReq){
        return groomerAppointmentService.AppointmentCompleteOrCancelForMan(appointmentCompleteOrCancelReq);
    }

    //----------------------------美容師個人管理------------------------------------------------------

    //查詢預約 for PG V
    @ApiOperation("Pg查詢預約單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
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
        if(search.isBlank()){
            groomerAppointmentQueryParameter.setSearch(null);
        }else {
            groomerAppointmentQueryParameter.setSearch(search);
        }
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
