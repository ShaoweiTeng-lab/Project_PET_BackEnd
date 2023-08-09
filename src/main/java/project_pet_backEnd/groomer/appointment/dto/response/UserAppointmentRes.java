package project_pet_backEnd.groomer.appointment.dto.response;


import lombok.Data;

import javax.validation.constraints.Size;
import java.sql.Date;

@Data
public class UserAppointmentRes {

    private Integer pgaNo; // 預約單編號 (Primary Key, AUTO_INCREMENT)
    private Integer pgId; // 美容師編號 (Foreign Key)
    private Integer userId; // 會員編號 (Foreign Key)
    private Date pgaDate; // sql.Date 預約日期
    @Size(min = 24, max = 24)
    private String pgaTime; // Varchar(24)  預約時段 (0:無預約 / 1:預約時段, 預設: 0)
    private Integer pgaState; // 預約單狀態 (0:未完成 / 1:完成訂單 / 2:取消, 預設: 0)
    private Integer pgaOption; // 預約選項
    private String pgaNotes; // 預約文字
    private String pgaPhone; // 預約電話 (Not Null)

    private String UserName;//會員姓名 form user-table

    private String pgName;//美容師姓名 form user-table
    private String pgGender;//美容師性別 form PET_GROOMER-table
    private String pgPic;//美容師照片 Base64 form PET_GROOMER-table

}
