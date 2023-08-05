package project_pet_backEnd.groomer.petgroomer.vo;

import lombok.Data;

import javax.persistence.Column;
import java.sql.Date;

@Data
public class GroomerLeave {
    @Column(name = "LEAVE_NO")
    private Integer leaveNo;
    @Column(name = "PG_ID")
    private Integer pgId;
    @Column(name = "LEAVE_CREATED")
    private Date leaveCreated; //sql.date
    @Column(name = "LEAVE_DATE")
    private Date leaveDate; //sql.date
    @Column(name = "LEAVE_TIME")
    private String leaveTime;
    @Column(name = "LEAVE_STATE")
    private int leaveState;

    // 此處省略建構子、Getter 和 Setter 方法
}
