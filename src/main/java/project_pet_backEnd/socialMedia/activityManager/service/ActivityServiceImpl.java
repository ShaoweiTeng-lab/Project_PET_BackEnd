package project_pet_backEnd.socialMedia.activityManager.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.socialMedia.activityManager.dao.ActivityDao;
import project_pet_backEnd.socialMedia.activityManager.dto.ActivityReq;
import project_pet_backEnd.socialMedia.activityManager.vo.Activity;
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
        activity.setStartTime(activityReq.getStartTime());
        activity.setEndTime(activityReq.getEndTime());
        activity.setActivityTime(activityReq.getActivityTime());
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
    public ResultResponse<Activity> update(Activity activity) {
        Optional<Activity> activityOptional = activityDao.findById(activity.getActivityId());
        if (!activityOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "查無此活動");
        }
        Activity updateResult = activityDao.save(activity);
        ResultResponse<Activity> response = new ResultResponse<>();
        response.setMessage(updateResult);
        return response;
    }

    @Override
    public ResultResponse<Activity> cancel(Activity activity) {
        Optional<Activity> activityOptional = activityDao.findById(activity.getActivityId());
        if (!activityOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "查無此活動");
        }
        Activity updateResult = activityDao.save(activity);
        ResultResponse<Activity> response = new ResultResponse<>();
        response.setMessage(updateResult);
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

    //use page and sort return result

    @Override
    public ResultResponse<Page<Activity>> getAllActivities() {

        return null;
    }
}
