package project_pet_backEnd.groomer.appointment.vo;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
@DynamicInsert
@DynamicUpdate
@Data
@Entity
@Table(name = "PET_GROOMER_APPOINTMENT")
public class PetGroomerAppointment {
    @Column(name = "PGA_NO")
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pgaNo; // 預約單編號 (Primary Key, AUTO_INCREMENT)
    @Column(name = "PG_ID")
    private Integer pgId; // 美容師編號 (Foreign Key)
    @Column(name = "USER_ID")
    private Integer userId; // 會員編號 (Foreign Key)
    @Column(name = "PGA_DATE")
    private Date pgaDate; // sql.Date 預約日期
    @Column(name = "PGA_TIME")
    @Size(min = 24, max = 24)
    private String pgaTime; // Varchar(24)  預約時段 (0:無預約 / 1:預約時段, 預設: 0)
    @Column(name = "PGA_STATE")
    private Integer pgaState; // 預約單狀態 (0:未完成 / 1:完成訂單 / 2:取消, 預設: 0)
    @Column(name = "PGA_OPTION")
    private Integer pgaOption; // 預約選項
    @Column(name = "PGA_NOTES")
    private String pgaNotes; // 預約文字
    @Column(name = "PGA_PHONE")
    private String pgaPhone; // 預約電話 (Not Null)

    // 此處省略建構子、Getter 和 Setter 方法
}