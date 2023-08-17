package project_pet_backEnd.homepage.vo;

import lombok.Data;

import javax.persistence.Lob;
import java.sql.Date;

@Data
public class PicRot {
    private Integer picNo;
    private String picLocateUrl;
    @Lob
    private byte[] pic;
    private Integer picRotStatus;
    private Date picRotStart;
    private Date picRotEnd;

}
