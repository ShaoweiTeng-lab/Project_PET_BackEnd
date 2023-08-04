package project_pet_backEnd.groomer.petgroomer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class ManagerGetByFunctionIdRes {
    @NotBlank
    private Integer managerId;
    @NotBlank
    private String  managerAccount;

}
