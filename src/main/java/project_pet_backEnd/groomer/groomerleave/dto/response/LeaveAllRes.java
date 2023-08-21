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
    private String leaveState;//審核狀態 0:未審核 1:審核通過 2:審核未通過
}
