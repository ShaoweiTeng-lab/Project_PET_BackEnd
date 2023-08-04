package project_pet_backEnd.groomer.petgroomer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class ManagerGetByFunctionIdReq {
    @NotBlank
    private Integer managerId;
    @NotBlank
    private String  managerAccount;

}
