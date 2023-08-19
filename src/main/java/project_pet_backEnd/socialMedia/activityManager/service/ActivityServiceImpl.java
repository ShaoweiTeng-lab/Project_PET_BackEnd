package project_pet_backEnd.socialMedia.activityManager.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.socialMedia.activityManager.dao.ActivityDao;
import project_pet_backEnd.socialMedia.activityManager.dto.ActivityReq;
import project_pet_backEnd.socialMedia.activityManager.vo.Activity;
import project_pet_backEnd.socialMedia.util.DateUtils;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDao activityDao;

    @Override
    public ResultResponse<Activity> create(ActivityReq activityReq) {
        Activity activity = new Activity();
        activity.setActivityTitle(activityReq.getTitle());
        activity.setActivityContent(activityReq.getContent());

        // default empId
        activity.setAdminId(1);
        activity.setStatus(1);
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
        ResultResponse<Activity> response = new ResultResponse<>();
        response.setMessage(activity);
        return response;
    }

    @Override
    public ResultResponse<Activity> update(int activityId, ActivityReq activityReq) {
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
        ResultResponse<Activity> response = new ResultResponse<>();
        response.setMessage(updateResult);
        return response;
    }

    @Override
    public ResultResponse<String> cancel(int activityId) {
        Optional<Activity> activityOptional = activityDao.findById(activityId);
        if (!activityOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "查無此活動");
        }
        Activity activity = activityOptional.get();
        activity.setStatus(0);
        Activity updateResult = activityDao.save(activity);
        ResultResponse<String> response = new ResultResponse<>();
        //查看更新狀態結果
        Integer status = updateResult.getStatus();
        if (status != 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "活動取消失敗");
        }

        //這邊將所有參加活動人員email進行通知 write code...

        response.setMessage("活動取消成功");
        return response;
    }

    @Override
    public ResultResponse<Activity> findActivityById(Integer activityId) {
        Optional<Activity> activityOptional = activityDao.findById(activityId);
        if (!activityOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "查無此活動");
        }
        Activity activity = activityOptional.get();
        ResultResponse<Activity> response = new ResultResponse<>();
        response.setMessage(activity);
        return response;
    }


    @Override
    public ResultResponse<Page<Activity>> getAllActivities(int pageSize, int offset) {
        Page<Activity> activityPage = activityDao.findAll(PageRequest.of(pageSize, offset, Sort.Direction.DESC, "activityId"));
        ResultResponse<Page<Activity>> response = new ResultResponse<>();
        response.setMessage(activityPage);
        return response;
    }
}

