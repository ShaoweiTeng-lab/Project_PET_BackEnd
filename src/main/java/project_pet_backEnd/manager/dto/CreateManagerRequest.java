package project_pet_backEnd.manager.dto;

import lombok.Data;

@Data
public class CreateManagerRequest {
    private  String managerAccount;
    private  String managerPassword;
}
