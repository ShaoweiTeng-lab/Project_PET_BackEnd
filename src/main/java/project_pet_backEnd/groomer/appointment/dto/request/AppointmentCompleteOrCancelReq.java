package project_pet_backEnd.groomer.appointment.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class AppointmentCompleteOrCancelReq {
    @NotNull
    private Integer pgaNo;
    @NotNull
    private Integer pgaState;
}
