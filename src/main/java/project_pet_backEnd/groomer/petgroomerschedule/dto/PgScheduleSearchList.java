package project_pet_backEnd.groomer.petgroomerschedule.dto;

import lombok.Data;
import java.sql.Date;

@Data
public class PgScheduleSearchList {
    private Integer pgsId;
    private String pgName;
    private Integer pgId;
    private Date pgsDate; //sql.date
    private String pgsState;//VARCHAR(24) 班表狀態時段 0:可預約 1:不可預約 2:已預約

    // 此處省略建構子、Getter 和 Setter 方法
}
