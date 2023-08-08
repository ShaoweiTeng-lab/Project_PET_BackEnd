package project_pet_backEnd.groomer.petgroomerschedule.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Date;

@Data
public class PetGroomerScheduleForAppointment {

    private Integer pgsId;

    private Integer pgId;

    private String pgsDate; //sql.date ->yyyy-mm-dd

    private String pgsState;
}
