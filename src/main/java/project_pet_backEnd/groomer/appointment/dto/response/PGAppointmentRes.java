package project_pet_backEnd.groomer.appointment.dto.response;

import lombok.Data;

import javax.persistence.Column;
import java.sql.Date;

@Data
public class PGAppointmentRes {
    private String pgName;// 美容師姓名
    private Integer pgaNo; // 預約單編號 (Primary Key, AUTO_INCREMENT)
    private Integer pgId; // 美容師編號 (Foreign Key)
    private Integer userId; // 會員編號 (Foreign Key)
    private String userName;//USER_NAME 會員姓名
    private Date pgaDate; // sql.Date 預約日期
    private String pgaTime; // Varchar(24)  預約時段 (0:無預約 / 1:預約時段, 預設: 0)
    private byte pgaState; // 預約單狀態 (0:未完成 / 1:完成訂單 / 2:取消, 預設: 0)
    private byte pgaOption; // 預約選項
    private String pgaNotes; // 預約文字
    private String pgaPhone; // 預約電話 (Not Null)
}
