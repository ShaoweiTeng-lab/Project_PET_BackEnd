package project_pet_backEnd.socialMedia.activityUser.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.socialMedia.activityManager.dao.ActivityDao;
import project_pet_backEnd.socialMedia.activityManager.dao.ActivityDaoImpl;
import project_pet_backEnd.socialMedia.activityManager.dto.ActivityRes;
import project_pet_backEnd.socialMedia.activityManager.vo.Activity;
import project_pet_backEnd.socialMedia.activityUser.dao.UserActivityDao;
import project_pet_backEnd.socialMedia.activityUser.dto.JoinReq;
import project_pet_backEnd.socialMedia.activityUser.vo.JoinActivity;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;
import java.util.Optional;

@Service
public class UserAcImpl implements UserActivityService{

    private UserActivityDao userJoinDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private ActivityDaoImpl activityImplDao;

    @Override
    public ResultResponse<Page<Activity>> getAllActivities(int page) {
        Page<Activity> activities = activityDao.findAll(PageRequest.of(page, 10, Sort.Direction.DESC, "activityId"));

        ResultResponse<Page<Activity>> response = new ResultResponse<>();
        response.setMessage(activities);
        return response;
    }

    @Override
    public ResultResponse<List<ActivityRes>> getHotActivities() {
        List<ActivityRes> hotActivities = activityImplDao.getHotActivities();
        ResultResponse<List<ActivityRes>> response = new ResultResponse<>();
        response.setMessage(hotActivities);
        return response;
    }

    @Override
    public ResultResponse<Activity> getActivityById(int activityId) {
        Optional<Activity> activity = activityDao.findById(activityId);
        if (!activity.isPresent()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "此活動不存在");
        Activity activityData = activity.get();
        ResultResponse<Activity> response = new ResultResponse<>();
        response.setMessage(activityData);
        return response;
    }

    //關鍵字搜尋(活動內容)
    @Override
    public ResultResponse<Page<Activity>> queryWithText(String content) {
        Page<Activity> activityPage = activityDao.findByActivityContentContaining(content, PageRequest.of(0, 10, Sort.Direction.DESC, "activityTime"));
        ResultResponse<Page<Activity>> response = new ResultResponse<>();
        response.setMessage(activityPage);
        return response;
    }


    @Override
    @Transactional
    public ResultResponse<String> joinActivity(Integer userId, JoinReq joinReq) {
        JoinActivity joinData = new JoinActivity();
        joinData.setActivityId(joinReq.getActivityId());
        joinData.setUserId(userId);
        joinData.setStatus(0);
        joinData.setPeopleCount(joinReq.getCount());


        //活動加入成功後必須在該活動上加入人數
        Optional<Activity> activity = activityDao.findById(joinReq.getActivityId());
        if (!activity.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "沒有此活動");
        }
        Activity activityCount = activity.get();
        //人數限制<(目前參加人數+新的參加人數)
        if (activityCount.getEnrollLimit() < (activityCount.getEnrollTotal() + joinReq.getCount())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "已經超過活動報名的人數限制，請注意人數");
        }
        //是否已經參加過活動
        JoinActivity joinExist = userJoinDao.findByActivityIdAndUserId(userId, joinReq.getActivityId());

        if (joinExist != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "已經報名過，無法再次報名");
        }


        JoinActivity joinResult = userJoinDao.save(joinData);
        activityCount.setEnrollTotal((activityCount.getEnrollTotal() + joinReq.getCount()));
        Activity saveResult = activityDao.save(activityCount);

        if (joinResult == null || saveResult == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "活動加入失敗");
        }

        ResultResponse<String> response = new ResultResponse<>();
        response.setMessage("活動加入成功");
        return response;
    }

    @Override
    public ResultResponse<String> leaveActivity(Integer userId, JoinReq joinReq) {
        String leaveResult = activityImplDao.leaveActivity(userId, joinReq);
        ResultResponse<String> response = new ResultResponse<>();
        response.setMessage(leaveResult);
        return response;
    }

    //使用者參加活動歷史清單
    @Override
    public ResultResponse<Page<Activity>> queryACHistory(Integer userId) {
        return null;
    }

}
