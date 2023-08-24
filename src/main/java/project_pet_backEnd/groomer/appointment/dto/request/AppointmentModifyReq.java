package project_pet_backEnd.groomer.appointment.dto.request;

import lombok.Data;
import javax.validation.constraints.NotNull;


@Data
public class AppointmentModifyReq {
    @NotNull
    private Integer pgaNo; // 預約單編號 (Primary Key, AUTO_INCREMENT)
    @NotNull
    private Integer pgId;
    private Integer pgaState; // 預約單狀態 (0:未完成 / 1:完成訂單 / 2:取消, 預設: 0)

    private String sourcePgaDate;//yyyy-mm-dd 修改前的日期
    private String pgaNewDate;//yyyy-mm-dd   修改後的預約日期

    private String sourcePgaTime;//xx:xx~xx:xx  原先預約時段
    private String pgaNewTime;//xx:xx~xx:xx   修改後的預約時段

    private String pgaOption; // 預約選項case 0 -> "狗狗洗澡";
    // "狗狗半手剪 (洗澡+剃毛)";"狗狗全手剪(洗澡+全身手剪造型)";
    // "貓咪洗澡";"貓咪大美容";
    private String pgaNotes; // 預約文字
    private String pgaPhone; // 預約電話
}
