package project_pet_backEnd.groomer.petgroomer.vo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
@Table(name = "PET_GROOMER")
@Data
public class PetGroomer {
    @Id
    @Column(name = "PG_ID")
    private Integer pgId;
    @Column(name = "MAN_ID")
    private Integer manId;
    @Column(name = "PG_NAME")
    private String pgName;
    @Column(name = "PG_GENDER")
    private Integer pgGender;
    @Column(name = "PG_PIC")
    private byte[] pgPic;
    @Column(name = "PG_EMAIL")
    private String pgEmail;
    @Column(name = "PG_PH")
    private String pgPh;
    @Column(name = "PG_ADDRESS")
    private String pgAddress;
    @Column(name = "PG_BIRTHDAY")
    private Date pgBirthday;//sql.date

    // 此處省略建構子、Getter 和 Setter 方法
}

