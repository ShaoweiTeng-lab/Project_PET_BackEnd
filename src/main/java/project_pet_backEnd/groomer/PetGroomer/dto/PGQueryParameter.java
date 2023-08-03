package project_pet_backEnd.groomer.PetGroomer.dto;

import lombok.Data;
import project_pet_backEnd.groomer.PetGroomer.dto.orderby.PGOrderBy;
import project_pet_backEnd.userManager.dto.Sort;
@Data
public class PGQueryParameter {
    private  String search;
    private PGOrderBy order;
    //NUM_APPOINTMENTS,PG_NAME
    private Sort sort;
    private  Integer limit;
    private  Integer offset;
}
