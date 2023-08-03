package project_pet_backEnd.groomer.dto;

import lombok.Data;
import project_pet_backEnd.userManager.dto.Sort;
@Data
public class PetGroomerQueryParameter {
    private  String search;
    private PetGrommerOrderBy order;
    //NUM_APPOINTMENTS,PG_NAME
    private Sort sort;
    private  Integer limit;
    private  Integer offset;
}
