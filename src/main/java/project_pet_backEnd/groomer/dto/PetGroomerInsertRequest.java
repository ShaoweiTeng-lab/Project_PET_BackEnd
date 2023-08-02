package project_pet_backEnd.groomer.dto;


import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class PetGroomerInsertRequest {
    @NotBlank
    private int manId;
    @NotBlank
    private String pgName;
    @Min(0)
    @Max(1)
    private int pgGender;
    private String pgPic;//base64
    private String pgEmail;
    private String pgPh;
    private String pgAddress;
    private Date pgBirthday;//sql.date
}
