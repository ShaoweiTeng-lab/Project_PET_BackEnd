package project_pet_backEnd.socialMedia.activityChat.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.socialMedia.activityChat.dto.UserActivity;
import project_pet_backEnd.socialMedia.activityUser.dao.UserNickNameDao;
import project_pet_backEnd.socialMedia.util.ImageUtils;
import project_pet_backEnd.user.vo.User;

@Repository
public class UsersImpl implements UserDao {

    private static final String EXISTED_USER = "existed_user:%s";
    private static final String USER_ID_KEY = "user:%s";
    private static final String ONLINE_USERS_KEY = "online_users";

    private static final String USER_ROOMS_KEY = "user:%d:rooms";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserNickNameDao userNickNameDao;


    // ================= 當使用者參加活動後，會被加入到活動聊天室頻道 ================= //
    public UserActivity createUser(int userId) {
        // user key
        User user = userNickNameDao.findById(userId).get();
        UserActivity userActivity = new UserActivity(userId, user.getUserNickName(), ImageUtils.base64Encode(user.getUserPic()), false);
        String existsUserKey = String.format(EXISTED_USER, userId);
        String userIdKey = String.format(USER_ID_KEY, userId);
        // store in redis
        redisTemplate.opsForValue().set(existsUserKey, userIdKey);
        redisTemplate.opsForHash().put(userIdKey, "username", user.getUserNickName());
        redisTemplate.opsForHash().put(userIdKey, "userPic", ImageUtils.base64Encode(user.getUserPic()));
        return userActivity;
    }

    // ================= 查看使用者是否存在聊天室 ================= //
    public boolean checkUserExists(int userId) {
        boolean key = redisTemplate.hasKey(String.format(EXISTED_USER, userId)) != null;
        return key;
    }

    // ================= 拿到使用者資訊 ================= //
    public UserActivity getUser(int userId) {
        if (!checkUserExists(userId)) {
            return null;
        }
        boolean onlineStatus = checkUserOnlineStatus(userId);
        String userIdKey = String.format(USER_ID_KEY, userId);
        String username = (String) redisTemplate.opsForHash().get(userIdKey, "username");
        String userPic = (String) redisTemplate.opsForHash().get(userIdKey, "userPic");

        return new UserActivity(userId, username, userPic, onlineStatus);
    }


    // ================= 查詢使用者目前是否上線 ================= //
    public boolean checkUserOnlineStatus(int userId) {
        boolean isOnline = redisTemplate.opsForSet().isMember(ONLINE_USERS_KEY, String.valueOf(userId)) != null;
        return isOnline;
    }


    // ================= 將user狀態標示為上線 ================= //
    public void addUserToOnlineList(String userId) {
        redisTemplate.opsForSet().add(ONLINE_USERS_KEY, userId);
    }


    // ================= 將使用者從上線清單移除 ================= //
    public void removeUserFromOnlineList(String userId) {
        redisTemplate.opsForSet().remove(ONLINE_USERS_KEY, userId);
    }

}
