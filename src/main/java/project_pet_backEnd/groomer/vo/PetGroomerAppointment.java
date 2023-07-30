package project_pet_backEnd.groomer.vo;

import lombok.Data;

import java.sql.Date;
@Data
public class PetGroomerAppointment {
    private int pgaNo;
    private int pgId;
    private int userId;
    private Date pgaDate;//sql.Date
    private String pgaTime;
    private int pgaState;

    // 此處省略建構子、Getter 和 Setter 方法
}
