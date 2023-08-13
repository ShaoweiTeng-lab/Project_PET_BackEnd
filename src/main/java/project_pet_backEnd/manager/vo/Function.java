package project_pet_backEnd.manager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@Entity
public class Function {
    @Id
    @Column(name = "FUNCTION_ID")
    private  Integer functionId;
    @Column(name = "FUNCTION_NAME")
    private  String functionName;
}
