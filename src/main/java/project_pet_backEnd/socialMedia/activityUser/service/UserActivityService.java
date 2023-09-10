package project_pet_backEnd.socialMedia.activityUser.service;


import org.springframework.data.domain.Page;
import org.springframework.data.relational.core.sql.In;
import project_pet_backEnd.socialMedia.activityManager.dto.ActivityRes;
import project_pet_backEnd.socialMedia.activityManager.vo.Activity;
import project_pet_backEnd.socialMedia.activityUser.dto.JoinListRes;
import project_pet_backEnd.socialMedia.activityUser.dto.JoinReq;
import project_pet_backEnd.socialMedia.util.PageRes;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

public interface UserActivityService {
    /*
     * 查看熱門活動(根據參與人數作為排序，列出五筆資料)
     */

    public ResultResponse<List<ActivityRes>> getHotActivities();

    /*
     * 查看最新活動(根據活動Id 排序倒序)
     */

    public ResultResponse<PageRes<ActivityRes>> getAllActivities(int page);


    /*
     * 查看單一 活動資訊
     */

    ResultResponse<ActivityRes> getActivityById(int activityId);


    /**
     * 查詢使用者報名活動紀錄
     */

    ResultResponse<PageRes<JoinListRes>> queryACHistory(Integer userId, Integer page);


    /**
     * 使用者關鍵字搜尋
     */
    ResultResponse<PageRes<ActivityRes>> queryWithText(String content, int page);

    //==============================================//

    /**
     * 使用者報名活動
     */

    ResultResponse<String> joinActivity(Integer userId, Integer activityId, JoinReq joinReq);

    /**
     * 使用者退出活動
     */
    ResultResponse<String> leaveActivity(Integer userId, Integer activityId, JoinReq joinReq);
}
