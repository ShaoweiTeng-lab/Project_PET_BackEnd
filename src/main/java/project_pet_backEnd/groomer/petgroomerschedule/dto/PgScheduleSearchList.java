package project_pet_backEnd.groomer.petgroomerschedule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PgScheduleSearchList {
    @Column(name = "PGS_ID")
    private Integer pgsId;
    @Column(name = "PG_NAME")
    private String pgName;
    @Column(name = "PG_ID")
    private Integer pgId;
    @Column(name = "PGS_DATE")
    private Date pgsDate; //sql.date
    @Column(name = "PGS_STATE")
    private String pgsState;//VARCHAR(24) 班表狀態時段 0:可預約 1:不可預約 2:已預約

    // 此處省略建構子、Getter 和 Setter 方法
}
