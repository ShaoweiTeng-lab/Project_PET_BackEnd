package project_pet_backEnd.groomer.petgroomerschedule.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "PET_GROOMER_SCHEDULE")
@AllArgsConstructor
@NoArgsConstructor
public class PetGroomerSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PGS_ID",insertable = false,updatable = false)
    private Integer pgsId;
    @Column(name = "PG_ID")
    private Integer pgId;
    @Column(name = "PGS_DATE")
    private Date pgsDate; //sql.date
    @Column(name = "PGS_STATE")
    private String pgsState;//VARCHAR(24) 班表狀態時段 0:可預約 1:不可預約 2:已預約

    // 此處省略建構子、Getter 和 Setter 方法
}
