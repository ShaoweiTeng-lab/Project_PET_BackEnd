package project_pet_backEnd.groomer.appointment.dto;

import lombok.Data;
import java.sql.Date;
@Data
public class AppointmentListForUser {

    private Integer pgaNo; // 預約單編號 (Primary Key, AUTO_INCREMENT)
    private Date pgaDate; // sql.Date 預約日期
    private String pgaTime; // Varchar(24)  預約時段 (0:無預約 / 1:預約時段, 預設: 0)
    private Integer pgaState; // 預約單狀態 (0:未完成 / 1:完成訂單 / 2:取消, 預設: 0)

    private Integer pgaOption;
    // 預約選項(0:狗狗洗澡 / 1:狗狗半手剪 (洗澡+剃毛) /
    // 2:狗狗全手剪(洗澡+全身手剪造型))
    // 3:貓咪洗澡 /4:貓咪大美容

    private String pgaNotes; // 預約文字
    private String pgaPhone; // 預約電話 (Not Null)

    private String UserName;//會員姓名 form user-table

    private String pgName;//美容師姓名 form PET_GROOMER-table
    private Integer pgGender;//美容師性別 form PET_GROOMER-table
    private byte[] pgPic;//美容師照片 Base64 form PET_GROOMER-table
}
