package project_pet_backEnd.socialMedia.activityUser.service;


import org.springframework.stereotype.Service;
import project_pet_backEnd.socialMedia.activityManager.vo.Activity;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;
@Service
public class UserAcImpl implements UserActivityService{

    @Override
    public ResultResponse<List<Activity>> getAllActivities() {
        return null;
    }

    @Override
    public ResultResponse<List<Activity>> getNewestActivities() {
        return null;
    }

    @Override
    public ResultResponse<Activity> getActivityById(int activityId) {
        return null;
    }
}
