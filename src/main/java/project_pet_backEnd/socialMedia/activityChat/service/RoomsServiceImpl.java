package project_pet_backEnd.socialMedia.activityChat.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_pet_backEnd.socialMedia.activityChat.dao.RoomDao;
import project_pet_backEnd.socialMedia.activityChat.dto.Room;
import project_pet_backEnd.socialMedia.activityChat.dto.request.AddUserToRoomRequest;
import project_pet_backEnd.socialMedia.activityChat.dto.response.GetUserRoomIdsSetResponse;
import project_pet_backEnd.socialMedia.activityChat.service.RoomsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RoomsServiceImpl implements RoomsService {

    @Autowired
    private RoomDao roomDao;

    @Override
    public void createGroupRoom( int activityId,String activityName) {
        roomDao.createGroupRoom(activityId, activityName);
    }

    //when activity create, chatroom also created.
    @Override
    public GetUserRoomIdsSetResponse getUserGroupRoomIds(int userId) {
        Set<String> roomIds = roomDao.getUserGroupRoomIds(userId);
        GetUserRoomIdsSetResponse getUserRoomIdsSetResponse = new GetUserRoomIdsSetResponse();
        List<Room> rooms = new ArrayList<>();
        for (String roomId : roomIds) {
            boolean roomExists = roomDao.checkRoomExists(roomId);
            if (roomExists) {
                String name = roomDao.getRoomNameById(roomId);
                rooms.add(new Room(roomId, name));
            }
        }
        getUserRoomIdsSetResponse.setRoomList(rooms);
        return getUserRoomIdsSetResponse;
    }

    @Override
    public Set<Integer> getOnlineUsers() {
        return roomDao.getOnlineUsers();
    }


    //when user enter activity, put in group chat
    @Override
    public void addToGroupChatRoom(AddUserToRoomRequest addUserToRoomRequest) {
//        roomDao.addToGroupChatRoom(addUserToRoomRequest.getUserId(), addUserToRoomRequest.getActivityId());
    }

    @Override
    public boolean checkRoomExists(String roomId) {
        return roomDao.checkRoomExists(roomId);
    }

    @Override
    public String getRoomNameById(String roomId) {
        return roomDao.getRoomNameById(roomId);
    }
}

