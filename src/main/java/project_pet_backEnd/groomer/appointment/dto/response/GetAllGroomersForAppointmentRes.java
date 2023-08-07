package project_pet_backEnd.groomer.appointment.dto.response;

import lombok.Data;

@Data
public class GetAllGroomersForAppointmentRes {
    private Integer pgId;
    private String pgName;
    private String pgGender;//String 男性 / 女性
    private String pgPic;//Base64
}
