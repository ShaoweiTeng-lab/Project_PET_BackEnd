package project_pet_backEnd.manager.dto;

import lombok.Data;

@Data
public class ManagerLoginRequest {
    private  String managerAccount;
    private  String managerPassword;
}
