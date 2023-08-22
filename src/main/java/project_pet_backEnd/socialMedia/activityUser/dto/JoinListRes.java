package project_pet_backEnd.socialMedia.activityUser.dto;

import lombok.Data;

@Data
public class JoinListRes {
    Integer userId;
    String userName;
    String userPhone;
    String userEmail;

    // 活動資訊
    Integer activityId;
    String activityTitle;
    String activityTime;
    Integer activityStatus;


}
