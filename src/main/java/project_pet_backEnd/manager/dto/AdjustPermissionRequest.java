package project_pet_backEnd.manager.dto;



import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;
@Data
public class AdjustPermissionRequest {
    @NotBlank
    private String account;
    private List<ManagerAuthorities>  authorities;
}
