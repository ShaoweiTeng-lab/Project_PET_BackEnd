package project_pet_backEnd.groomer.appointment.dto.request;

import lombok.Data;

@Data
public class insertAppointmentForUserReq {
    private Integer pgId; // 美容師編號 (Foreign Key)
    private Integer userId; // 會員編號 (Foreign Key)
    private String pgaDate; // yyyy-mm-dd sql.Date 預約日期
    private String pgaTime; // Varchar(24)  預約時段 (0:無預約 / 1:預約時段, 預設: 0)
    private byte pgaState; // 預約單狀態 (0:未完成 / 1:完成訂單 / 2:取消, 預設: 0)
    private byte pgaOption; // 預約選項
    private String pgaNotes; // 預約文字
    private String pgaPhone; // 預約電話 (Not Null)

}
