package project_pet_backEnd.groomer.petgroomer.dto.response;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Date;
@Data
public class GetAllGroomerListRes {
        @NotNull
        private Integer pgId;
        @NotNull
        private Integer manId;
        private String pgName;
        private String pgGender;//String 男性 / 女性
        private String pgPic;//Base64
        private String pgEmail;
        private String pgPh;
        private String pgAddress;
        private String pgBirthday;//sql.date
        // 此處省略建構子、Getter 和 Setter 方法
}

