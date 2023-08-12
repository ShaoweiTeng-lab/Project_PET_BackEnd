package project_pet_backEnd.groomer.petgroomer.dto.request;

import lombok.Data;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class GetAllGroomerListReq {
        @NotNull
        private Integer pgId;
        @NotNull
        private Integer manId;
        private String pgName;
        private Integer pgGender;//0 女性 1男性
        @Lob
        private byte[] pgPic;
        @Email
        private String pgEmail;
        private String pgPh;
        private String pgAddress;
        private java.sql.Date pgBirthday;//sql.date
        // 此處省略建構子、Getter 和 Setter 方法
}

