package project_pet_backEnd.groomer.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class PetGroomerInsertRequest {
    private int manId;
    private String pgName;
    private int pgGender;
    private String pgPic;//base64
    private String pgEmail;
    private String pgPh;
    private String pgAddress;
    private Date pgBirthday;//sql.date
}
