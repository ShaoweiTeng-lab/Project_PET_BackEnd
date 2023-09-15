package project_pet_backEnd.homepageManage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Lob;
import java.util.Date;

@Data
public class PicRotRes {
    Integer picNo;
    String picLocateUrl;
    String pic;
    Integer picRotStatus;
    String picRotStart;
    String picRotEnd;

}
