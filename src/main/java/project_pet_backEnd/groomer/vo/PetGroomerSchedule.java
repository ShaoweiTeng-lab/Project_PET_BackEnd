package project_pet_backEnd.groomer.vo;

import lombok.Data;

import java.sql.Date;

@Data
public class PetGroomerSchedule {
    private int pgsId;
    private int pgId;
    private Date pgsDate;//sql.date
    private String pgsState;

    // 此處省略建構子、Getter 和 Setter 方法
}
