package project_pet_backEnd.socialMedia.activityManager.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.socialMedia.activityChat.dao.RoomDao;
import project_pet_backEnd.socialMedia.activityManager.dto.ActivityRes;
import project_pet_backEnd.socialMedia.activityManager.vo.Activity;
import project_pet_backEnd.socialMedia.activityUser.dto.JoinReq;
import project_pet_backEnd.socialMedia.activityUser.vo.JoinActivity;
import project_pet_backEnd.socialMedia.util.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ActivityDaoImpl {
    @Autowired
    private NamedParameterJdbcTemplate template;

    @Autowired
    private RoomDao roomDao;


    //查詢熱門活動
    public List<ActivityRes> getHotActivities() {
        String sql = "SELECT * FROM activity\n" +
                "where activity_status = 0\n" +
                "order by enrollment_count desc\n" +
                "limit 5\n";
        MapSqlParameterSource params = new MapSqlParameterSource();

        List<ActivityRes> activityList =
                template.query(sql, params, (rs, rowNum) -> {
                    ActivityRes activityRes = new ActivityRes();
                    activityRes.setActivityId(rs.getInt("ACTIVITY_ID"));
                    activityRes.setTitle(rs.getString("ACTIVITY_TITLE"));
                    activityRes.setContent(rs.getString("ACTIVITY_CONTENT"));
                    activityRes.setStatus(rs.getInt("ACTIVITY_STATUS"));
                    activityRes.setStartTime(DateUtils.dateSqlToStr(rs.getDate("ENROLL_START")));
                    activityRes.setEndTime(DateUtils.dateSqlToStr(rs.getDate("ENROLL_END")));
                    activityRes.setActivityTime(DateUtils.dateTimeSqlToStr(rs.getTimestamp("ACTIVITY_TIME")));
                    activityRes.setPeopleCount(rs.getInt("ENROLLMENT_COUNT"));
                    activityRes.setEnrollLimit(rs.getInt("ENROLLMENT_LIMIT"));

                    return activityRes;
                });


        return activityList;

    }


    //退出活動
    public String leaveActivity(int userId, int activityId) {

        String querySql = "select * FROM activity\n" +
                "where activity_id = :activityId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("activityId", activityId);

        Activity queryData =
                template.queryForObject(querySql, params, (rs, rowNum) -> {
                    Activity activity = new Activity();
                    activity.setActivityId(rs.getInt("ACTIVITY_ID"));
                    activity.setActivityTime(rs.getTimestamp("ACTIVITY_TIME"));
                    activity.setEnrollTotal((rs.getInt("ENROLLMENT_COUNT")));
                    return activity;
                });

        //比對時間-決定使用者是否可以取消活動(假設距離活動小於三天就不能取消)
        int queryDate = (int) (queryData.getActivityTime().getTime() / (1000 * 60 * 60 * 24));
        int nowDate = (int) (System.currentTimeMillis() / (1000 * 60 * 60 * 24));
        if ((queryDate - nowDate) < 3) {
            return "你已經超過可以退出活動的時間";
        } else {

            //改變使用者參加狀態
            String userStatusUpdate = "update activity_participation set enter_status = 1\n" +
                    "where activity_id = :activityId AND user_id = :userId";
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("activityId", activityId);
            userMap.put("userId", userId);
            template.update(userStatusUpdate, userMap);

            //查詢user參加活動清單人數

            String enrollCountSql = "select * FROM activity_participation\n" +
                    "where activity_id = :activityId AND user_id = :userId";

            MapSqlParameterSource countParams = new MapSqlParameterSource();
            countParams.addValue("activityId", activityId);
            countParams.addValue("userId", userId);

            JoinActivity queryJoinData =
                    template.queryForObject(enrollCountSql, countParams, (rs, rowNum) -> {
                        JoinActivity joinActivity = new JoinActivity();
                        joinActivity.setActivityId(rs.getInt("ACTIVITY_ID"));
                        joinActivity.setPeopleCount(rs.getInt("ENTER_COUNT"));
                        joinActivity.setStatus(rs.getInt("ENTER_STATUS"));
                        return joinActivity;
                    });

            if (queryJoinData.getStatus() == 1) {
                return "你已經退出活動，無法再退出一次!";
            }

            //改變參加活動人數的數量
            String activityCountUpdate = "update activity set enrollment_count = enrollment_count - :leaveCount\n" +
                    "where activity_id = :activityId";
            Map<String, Object> countMap = new HashMap<>();
            countMap.put("leaveCount", queryJoinData.getPeopleCount());
            countMap.put("activityId", activityId);
            template.update(activityCountUpdate, countMap);


            //redis操作 1.使用者資訊從活動聊天室移除 2.使用者活動列表中移除一筆活動清單
            roomDao.removeRoomUserList(activityId, userId);
            roomDao.removeRoomKeyToUser(userId, activityId);

            return "已經成功退出活動!";

        }


    }


}
