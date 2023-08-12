package project_pet_backEnd.groomer.petgroomer.dto.request;


import lombok.Data;
import lombok.extern.jbosslog.JBossLog;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class PGInsertReq {
    @NotNull
    private Integer manId;
    @NotBlank
    private String pgName;
    private Integer pgGender;
    @Lob
    private byte[] pgPic;//base64
    @Email
    private String pgEmail;
    private String pgPh;
    private String pgAddress;
    private java.sql.Date pgBirthday;//sql.date
}
