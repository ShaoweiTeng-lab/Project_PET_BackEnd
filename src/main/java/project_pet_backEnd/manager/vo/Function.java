package project_pet_backEnd.manager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@AllArgsConstructor
@Entity
public class Function {
    @Column(name = "function_Id")
    private  Integer functionId;
    @Column(name ="function_name")
    private  String functionName;
}
