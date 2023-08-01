package project_pet_backEnd.manager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.*;
@DynamicInsert
@DynamicUpdate
@Data
@Entity
@Table(name = "manager")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MANAGER_ID")
    private Integer managerId;
    @Column(name = "MANAGER_ACCOUNT")
    private String  managerAccount;
    @Column(name = "MANAGER_PASSWORD")
    private String  managerPassword;
    @Column(name = "MANAGER_CREATED" , columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private java.util.Date managerCreated;
    @Column(name = "MANAGER_STATE" , columnDefinition = "TINYINT NOT NULL DEFAULT 1")
    private Integer managerState;
}
