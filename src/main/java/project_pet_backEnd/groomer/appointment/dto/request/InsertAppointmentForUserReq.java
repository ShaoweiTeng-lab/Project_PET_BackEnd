package project_pet_backEnd.groomer.appointment.dto.request;

import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class InsertAppointmentForUserReq {
    @NotNull
    private Integer pgId; // 美容師編號 (Foreign Key)
    @NotBlank
    private String pgaDate; // yyyy-mm-dd sql.Date 預約日期
    @NotBlank
    private String pgaTime; // Varchar(24)  預約時段 (0:無預約 / 1:預約時段, 預設: 0)
    private Integer pgaState; // 預約單狀態 (0:未完成 / 1:完成訂單 / 2:取消, 預設: 0)
    @NotNull
    private Integer pgaOption; // 預約選項

    private String pgaNotes; // 預約文字
    @NotBlank
    private String pgaPhone; // 預約電話 (Not Null)

}
