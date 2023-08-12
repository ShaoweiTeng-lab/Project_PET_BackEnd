package project_pet_backEnd.groomer.appointment.dto;

import lombok.Data;
import project_pet_backEnd.userManager.dto.Sort;

@Data
public class UserAppoQueryParameter {
    private  UserAppoOrderBy order;//PGA_DATE
    private Sort sort;
    private  Integer limit;
    private  Integer offset;
}
