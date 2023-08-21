package project_pet_backEnd.manager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "Permission")
@IdClass(PermissionId.class)
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    @Id
    @Column(name = "MANAGER_ID")
    private Integer managerId;
    @Id
    @Column(name = "function_Id")
    private Integer functionId;
}

class PermissionId implements Serializable {
    private Integer managerId;
    private Integer functionId;
}
