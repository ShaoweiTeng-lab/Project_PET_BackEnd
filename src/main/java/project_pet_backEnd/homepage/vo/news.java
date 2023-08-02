package project_pet_backEnd.homepage.vo;

import lombok.Data;
import java.sql.Date;

@Data
public class news {
    private Integer newsNo;
    private String newsTitle;
    private String newsCont;
    private Integer newsStatus;
    private Date updateTime;

}
