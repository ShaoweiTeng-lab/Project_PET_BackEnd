package project_pet_backEnd.groomer.vo;

import lombok.Data;

import java.sql.Date;
@Data
public class PetGroomerAppointment {
    private int pgaNo;        // 預約單編號 (Primary Key, AUTO_INCREMENT)
    private int pgId;         // 美容師編號 (Foreign Key)
    private int userId;       // 會員編號 (Foreign Key)
    private Date pgaDate;     // sql.Date 預約日期
    private String pgaTime;   // 預約時段 (0:無預約 / 1:預約時段, 預設: 0)
    private byte pgaState;    // 預約單狀態 (0:未完成 / 1:完成訂單 / 2:取消, 預設: 0)
    private byte pgaOption;   // 預約選項
    private String pgaNotes;  // 預約文字
    private String pgaPhone;  // 預約電話 (Not Null)

    // 此處省略建構子、Getter 和 Setter 方法
}
