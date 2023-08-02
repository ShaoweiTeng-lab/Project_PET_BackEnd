package project_pet_backEnd.userManager.dto;

import lombok.Data;

@Data
public class UserQueryParameter {
    private  String search;
    private  UserOrderBy order;
    private  Sort sort;
    private  Integer limit;
    private  Integer offset;
}
