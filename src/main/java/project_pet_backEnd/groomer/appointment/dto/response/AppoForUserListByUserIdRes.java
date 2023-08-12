package project_pet_backEnd.groomer.appointment.dto.response;

import lombok.Data;

import java.sql.Date;
@Data
public class AppoForUserListByUserIdRes {
    private Integer pgaNo; // 預約單編號 (Primary Key, AUTO_INCREMENT)
    private String pgaDate; // yyyy-dd-mm 預約日期
    private String pgaTime; // 轉為時間x:00 ~ x:00 預約時段 (0:無預約 / 1:預約時段, 預設: 0)
    private String pgaState; // 預約單狀態 (0:未完成 / 1:訂單已完成 / 2:取消, 預設: 0)

    private String pgaOption;
    // 預約選項(0:狗狗洗澡 / 1:狗狗半手剪 (洗澡+剃毛) /
    // 2:狗狗全手剪(洗澡+全身手剪造型))
    // 3:貓咪洗澡 /4:貓咪大美容

    private String pgaNotes; // 預約文字
    private String pgaPhone; // 預約電話 (Not Null)

    private String UserName;//會員姓名 form user-table

    private String pgName;//美容師姓名 form PET_GROOMER-table
    private String pgGender;//美容師性別 form PET_GROOMER-table
    private String pgPic;//美容師照片 Base64 form PET_GROOMER-table
}
