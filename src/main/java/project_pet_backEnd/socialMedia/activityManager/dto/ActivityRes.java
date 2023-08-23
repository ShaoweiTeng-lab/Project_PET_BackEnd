package project_pet_backEnd.socialMedia.activityManager.dto;

import lombok.Data;

@Data
public class ActivityRes {
    Integer activityId;
    String title;
    String content;
    String startTime;
    String endTime;
    String activityTime;
    byte[] activityPicture;
    Integer enrollLimit;
    Integer peopleCount;
    Integer status;
}
