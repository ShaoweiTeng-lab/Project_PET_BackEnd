package project_pet_backEnd.groomer.groomerleave.dto.request;

import lombok.Data;
@Data
public class InsertLeaveReq {

    private String leaveDate; //yyyy-mm-dd
    private String leaveTime;

    // 此處省略建構子、Getter 和 Setter 方法
}
