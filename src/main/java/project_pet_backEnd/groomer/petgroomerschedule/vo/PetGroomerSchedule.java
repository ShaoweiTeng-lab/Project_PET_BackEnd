package project_pet_backEnd.groomer.petgroomerschedule.vo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
@Table(name = "PET_GROOMER_SCHEDULE")
@Data
public class PetGroomerSchedule {
    @Id
    @Column(name = "PGS_ID")
    private Integer pgsId;
    @Column(name = "PG_ID")
    private Integer pgId;
    @Column(name = "PGS_DATE")
    private Date pgsDate; //sql.date
    @Column(name = "PGS_STATE")
    private String pgsState;//VARCHAR(24) 班表狀態時段 0:可預約 1:不可預約 2:已預約

    // 此處省略建構子、Getter 和 Setter 方法
}
