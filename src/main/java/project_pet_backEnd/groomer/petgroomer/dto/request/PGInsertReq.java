package project_pet_backEnd.groomer.petgroomer.dto.request;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Data
public class PGInsertReq {
    @NotBlank
    private Integer manId;
    @NotBlank
    private String pgName;
    private String pgGender;//String "男性","女性"
    private String pgPic;//base64
    @Email
    private String pgEmail;
    private String pgPh;
    private String pgAddress;
    private Date pgBirthday;//sql.date
}
