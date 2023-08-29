package project_pet_backEnd.socialMedia.activityChat.dao;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.socialMedia.activityChat.dao.RoomDao;
import project_pet_backEnd.socialMedia.activityChat.dto.Message;
import project_pet_backEnd.socialMedia.activityChat.dto.UserActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Repository
public class RoomsImpl implements RoomDao {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserDao userDao;


    private static final String USER_ROOMS_KEY = "user:%d:rooms";
    private static final String ROOM_KEY = "room:%s";
    private static final String ROOM_NAME_KEY = "room:%s:name";
    private static final String ONLINE_USERS_KEY = "online_users";
    private static final String ACTIVITY_USER_LIST = "activity:%d";


    // ================= 建立活動聊天室 ================= //

    public void createGroupRoom(int activityId, String activityName) {

        //團體聊天室的Id，裡面放message
        String roomId = String.valueOf(activityId);
        String groupRoomKey = String.format(ROOM_KEY, roomId);

        //default one message
        Message message = new Message();
        message.setMessage("來自管理員的第一條訊息");
        message.setUsername("社群管理員");
        message.setRoomId(roomId);
        message.setDate((int) System.currentTimeMillis());
        message.setUserId("1");
        redisTemplate.opsForSet().add(groupRoomKey, String.valueOf(message));
        //聊天室名稱
        String roomNameKey = String.format(ROOM_NAME_KEY, roomId);
        redisTemplate.opsForSet().add(roomNameKey, activityName);

    }


    // ================= 拿到使用者所有聊天室列表 ================= //

    @Override
    public Set<String> getUserGroupRoomIds(int userId) {
        String userRoomsKey = String.format(USER_ROOMS_KEY, userId);
        return redisTemplate.opsForSet().members(userRoomsKey);
    }


    // ================= 當參加活動後，使用者新增一筆活動聊天室 ================= //
    @Override
    public boolean addRoomKeyToUser(int userId, int activityId) {

        String userRoomsKey = String.format(USER_ROOMS_KEY, userId);
        redisTemplate.opsForSet().add(userRoomsKey, String.valueOf(activityId));

        //檢查聊天室資訊新增是否成功
        return redisTemplate.opsForSet().isMember(userRoomsKey, String.valueOf(activityId)) != null;
    }

    // ================= 當活動結束後，使用者移除一筆活動聊天室 ================= //
    @Override
    public boolean removeRoomKeyToUser(int userId, int activityId) {
        String userRoomsKey = String.format(USER_ROOMS_KEY, userId);
        redisTemplate.opsForSet().remove(userRoomsKey, String.valueOf(activityId));

        Boolean member = redisTemplate.opsForSet().isMember(userRoomsKey, String.valueOf(activityId));
        if (Boolean.TRUE.equals(member)) {
            return false;
        } else {
            return true;
        }

    }


    // ================= 檢查聊天室是否已經存在 ================= //
    public boolean checkRoomExists(String roomId) {
        Boolean key = redisTemplate.hasKey(String.format(ROOM_KEY, roomId));
        if (key != null) {
            return true;
        }
        return false;
    }


    // ================= 拿到聊天室名稱 ================= //
    public String getRoomNameById(String roomId) {
        String roomNameKey = String.format(ROOM_NAME_KEY, roomId);
        return redisTemplate.opsForValue().get(roomNameKey);
    }


    // ================= 拿到使用者線上清單 ================= //
    @Override
    public Set<Integer> getOnlineUsers() {
        Set<String> onlineIds = redisTemplate.opsForSet().members(ONLINE_USERS_KEY);
        if (onlineIds == null) {
            System.out.println("目前沒有用戶在線");
            return null;
        }
        return onlineIds.stream().map(Integer::parseInt).collect(Collectors.toSet());
    }


    // ================= 透過roomId拿到所有訊息 ================= //

    @Override
    public Set<String> getMessages(String roomId, int offset, int size) {
        String roomNameKey = String.format(ROOM_KEY, roomId);
        return redisTemplate.opsForZSet().reverseRange(roomNameKey, offset, offset + size);
    }

    // ================= 儲存訊息到聊天室 ================= //
    @Override
    public void saveMessage(Message message) {
        String roomKey = String.format(ROOM_KEY, message.getRoomId());

        try {
            String jacksonMessage = objectMapper.writeValueAsString(message);
            redisTemplate.opsForZSet().add(roomKey, objectMapper.writeValueAsString(message), message.getDate());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    // ================= 將使用者加入到使用者活動清單，用來記錄活動有的成員 ================= //
    @Override
    public void createRoomUserList(int activityId, int userId) {
        String activityUserListKey = String.format(ACTIVITY_USER_LIST, activityId);
        String userIdValue = String.valueOf(userId);
        redisTemplate.opsForSet().add(activityUserListKey, userIdValue);

    }


    // ================= 使用者取消活動，將使用者從活動清單移除 ================= //
    @Override
    public void removeRoomUserList(int roomId, int userId) {

        String activityUserListKey = String.format(ACTIVITY_USER_LIST, roomId);
        redisTemplate.opsForSet().remove(activityUserListKey, String.valueOf(userId));

    }


    // ================= 活動結束後移除聊天室和聊天細節 ================= //
    @Override
    public void removeGroupRoom(int roomId) {
        String roomIdStr = String.valueOf(roomId);
        String groupRoomKey = String.format(ROOM_KEY, roomIdStr);
        Set<String> userIdLists = redisTemplate.opsForSet().members(groupRoomKey);
        String roomNameKey = String.format(ROOM_NAME_KEY, roomIdStr);
        Set<String> roomNames = redisTemplate.opsForSet().members(roomNameKey);

        for (String roomName : roomNames) {
            redisTemplate.opsForSet().remove(roomNameKey, roomName);
        }
        for (String userId : userIdLists) {
            redisTemplate.opsForSet().remove(groupRoomKey, userId);
        }
    }

    // ================= 拿到目前聊天室的使用者清單 ================= //
    @Override
    public List<UserActivity> getCurrentRoomUserOnlineList(int roomId) {
        String roomIdStr = String.valueOf(roomId);
        String groupRoomKey = String.format(ROOM_KEY, roomIdStr);
        Set<String> userIdLists = redisTemplate.opsForSet().members(groupRoomKey);
        if (userIdLists == null) {
            return null;
        } else {
            List<UserActivity> userActivityList = new ArrayList<>();
            for (String userId : userIdLists) {
                UserActivity user = userDao.getUser(Integer.parseInt(userId));
                userActivityList.add(user);
            }
            return userActivityList;
        }
    }


}
