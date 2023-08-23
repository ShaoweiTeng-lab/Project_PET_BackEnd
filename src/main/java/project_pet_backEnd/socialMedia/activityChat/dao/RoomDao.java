package project_pet_backEnd.socialMedia.activityChat.dao;


import java.util.Set;

public interface RoomDao {
    //create room
    void createGroupRoom(int activityId, String activityName) ;

    //get specific user  all roomIds
    Set<String> getUserGroupRoomIds(int userId);

    //get online userId list
    Set<Integer> getOnlineUsers();

    // add user into group chat
    boolean addToGroupChatRoom(int userId, int activityId);

    // check room is exists?
    boolean checkRoomExists(String roomId);

    // get chat room name
    String getRoomNameById(String roomId);
}
