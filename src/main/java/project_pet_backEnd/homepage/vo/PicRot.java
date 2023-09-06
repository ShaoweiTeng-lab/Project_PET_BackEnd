package project_pet_backEnd.homepage.vo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Entity(name = "PIC_ROTATE")
@Data
public class PicRot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer picNo;
    private String picLocateUrl;
    @Lob
    private byte[] pic;
    private Integer picRotStatus;
    private Date picRotStart;
    private Date picRotEnd;

}
