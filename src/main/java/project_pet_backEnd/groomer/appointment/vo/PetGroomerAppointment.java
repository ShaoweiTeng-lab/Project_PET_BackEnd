package project_pet_backEnd.groomer.appointment.vo;

import lombok.Data;

import javax.persistence.Column;
import java.sql.Date;
@Data
public class PetGroomerAppointment {
    @Column(name = "PGA_NO")
    private Integer pgaNo; // 預約單編號 (Primary Key, AUTO_INCREMENT)
    @Column(name = "PG_ID")
    private Integer pgId; // 美容師編號 (Foreign Key)
    @Column(name = "USER_ID")
    private Integer userId; // 會員編號 (Foreign Key)
    @Column(name = "PGA_DATE")
    private Date pgaDate; // sql.Date 預約日期
    @Column(name = "PGA_TIME")
    private String pgaTime; // 預約時段 (0:無預約 / 1:預約時段, 預設: 0)
    @Column(name = "PGA_STATE")
    private byte pgaState; // 預約單狀態 (0:未完成 / 1:完成訂單 / 2:取消, 預設: 0)
    @Column(name = "PGA_OPTION")
    private byte pgaOption; // 預約選項
    @Column(name = "PGA_NOTES")
    private String pgaNotes; // 預約文字
    @Column(name = "PGA_PHONE")
    private String pgaPhone; // 預約電話 (Not Null)

    // 此處省略建構子、Getter 和 Setter 方法
}