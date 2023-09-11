package project_pet_backEnd.groomer.pgPushNotify;


import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project_pet_backEnd.groomer.appointment.dao.AppointmentRepository;
import project_pet_backEnd.groomer.appointment.vo.PetGroomerAppointment;
@Aspect
@Component
public class PgNotifyAspect {

    @Autowired
    PgNotifyWebSocketHandler pgNotifyWebSocketHandler;
    @Autowired
    AppointmentRepository appointmentRepository;

    //todo: 當用戶預約時
    @After("execution(* project_pet_backEnd.groomer.appointment.service.*.insertNewAppointmentAndUpdateSchedule(..))")
    public void AppointmentNotify() throws Exception{
        PgNotifyType pgNotifyType = PgNotifyType.Appointment;
        PetGroomerAppointment petGroomerAppointment = appointmentRepository.findFirstByOrderByPgaNoDesc();

        PgNotifyMsg pgNotifyMsg = new PgNotifyMsg(pgNotifyType,"您的預約單已成立!預約單編號為:[ "+petGroomerAppointment.getPgaNo()+" ]。");
        pgNotifyWebSocketHandler.publishPersonalNotifyMsg(petGroomerAppointment.getUserId(),pgNotifyMsg);

    }

}
