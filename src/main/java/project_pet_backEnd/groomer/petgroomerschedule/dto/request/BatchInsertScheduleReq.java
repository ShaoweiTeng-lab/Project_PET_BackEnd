package project_pet_backEnd.groomer.petgroomerschedule.dto.request;

import lombok.Data;
import javax.validation.constraints.*;

@Data
public class BatchInsertScheduleReq {
    @NotNull
    private Integer pgId;
    @NotNull
    @Min(1990) @Max(3000)
    private Integer year;
    @NotNull
    @Min(1) @Max(12)
    private Integer month;
    @Size(min = 24, max = 24)
    private String pgsState;//24
}
