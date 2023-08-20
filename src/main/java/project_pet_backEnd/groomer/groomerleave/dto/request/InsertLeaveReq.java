package project_pet_backEnd.groomer.groomerleave.dto.request;

import lombok.Data;
@Data
public class InsertLeaveReq {

    private Integer pgId;
    private String leaveDate; //yyyy-mm-dd
    private String leaveTime;
    private int leaveState;//審核狀態 0:未審核 1:審核通過 2:審核未通過

    // 此處省略建構子、Getter 和 Setter 方法
}
