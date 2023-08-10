package project_pet_backEnd.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class ManagerProfileResponse {
    private String  managerAccount;
    private String managerCreated;
    private String managerState;
}
