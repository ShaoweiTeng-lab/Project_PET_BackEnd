package project_pet_backEnd.groomer.groomerleave.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangeLeaveReq {
    @NotNull
    private Integer leaveNo;
    @NotNull
    private Integer leaveState;//審核狀態 0:未審核 1:審核通過 2:審核未通過

    // 此處省略建構子、Getter 和 Setter 方法
}
