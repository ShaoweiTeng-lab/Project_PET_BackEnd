package project_pet_backEnd.groomer.vo;

import lombok.Data;
import java.sql.Date;
@Data
public class PetGroomer {
    private int pgId;
    private int manId;
    private String pgName;
    private int pgGender;
    private byte[] pgPic;
    private String pgEmail;
    private String pgPh;
    private String pgAddress;
    private Date pgBirthday;//sql.date

    // 此處省略建構子、Getter 和 Setter 方法
}

