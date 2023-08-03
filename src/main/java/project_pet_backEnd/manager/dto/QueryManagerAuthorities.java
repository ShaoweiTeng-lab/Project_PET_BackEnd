package project_pet_backEnd.manager.dto;

import lombok.Data;

import java.util.List;
@Data
public class QueryManagerAuthorities {
    private String managerAccount;
    private List<ManagerAuthorities> managerAuthoritiesList;
}
