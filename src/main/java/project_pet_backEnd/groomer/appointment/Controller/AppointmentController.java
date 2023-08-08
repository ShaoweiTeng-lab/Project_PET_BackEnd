package project_pet_backEnd.groomer.appointment.Controller;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project_pet_backEnd.groomer.appointment.dto.PageForAppointment;
import project_pet_backEnd.groomer.appointment.dto.response.GetAllGroomersForAppointmentRes;
import project_pet_backEnd.groomer.appointment.service.GroomerAppointmentService;
import project_pet_backEnd.user.dto.ResultResponse;

import java.util.List;

@RestController
@Validated
public class AppointmentController {

    @Autowired
    GroomerAppointmentService groomerAppointmentService;

    /*
     * 前台 for User 進入頁面提供選擇美容師List 並且藉由userId拿到 userPh & user姓名
     */
    @GetMapping("/user/AppointmentPage")
    public ResponseEntity<PageForAppointment<List<GetAllGroomersForAppointmentRes>>> getAllGroomersListForAppointmentPage(@ApiParam(hidden = true)@RequestAttribute(name = "userId") Integer userId){
        PageForAppointment<List<GetAllGroomersForAppointmentRes>> allGroomersForAppointment = groomerAppointmentService.getAllGroomersForAppointment(userId);
        return ResponseEntity.status(HttpStatus.OK).body(allGroomersForAppointment);
    }
    /*
     * 前台 for User 選擇美容師後列出該美容師含當日至一個月內的班表
     */
    @GetMapping("/user/ChosePgGetScheduleByPgId")
    public ResponseEntity<?> chosePgGetScheduleByPgId(@RequestParam Integer pgId){
        ResultResponse allGroomersForAppointment = groomerAppointmentService.getGroomerScheduleByPgId(pgId);
        return ResponseEntity.status(HttpStatus.OK).body(allGroomersForAppointment);
    }



}
