package project_pet_backEnd.homepageManage.dto;

import lombok.Data;

@Data
public class HomepageNewsRes {
    Integer newsNo;
    String newsTitle;
    // base64 String
    String pic;
}
