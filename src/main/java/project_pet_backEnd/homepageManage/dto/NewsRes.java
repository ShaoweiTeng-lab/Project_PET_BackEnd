package project_pet_backEnd.homepageManage.dto;

import lombok.Data;

@Data
public class NewsRes {
    Integer newsNo;
    String newsTitle;
    String newsCont;
    Integer newsStatus;
    String updateTime;
}
