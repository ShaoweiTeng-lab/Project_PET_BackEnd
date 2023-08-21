package project_pet_backEnd.groomer.petgroomerschedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetScheduleRes {
    private Integer pgsId;
    private String pgName;
    private Integer pgId;
    private String pgsDate;
    private String pgsState;//VARCHAR(24) 班表狀態時段 0:可預約 1:不可預約 2:已預約

}
