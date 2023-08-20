package project_pet_backEnd.groomer.groomerleave.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;

@Table(name = "GROOMER_LEAVE")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class GroomerLeave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LEAVE_NO",insertable = false,updatable = false)
    private Integer leaveNo;
    @Column(name = "PG_ID")
    private Integer pgId;
    private Date leaveCreated; //sql.date
    @Column(name = "LEAVE_DATE")
    private Date leaveDate; //sql.date
    @Column(name = "LEAVE_TIME")
    private String leaveTime;
    @Column(name = "LEAVE_STATE")
    private int leaveState;

    // 此處省略建構子、Getter 和 Setter 方法
}
