package project_pet_backEnd.manager.dto;

import lombok.Data;
import org.springframework.data.relational.core.sql.In;

@Data
public class QueryManagerParameter {
   private Integer limit;
    private Integer offset;
}
