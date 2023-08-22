package project_pet_backEnd.groomer.petgroomerschedule.dto.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
public class ScheduleModifyReq {

    @NotNull
    private Integer pgsId;
    @NotNull
    private Integer pgId;
    @NotBlank
    private String pgsDate;//yyyy-mm-dd
    private String pgsState;//24
}
