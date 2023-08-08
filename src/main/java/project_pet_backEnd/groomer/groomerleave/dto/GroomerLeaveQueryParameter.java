package project_pet_backEnd.groomer.groomerleave.dto;

import lombok.Data;
import project_pet_backEnd.groomer.petgroomerschedule.dto.ScheduleOrderBy;
import project_pet_backEnd.userManager.dto.Sort;

@Data
public class GroomerLeaveQueryParameter {

    private  String search;
    private LeaveOrderBy order;
    //LEAVE_DATE請假日期, LEAVE_CREATED假單申請日期,
    // PG_ID美容師編號 LEAVE_STATE審核狀態 0:未審核 1:審核通過 2:審核未通過
    //PG_NAME美容師名稱
    private Sort sort;
    private  Integer limit;
    private  Integer offset;
}
