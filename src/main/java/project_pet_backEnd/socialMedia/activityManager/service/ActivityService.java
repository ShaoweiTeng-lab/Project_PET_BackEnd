package project_pet_backEnd.socialMedia.activityManager.service;


import org.springframework.data.domain.Page;
import project_pet_backEnd.socialMedia.activityManager.dto.ActivityReq;
import project_pet_backEnd.socialMedia.activityManager.dto.ActivityRes;
import project_pet_backEnd.socialMedia.activityManager.vo.Activity;
import project_pet_backEnd.socialMedia.util.PageRes;
import project_pet_backEnd.utils.commonDto.ResultResponse;

public interface ActivityService {

    ResultResponse<ActivityRes> create(ActivityReq activityReq);

    ResultResponse<ActivityRes> update(int activityId, ActivityReq activityReq);

    /*
     * 取消活動 更改活動狀態 - 通知所有參與活動的使用者
     */
    ResultResponse<String> cancel(int activityId);

    ResultResponse<ActivityRes> findActivityById(Integer activityId);

    /*
     * 查詢活動列表-反向排序+分頁
     */
    ResultResponse<PageRes<ActivityRes>> getAllActivities(int pageSize, int offset);

    /**
     * 結果轉換
     */

    ResultResponse<ActivityRes> convertToAcRes(Activity activity);

    ResultResponse<PageRes<ActivityRes>> convertToAcPage(Page<Activity> activityPage);
}
