package project_pet_backEnd.groomer.petgroomer.vo;

import lombok.Data;

import java.sql.Date;

@Data
public class GroomerLeave{
    private Integer leaveNo;
    private Integer pgId;
    private java.util.Date leaveCreated;//util.Date
    private Date leaveDate;//sql.date
    private String leaveTime;
    private int leaveState;

    // 此處省略建構子、Getter 和 Setter 方法
}
