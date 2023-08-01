package project_pet_backEnd.groomer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ManagerGetByFunctionIdRequest {

    private Integer managerId;

    private String  managerAccount;

}
