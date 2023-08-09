package project_pet_backEnd.socialMedia.chat.dao.Impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.socialMedia.chat.dao.UserDao;
import project_pet_backEnd.socialMedia.chat.dto.User;


import java.util.Set;

@Repository
public class UsersImpl implements UserDao {

    private static final String EXISTED_USER = "existed_user:%s";
    private static final String USER_ID_KEY = "user:%s";
    private static final String ONLINE_USERS_KEY = "online_users";

    private static final String USER_ROOMS_KEY = "user:%d:rooms";

    private RedisTemplate<String, String> redisTemplate;

    //when user enter activity chat --> create user and add to activity chat room

    public User createUser(int userId, String username, Boolean isOnline) {
        // user key
        User user = new User(userId, username, isOnline);
        String existsUserKey = String.format(EXISTED_USER, userId);
        String userIdKey = String.format(USER_ID_KEY, userId);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(username);

        // store in redis
        redisTemplate.opsForValue().set(existsUserKey, userIdKey);
        redisTemplate.opsForHash().put(userIdKey, "username", username);
        redisTemplate.opsForHash().put(userIdKey, "password", hashedPassword);
        return user;
    }

    // check user existed in redis
    public boolean checkUserExists(int userId) {
        Boolean result = redisTemplate.hasKey(String.format(EXISTED_USER, userId));
        return result != null;
    }

    // get user information
    public User getUser(int userId, String username) {
        if (!checkUserExists(userId)) {
            return null;
        }
        boolean isOnline = redisTemplate.opsForSet().isMember(ONLINE_USERS_KEY, String.valueOf(userId)) != null;
        return new User(userId, username, isOnline);
    }

    // user have many chat room -> unique roomId
    public Set<String> getUserRoomIds(int userId) {
        String userRoomsIds = String.format(USER_ROOMS_KEY, userId);
        return redisTemplate.opsForSet().members(userRoomsIds);
    }


    // add user online list
    public void addUserToOnlineList(String userId) {
        redisTemplate.opsForSet().add(ONLINE_USERS_KEY, userId);
    }

    // remove user online list
    public void removeUserFromOnlineList(String userId) {
        redisTemplate.opsForSet().remove(ONLINE_USERS_KEY, userId);
    }

}
