package project_pet_backEnd.groomer.petgroomer.dto;

import lombok.Data;
import project_pet_backEnd.groomer.petgroomer.dto.orderby.PGOrderBy;
import project_pet_backEnd.userManager.dto.Sort;
@Data
public class PGQueryParameter {
    private  String search;
    private PGOrderBy order;
    private Integer porId;
    private Integer userId;
    private Integer pgId;
    //NUM_APPOINTMENTS,PG_NAME
    private Sort sort;
    private  Integer limit;
    private  Integer offset;
}
