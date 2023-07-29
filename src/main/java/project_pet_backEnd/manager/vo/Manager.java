package project_pet_backEnd.manager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.relational.core.sql.In;

@Data
@AllArgsConstructor
public class Manager {
    private Integer managerId;
    private String  managerAccount;
    private String  managerPassword;
    private java.util.Date managerCreated;
    private Integer managerState;
}
