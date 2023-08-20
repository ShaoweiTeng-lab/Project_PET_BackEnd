package project_pet_backEnd.socialMedia.activityChat.service.Impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_pet_backEnd.socialMedia.activityChat.dao.ChatDao;
import project_pet_backEnd.socialMedia.activityChat.dao.RoomDao;
import project_pet_backEnd.socialMedia.activityChat.dto.PubSubMessage;
import project_pet_backEnd.socialMedia.activityChat.dto.response.GetMessagesResponse;
import project_pet_backEnd.socialMedia.activityChat.service.ChatService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ChatServiceImpl implements ChatService {
    

    private ChatDao chatDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private ObjectMapper objectMapper;


    /*
     * send message to redis channel
     */
    @Override
    public void sendMessageToRedis(String channel, String message) {
        chatDao.sendMessageToRedis(channel, message);

    }

    /*
     * get messages by room id
     */

    @Override
    public GetMessagesResponse getMessages(String roomId, int offset, int size) {
        List<PubSubMessage> messages = new ArrayList<>();
        boolean checkRoomExists = roomDao.checkRoomExists(roomId);
        if (checkRoomExists) {
            Set<String> values = chatDao.getMessages(roomId, offset, size);
            for (String value : values) {
                PubSubMessage message = deserialize(value);
                messages.add(message);
            }
        }
        GetMessagesResponse getMessagesResponse = new GetMessagesResponse();
        getMessagesResponse.setMessages(messages);
        return getMessagesResponse;
    }

    /*
     * save messages to redis
     */
    @Override
    public void saveMessage(PubSubMessage pubSubMessage) {
        chatDao.saveMessage(pubSubMessage);

    }

    /*
     * get deserialize message
     */
    private PubSubMessage deserialize(String value) {
        try {
            return objectMapper.readValue(value, PubSubMessage.class);
        } catch (Exception e) {
            System.out.printf("Couldn't deserialize json: %s%n", value);
            e.printStackTrace();
        }
        return null;
    }


}
