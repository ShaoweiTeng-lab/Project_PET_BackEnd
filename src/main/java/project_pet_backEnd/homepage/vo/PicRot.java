package project_pet_backEnd.homepage.vo;

import lombok.Data;
import java.sql.Date;

@Data
public class PicRot {
    private Integer picNo;
    private Integer pdNo;
    private Integer activityId;
    private byte[] pic;
    private Integer picRotStatus;
    private Date picRotStart;
    private Date picRotEnd;

}
