package project_pet_backEnd.groomer.appointment.vo;

import lombok.Data;

import javax.persistence.Column;
import java.sql.Date;

@Data
public class PetGroomerSchedule {
    @Column(name = "PGS_ID")
    private Integer pgsId;
    @Column(name = "PG_ID")
    private Integer pgId;
    @Column(name = "PGS_DATE")
    private Date pgsDate; //sql.date
    @Column(name = "PGS_STATE")
    private String pgsState;

    // 此處省略建構子、Getter 和 Setter 方法
}
