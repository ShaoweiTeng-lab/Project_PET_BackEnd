package project_pet_backEnd.manager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "manager")
public class Manager {
    @Id
    @Column(name = "MANAGER_ID")
    private Integer managerId;
    @Column(name = "MANAGER_ACCOUNT")
    private String  managerAccount;
    @Column(name = "MANAGER_PASSWORD")
    private String  managerPassword;
    @Column(name = "MANAGER_CREATED")
    private java.util.Date managerCreated;
    @Column(name = "MANAGER_STATE")
    private Integer managerState;
}
