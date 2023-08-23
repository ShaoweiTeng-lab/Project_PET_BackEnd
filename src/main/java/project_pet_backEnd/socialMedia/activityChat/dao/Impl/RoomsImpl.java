package project_pet_backEnd.socialMedia.activityChat.dao.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.socialMedia.activityChat.dao.RoomDao;

import java.util.Set;
import java.util.stream.Collectors;


@Repository
public class RoomsImpl implements RoomDao {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private static final String USER_ROOMS_KEY = "user:%d:rooms";
    private static final String ROOM_KEY = "room:%s";
    private static final String ROOM_NAME_KEY = "room:%s:name";
    private static final String ONLINE_USERS_KEY = "online_users";


    // manager can create group rooms

    public void createGroupRoom(int activityId, String activityName) {
        String roomId = String.valueOf(activityId);
        if (!checkRoomExists(roomId)) {
            String groupRoomKey = String.format(ROOM_KEY, roomId);
            redisTemplate.opsForSet().add("room", roomId);
            redisTemplate.opsForSet().add(groupRoomKey, activityName);
        } else {
            System.out.println("該聊天室已經存在");
        }
    }


    //get user group roomIds by userId
    @Override
    public Set<String> getUserGroupRoomIds(int userId) {
        String userRoomsKey = String.format(USER_ROOMS_KEY, userId);
        return redisTemplate.opsForSet().members(userRoomsKey);
    }

    //check room exists
    public boolean checkRoomExists(String roomId) {
        return redisTemplate.hasKey(String.format(ROOM_KEY, roomId)) != null;
    }

    //get room name
    public String getRoomNameById(String roomId) {
        String roomNameKey = String.format(ROOM_NAME_KEY, roomId);
        return redisTemplate.opsForValue().get(roomNameKey);
    }


    // get online user list
    @Override
    public Set<Integer> getOnlineUsers() {
        Set<String> onlineIds = redisTemplate.opsForSet().members(ONLINE_USERS_KEY);
        if (onlineIds == null) {
            System.out.println("目前沒有用戶在線");
            return null;
        }
        return onlineIds.stream().map(Integer::parseInt).collect(Collectors.toSet());
    }


    // add user to chat room
    @Override
    public boolean addToGroupChatRoom(int userId, int activityId) {

        String roomsKey = String.format(USER_ROOMS_KEY, userId);
        redisTemplate.opsForSet().add(roomsKey, String.valueOf(activityId));

        //check user add to room
        return redisTemplate.opsForSet().isMember(roomsKey, String.valueOf(activityId)) != null;
    }


}
