package project_pet_backEnd.manager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@AllArgsConstructor
public class Function {
    private  Integer functionId;
    private  String functionName;
}
