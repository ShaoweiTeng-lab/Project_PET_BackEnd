package project_pet_backEnd.manager.dto;

import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AdjustManagerRequest {
    private Integer  managerId;
    @NotBlank(message = "帳號不可為空")
    private  String  orgManagerAccount;//修改前的帳號名稱
    @NotBlank(message = "帳號不可為空")
    private  String  managerAccount;
    @NotBlank(message = "密碼不可為空")
    private  String  managerPassword;
    @Min(0)
    @Max(1)
    private  Integer managerState;
}
