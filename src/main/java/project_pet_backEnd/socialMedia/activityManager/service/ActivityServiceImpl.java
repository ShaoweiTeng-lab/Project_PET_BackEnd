package project_pet_backEnd.socialMedia.activityManager.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.socialMedia.activityManager.dao.ActivityDao;
import project_pet_backEnd.socialMedia.activityManager.dto.ActivityReq;
import project_pet_backEnd.socialMedia.activityManager.dto.ActivityRes;
import project_pet_backEnd.socialMedia.activityManager.vo.Activity;
import project_pet_backEnd.socialMedia.activityUser.dto.JoinListRes;
import project_pet_backEnd.socialMedia.activityUser.vo.JoinActivity;
import project_pet_backEnd.socialMedia.util.DateUtils;
import project_pet_backEnd.socialMedia.util.PageRes;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDao activityDao;

    /**
     * 建立活動
     */
    @Override
    public ResultResponse<ActivityRes> create(ActivityReq activityReq) {
        Activity activity = new Activity();
        activity.setActivityTitle(activityReq.getTitle());
        activity.setActivityContent(activityReq.getContent());

        // default empId
        activity.setAdminId(1);
        /**
         * 0: 執行中
         * 1: 已取消
         */
        activity.setStatus(0);
        activity.setEnrollTotal(0);

        activity.setStartTime(DateUtils.dateStrToSql(activityReq.getStartTime()));
        activity.setEndTime(DateUtils.dateStrToSql(activityReq.getEndTime()));
        activity.setActivityTime(DateUtils.dateTimeStrToSql(activityReq.getActivityTime()));
        activity.setEnrollLimit(activityReq.getEnrollLimit());
        Activity createResult = activityDao.save(activity);
        //判斷是否建立成功
        if (createResult == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "活動建立失敗");
        }

        ActivityRes activityRes = new ActivityRes();
        activityRes.setActivityId(createResult.getActivityId());
        activityRes.setTitle(createResult.getActivityTitle());
        activityRes.setContent(createResult.getActivityContent());
        activityRes.setActivityPicture(createResult.getActivityPicture());
        activityRes.setPeopleCount(createResult.getEnrollTotal());
        activityRes.setEnrollLimit(createResult.getEnrollLimit());
        activityRes.setStatus(createResult.getStatus());
        //time
        activityRes.setActivityTime(DateUtils.dateTimeSqlToStr(createResult.getActivityTime()));
        activityRes.setStartTime(DateUtils.dateSqlToStr(createResult.getStartTime()));
        activityRes.setEndTime(DateUtils.dateSqlToStr(createResult.getEndTime()));

        ResultResponse<ActivityRes> response = new ResultResponse<>();
        response.setMessage(activityRes);
        return response;
    }

    /**
     * 更新活動內容
     */
    @Override
    public ResultResponse<ActivityRes> update(int activityId, ActivityReq activityReq) {
        Optional<Activity> activityOptional = activityDao.findById(activityId);
        if (!activityOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "查無此活動");
        }
        Activity activity = activityOptional.get();
        activity.setActivityTitle(activityReq.getTitle());
        activity.setActivityContent(activityReq.getContent());
        activity.setEnrollLimit(activityReq.getEnrollLimit());
        activity.setActivityTime(DateUtils.dateTimeStrToSql(activityReq.getActivityTime()));
        activity.setStartTime(DateUtils.dateStrToSql(activityReq.getStartTime()));
        activity.setEndTime(DateUtils.dateStrToSql(activityReq.getEndTime()));
        Activity updateResult = activityDao.save(activity);

        //res setting
        ActivityRes activityRes = new ActivityRes();
        activityRes.setActivityId(updateResult.getActivityId());
        activityRes.setTitle(updateResult.getActivityTitle());
        activityRes.setContent(updateResult.getActivityContent());
        activityRes.setActivityPicture(updateResult.getActivityPicture());
        activityRes.setPeopleCount(updateResult.getEnrollTotal());
        activityRes.setEnrollLimit(updateResult.getEnrollLimit());
        activityRes.setStatus(updateResult.getStatus());
        //time
        activityRes.setActivityTime(DateUtils.dateTimeSqlToStr(updateResult.getActivityTime()));
        activityRes.setStartTime(DateUtils.dateSqlToStr(updateResult.getStartTime()));
        activityRes.setEndTime(DateUtils.dateSqlToStr(updateResult.getEndTime()));

        ResultResponse<ActivityRes> response = new ResultResponse<>();
        response.setMessage(activityRes);
        return response;
    }

    /**
     * 取消活動
     */

    @Override
    public ResultResponse<String> cancel(int activityId) {
        Optional<Activity> activityOptional = activityDao.findById(activityId);
        if (!activityOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "查無此活動");
        }
        Activity activity = activityOptional.get();
        /**
         * 0: 執行中
         * 1: 已取消
         */
        activity.setStatus(1);
        Activity updateResult = activityDao.save(activity);
        ResultResponse<String> response = new ResultResponse<>();
        //查看更新狀態結果
        Integer status = updateResult.getStatus();
        if (status != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "活動取消失敗");
        }

        //這邊將所有參加活動人員email進行通知 write code...

        response.setMessage("活動取消成功");
        return response;
    }

    /**
     * 查詢單一 活動資訊
     */

    @Override
    public ResultResponse<ActivityRes> findActivityById(Integer activityId) {
        Optional<Activity> activityOptional = activityDao.findById(activityId);
        if (!activityOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "查無此活動");
        }
        Activity activity = activityOptional.get();
        ResultResponse<ActivityRes> response = new ResultResponse<>();

        //res setting
        ActivityRes activityRes = new ActivityRes();
        activityRes.setActivityId(activity.getActivityId());
        activityRes.setTitle(activity.getActivityTitle());
        activityRes.setContent(activity.getActivityContent());
        activityRes.setActivityPicture(activity.getActivityPicture());
        activityRes.setPeopleCount(activity.getEnrollTotal());
        activityRes.setEnrollLimit(activity.getEnrollLimit());
        activityRes.setStatus(activity.getStatus());
        //time
        activityRes.setActivityTime(DateUtils.dateTimeSqlToStr(activity.getActivityTime()));
        activityRes.setStartTime(DateUtils.dateSqlToStr(activity.getStartTime()));
        activityRes.setEndTime(DateUtils.dateSqlToStr(activity.getEndTime()));
        response.setMessage(activityRes);
        return response;
    }

    /**
     * 查詢所有活動
     */

    @Override
    public ResultResponse<PageRes<ActivityRes>> getAllActivities(int pageSize, int offset) {
        Page<Activity> activityPage = activityDao.findAll(PageRequest.of(pageSize, offset, Sort.Direction.DESC, "activityId"));
        List<ActivityRes> activityResultList = new ArrayList<>();
        List<Activity> activities = new ArrayList<>();
        if (activityPage != null && activityPage.hasContent()) {
            activities = activityPage.getContent();
        }

        for (Activity activity : activities) {
            ActivityRes activityRes = new ActivityRes();
            activityRes.setActivityId(activity.getActivityId());
            activityRes.setTitle(activity.getActivityTitle());
            activityRes.setContent(activity.getActivityContent());
            activityRes.setActivityPicture(activity.getActivityPicture());
            activityRes.setPeopleCount(activity.getEnrollTotal());
            activityRes.setEnrollLimit(activity.getEnrollLimit());
            activityRes.setStatus(activity.getStatus());
            //time
            activityRes.setActivityTime(DateUtils.dateTimeSqlToStr(activity.getActivityTime()));
            activityRes.setStartTime(DateUtils.dateSqlToStr(activity.getStartTime()));
            activityRes.setEndTime(DateUtils.dateSqlToStr(activity.getEndTime()));
            activityResultList.add(activityRes);
        }

        PageRes pageRes = new PageRes();
        pageRes.setResList(activityResultList);
        pageRes.setCurrentPageNumber(activityPage.getNumber());
        pageRes.setPageSize(activityPage.getSize());
        pageRes.setTotalPage(activityPage.getTotalPages());
        pageRes.setCurrentPageDataSize(activityPage.getNumberOfElements());

        ResultResponse<PageRes<ActivityRes>> response = new ResultResponse<>();
        response.setMessage(pageRes);
        return response;
    }

    // ====================== 轉換成 ActivityRes ======================//

    public ResultResponse<ActivityRes> convertToAcRes(Activity activity) {
        ResultResponse<ActivityRes> response = new ResultResponse<>();
        //res setting
        ActivityRes activityRes = new ActivityRes();
        activityRes.setActivityId(activity.getActivityId());
        activityRes.setTitle(activity.getActivityTitle());
        activityRes.setContent(activity.getActivityContent());
        activityRes.setActivityPicture(activity.getActivityPicture());
        activityRes.setPeopleCount(activity.getEnrollTotal());
        activityRes.setEnrollLimit(activity.getEnrollLimit());
        activityRes.setStatus(activity.getStatus());
        //time
        activityRes.setActivityTime(DateUtils.dateTimeSqlToStr(activity.getActivityTime()));
        activityRes.setStartTime(DateUtils.dateSqlToStr(activity.getStartTime()));
        activityRes.setEndTime(DateUtils.dateSqlToStr(activity.getEndTime()));
        response.setMessage(activityRes);

        return response;

    }

    // ====================== 轉換成 page<ActivityRes> ======================//

    public ResultResponse<PageRes<ActivityRes>> convertToAcPage(Page<Activity> activityPage) {
        List<ActivityRes> activityResultList = new ArrayList<>();
        List<Activity> activities = new ArrayList<>();
        if (activityPage != null && activityPage.hasContent()) {
            activities = activityPage.getContent();
        }

        for (Activity activity : activities) {
            ActivityRes activityRes = new ActivityRes();
            activityRes.setActivityId(activity.getActivityId());
            activityRes.setTitle(activity.getActivityTitle());
            activityRes.setContent(activity.getActivityContent());
            activityRes.setActivityPicture(activity.getActivityPicture());
            activityRes.setPeopleCount(activity.getEnrollTotal());
            activityRes.setEnrollLimit(activity.getEnrollLimit());
            activityRes.setStatus(activity.getStatus());
            //time
            activityRes.setActivityTime(DateUtils.dateTimeSqlToStr(activity.getActivityTime()));
            activityRes.setStartTime(DateUtils.dateSqlToStr(activity.getStartTime()));
            activityRes.setEndTime(DateUtils.dateSqlToStr(activity.getEndTime()));
            activityResultList.add(activityRes);
        }

        PageRes pageRes = new PageRes();
        pageRes.setResList(activityResultList);
        pageRes.setCurrentPageNumber(activityPage.getNumber());
        pageRes.setPageSize(activityPage.getSize());
        pageRes.setTotalPage(activityPage.getTotalPages());
        pageRes.setCurrentPageDataSize(activityPage.getNumberOfElements());

        ResultResponse<PageRes<ActivityRes>> response = new ResultResponse<>();
        response.setMessage(pageRes);
        return response;
    }

    public ResultResponse<PageRes<JoinListRes>> convertToJoinListPage(Page<JoinActivity> joinPage) {

        List<JoinActivity> joinAcList = new ArrayList<>();
        List<JoinListRes> joinListResultList = new ArrayList<>();

        if (joinPage != null && joinPage.hasContent()) {
            joinAcList = joinPage.getContent();
        }

        for (JoinActivity join : joinAcList) {
            JoinListRes joinListRes = new JoinListRes();
            joinListRes.setUserId(join.getUserId());
            joinListRes.setUserName(join.getUser().getUserName());
            joinListRes.setUserEmail(join.getUser().getUserEmail());
            joinListRes.setUserPhone(join.getUser().getUserPhone());

            joinListRes.setActivityId(join.getActivityId());
            joinListRes.setActivityTitle(join.getActivity().getActivityTitle());
            joinListRes.setActivityTime(DateUtils.dateTimeSqlToStr(join.getActivity().getActivityTime()));
            joinListRes.setActivityStatus(join.getActivity().getStatus());


            joinListResultList.add(joinListRes);
        }

        PageRes pageRes = new PageRes();
        pageRes.setResList(joinListResultList);
        pageRes.setCurrentPageNumber(joinPage.getNumber());
        pageRes.setPageSize(joinPage.getSize());
        pageRes.setTotalPage(joinPage.getTotalPages());
        pageRes.setCurrentPageDataSize(joinPage.getNumberOfElements());

        ResultResponse<PageRes<JoinListRes>> response = new ResultResponse<>();
        response.setMessage(pageRes);
        return response;
    }
}

