package project_pet_backEnd.socialMedia.activityChat.dao;


import project_pet_backEnd.socialMedia.activityChat.dto.UserActivity;
import project_pet_backEnd.socialMedia.activityChat.vo.PubSubMessage;

import java.util.List;
import java.util.Set;

public interface RoomDao {
    void createGroupRoom(int activityId, String activityName);

    //建立活動聊天室使用者清單
    void createRoomUserList(int activityId, int userId);

    //拿到所有活動聊天室列表
    Set<String> getGroupRoomIds();

    //拿到聊天室使用者清單
    Set<String> getRoomUserList(String roomId);

    //移除聊天室-當活動結束或取消後
    void removeGroupRoom(int roomId);

    //移除活動聊天室user清單
    void removeRoomUserList(int roomId, int userId);

    Set<String> getUserGroupRoomIds(int userId);

    Set<Integer> getOnlineUsers();

    //顯示在活動聊天室左邊的列表
    boolean addRoomKeyToUser(int userId, int activityId);

    //活動結束後，從列表中移除一筆活動
    boolean removeRoomKeyToUser(int userId, int activityId);

    boolean checkRoomExists(String roomId);

    String getRoomNameById(String roomId);

    // 透過roomId 拿到所有message
    Set<String> getMessages(String roomId, int offset, int size);

    void saveMessage(PubSubMessage pubSubMessage);


    //拿取目前活動上線使用者
    List<UserActivity> getCurrentRoomUserOnlineList(String roomId);

    //修改聊天室名稱
    void changeRoomName(String roomId, String activityName);


}
