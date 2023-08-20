package project_pet_backEnd.groomer.groomerleave.dto.response;

import lombok.Data;

import java.sql.Date;
@Data
public class LeaveAllRes {
    private Integer leaveNo;
    private Integer pgId;
    private String pgName;
    private String leaveCreated; //yyyy-mm-dd
    private String leaveDate; //yyyy-mm-dd
    private String leaveTime;
    private int leaveState;
}
