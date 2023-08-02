package project_pet_backEnd.manager.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateManagerRequest {
    @NotBlank(message = "managerAccount不可為空")
    private  String managerAccount;
    @NotBlank(message = "managerPassword不可為空")
    private  String managerPassword;
}
