package project_pet_backEnd.homepage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "Asia/Taipei")
    private Date picRotStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "Asia/Taipei")
    private Date picRotEnd;

}
