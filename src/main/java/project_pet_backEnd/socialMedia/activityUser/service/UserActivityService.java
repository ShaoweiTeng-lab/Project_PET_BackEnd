package project_pet_backEnd.socialMedia.activityUser.service;



import project_pet_backEnd.socialMedia.activityManager.vo.Activity;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

public interface UserActivityService {
    /*
     * 查看所有活動-熱門活動排上方(根據參與人數作為排序)
     */

    public ResultResponse<List<Activity>> getAllActivities();

    /*
     * 查看最新活動活動-限制比數5比
     */

    ResultResponse<List<Activity>> getNewestActivities();


    /*
     * 查看單一 活動資訊
     */

    ResultResponse<Activity> getActivityById(int activityId);

}
