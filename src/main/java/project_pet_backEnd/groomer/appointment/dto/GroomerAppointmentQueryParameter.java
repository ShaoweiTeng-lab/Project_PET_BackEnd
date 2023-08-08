package project_pet_backEnd.groomer.appointment.dto;

import lombok.Data;
import project_pet_backEnd.groomer.groomerleave.dto.LeaveOrderBy;
import project_pet_backEnd.userManager.dto.Sort;

@Data
public class GroomerAppointmentQueryParameter {
    private  String search;
    private AppointmentOrderBy order;
    //PGA_NO 預約單編號,PG_ID 美容師編號
    // PG_NAME 美容師姓名,USER_ID 會員編號,USER_NAME 會員姓名
    // PGA_DATE 預約日期
    // PGA_STATE 預約單狀態 0:未完成 1:完成訂單 2:取消
    // PGA_OPTION 預約選項
    private Sort sort;
    private  Integer limit;
    private  Integer offset;
}
