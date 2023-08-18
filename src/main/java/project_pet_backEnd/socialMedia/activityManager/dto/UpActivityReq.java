package project_pet_backEnd.socialMedia.activityManager.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UpActivityReq {
    Integer activityId;
    String title;
    String content;
    Timestamp startTime;
    Timestamp endTime;
    Timestamp activityTime;
    Integer enrollLimit;
}
