package project_pet_backEnd.groomer.petgroomer.vo;

import lombok.Data;
import java.sql.Date;
@Data
public class PetGroomer {
    private Integer pgId;
    private Integer manId;
    private String pgName;
    private Integer pgGender;
    private byte[] pgPic;
    private String pgEmail;
    private String pgPh;
    private String pgAddress;
    private Date pgBirthday;//sql.date

    // 此處省略建構子、Getter 和 Setter 方法
}

