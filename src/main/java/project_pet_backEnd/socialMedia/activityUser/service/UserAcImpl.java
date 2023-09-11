package project_pet_backEnd.socialMedia.activityUser.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.socialMedia.activityChat.dao.RoomDao;
import project_pet_backEnd.socialMedia.activityChat.dao.UserDao;
import project_pet_backEnd.socialMedia.activityManager.dao.ActivityDao;
import project_pet_backEnd.socialMedia.activityManager.dao.ActivityDaoImpl;
import project_pet_backEnd.socialMedia.activityManager.dto.ActivityRes;
import project_pet_backEnd.socialMedia.activityManager.service.ActivityServiceImpl;
import project_pet_backEnd.socialMedia.activityManager.vo.Activity;
import project_pet_backEnd.socialMedia.activityUser.dao.UserActivityDao;
import project_pet_backEnd.socialMedia.activityUser.dto.JoinListRes;
import project_pet_backEnd.socialMedia.activityUser.dto.JoinReq;
import project_pet_backEnd.socialMedia.activityUser.vo.JoinActivity;
import project_pet_backEnd.socialMedia.util.PageRes;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserAcImpl implements UserActivityService {

    @Autowired
    private UserActivityDao userJoinDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private ActivityDaoImpl activityImplDao;

    @Autowired
    private ActivityServiceImpl activityService;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private UserDao userDao;


    /**
     * 查詢所有活動
     */

    @Override
    public ResultResponse<PageRes<ActivityRes>> getAllActivities(int page) {
        Page<Activity> activityPage = activityDao.findAll(PageRequest.of(page, 5, Sort.by("status").ascending().and(Sort.by("activityId").descending())));
        ResultResponse<PageRes<ActivityRes>> response = activityService.convertToAcPage(activityPage);
        return response;
    }

    /**
     * 查詢熱門活動
     */
    @Override
    public ResultResponse<List<ActivityRes>> getHotActivities() {
        List<ActivityRes> hotActivities = activityImplDao.getHotActivities();
        ResultResponse<List<ActivityRes>> response = new ResultResponse<>();
        response.setMessage(hotActivities);
        return response;
    }

    /**
     * 查詢活動資訊
     */
    @Override
    public ResultResponse<ActivityRes> getActivityById(int activityId) {
        Optional<Activity> activityOptional = activityDao.findById(activityId);
        if (!activityOptional.isPresent()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "此活動不存在");
        Activity activity = activityOptional.get();
        ResultResponse<ActivityRes> response = activityService.convertToAcRes(activity);
        return response;
    }


    /**
     * 關鍵字搜尋(活動內容)
     */
    @Override
    public ResultResponse<PageRes<ActivityRes>> queryWithText(String content, int page) {
        Page<Activity> activityPage = activityDao.findByActivityContentContaining(content, PageRequest.of(page, 5, Sort.Direction.DESC, "activityTime"));
        ResultResponse<PageRes<ActivityRes>> response = activityService.convertToAcPage(activityPage);
        return response;
    }

    /**
     * user 加入活動
     */

    @Override
    public ResultResponse<String> joinActivity(Integer userId, Integer activityId, JoinReq joinReq) {
        JoinActivity joinData = new JoinActivity();
        joinData.setActivityId(activityId);
        joinData.setUserId(userId);
        joinData.setStatus(0);
        joinData.setPeopleCount(joinReq.getCount());


        //活動加入成功後必須在該活動上加入人數
        Optional<Activity> activity = activityDao.findById(activityId);
        if (!activity.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "沒有此活動");
        }
        Activity activityCount = activity.get();
        //人數限制<(目前參加人數+新的參加人數)
        if (activityCount.getEnrollLimit() < (activityCount.getEnrollTotal() + joinReq.getCount())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "已經超過活動報名的人數限制，請注意人數");
        }
        //是否已經參加過活動
        JoinActivity joinExist = userJoinDao.findByActivityIdAndUserId(activityId, userId);
        if (joinExist != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "已經報名過，無法再次報名");
        }
        //修改活動參加人數並請新增參假者清單
        activityCount.setEnrollTotal((activityCount.getEnrollTotal() + joinReq.getCount()));


        try {
            userJoinDao.save(joinData);
            activityDao.save(activityCount);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "活動加入失敗");
        }

        //redis 1.加入使用者資訊 create user 2.為活動加入此使用者資訊 3.為使用者活動聊天室加入此筆活動資訊
        //檢查使用者是否存在redis快取中
        boolean exists = userDao.checkUserExists(userId);

        if (!exists) {
            userDao.createUser(userId);
        }
        //活動聊天室使用者清單
        roomDao.createRoomUserList(activityId, userId);
        //使用者新增一筆活動聊天室列表
        roomDao.addRoomKeyToUser(userId, activityId);


        ResultResponse<String> response = new ResultResponse<>();
        response.setMessage("活動加入成功");
        return response;
    }

    /**
     * user 退出活動
     */
    @Override
    public ResultResponse<String> leaveActivity(Integer userId, Integer activityId) {
        String leaveResult = activityImplDao.leaveActivity(userId, activityId);
        ResultResponse<String> response = new ResultResponse<>();
        response.setMessage(leaveResult);
        return response;
    }

    /**
     * user 查詢參加活動歷史清單
     */
    @Override
    public ResultResponse<PageRes<JoinListRes>> queryACHistory(Integer userId, Integer page) {
        Page<JoinActivity> joinPage = userJoinDao
                .findAllByUserId(userId, PageRequest.of(page, 10, Sort.by("enterTime").descending().and(Sort.by("status").descending())));
        ResultResponse<PageRes<JoinListRes>> response = activityService.convertToJoinListPage(joinPage);
        return response;
    }
}
