package project_pet_backEnd.homepage.vo;

import lombok.Data;
import java.sql.Date;

@Data
public class picrot {
    private Integer picNo;
    private Integer pdNo;
    private String activityId;
    private String pic;
    private Integer picRotStatus;
    private Date picRotStart;
    private Date picRotEnd;

}
