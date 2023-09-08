package project_pet_backEnd.socialMedia.activityChat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.socialMedia.activityChat.dao.RoomDao;
import project_pet_backEnd.socialMedia.activityChat.dto.ChatMessage;
import project_pet_backEnd.socialMedia.activityChat.dto.UserActivity;
import project_pet_backEnd.socialMedia.activityChat.dto.UserRoom;
import project_pet_backEnd.socialMedia.activityChat.vo.PubSubMessage;
import project_pet_backEnd.socialMedia.util.DateUtils;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomDao roomDao;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ResultResponse<List<ChatMessage>> getMessageByRoomId(int roomId, int page, int size) {
        ResultResponse<List<ChatMessage>> response = new ResultResponse<>();
        Set<String> messages = roomDao.getMessages(String.valueOf(roomId), page, size);
        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        for (String message : messages) {
            ChatMessage chatMessage = new ChatMessage();
            try {
                PubSubMessage pubSubMessage = objectMapper.readValue(message, PubSubMessage.class);
                chatMessage.setMessage(pubSubMessage.getContent());
                chatMessage.setUserPic(pubSubMessage.getUserPic());
                chatMessage.setDate(DateUtils.intToString(pubSubMessage.getDate()));
                chatMessage.setUsername(pubSubMessage.getUsername());
                chatMessage.setRoomId(pubSubMessage.getRoomId());
                chatMessages.add(chatMessage);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "伺服器錯誤");

            }
        }
        response.setMessage(chatMessages);
        return response;
    }

    @Override
    public ResultResponse<List<UserRoom>> getUserRoomList(int userId) {
        ResultResponse<List<UserRoom>> response = new ResultResponse<>();
        List<UserRoom> userRooms = new ArrayList<>();
        Set<String> roomIds = roomDao.getUserGroupRoomIds(userId);
        try {
            for (String roomId : roomIds) {
                UserRoom userRoom = new UserRoom();
                userRoom.setRoomId(Integer.parseInt(roomId));
                String roomName = roomDao.getRoomNameById(roomId);
                userRoom.setRoomName(roomName);
                userRooms.add(userRoom);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "伺服器錯誤");
        }
        response.setMessage(userRooms);
        return response;
    }

    @Override
    public ResultResponse<List<UserActivity>> getOnlineUserList(int roomId) {
        ResultResponse<List<UserActivity>> response = new ResultResponse<>();
        List<UserActivity> userOnlineList = new ArrayList<>();
        try {
            userOnlineList = roomDao.getCurrentRoomUserOnlineList(roomId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "伺服器錯誤");
        }
        response.setMessage(userOnlineList);
        return response;
    }

    @Override
    public ResultResponse<List<UserRoom>> getAllRoomList() {
        ResultResponse<List<UserRoom>> response = new ResultResponse<>();
        List<UserRoom> userRooms = new ArrayList<>();
        Set<String> roomIds = roomDao.getGroupRoomIds();
        try {
            for (String roomId : roomIds) {
                UserRoom userRoom = new UserRoom();
                userRoom.setRoomId(Integer.parseInt(roomId));
                String roomName = roomDao.getRoomNameById(roomId);
                userRoom.setRoomName(roomName);
                userRooms.add(userRoom);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "伺服器錯誤");
        }
        response.setMessage(userRooms);
        return response;
    }
}
