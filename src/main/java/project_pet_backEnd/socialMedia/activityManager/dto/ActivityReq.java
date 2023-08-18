package project_pet_backEnd.socialMedia.activityManager.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ActivityReq {
    // 建立活動
    String title;
    String content;
    Timestamp startTime;
    Timestamp endTime;
    Timestamp activityTime;
    Integer enrollLimit;
}
