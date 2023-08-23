package project_pet_backEnd.socialMedia.activityChat.dao;



import project_pet_backEnd.socialMedia.activityChat.dto.PubSubMessage;

import java.util.Set;

public interface ChatDao {

    // get messages by room id
    Set<String> getMessages(String roomId, int offset, int size);

    // save message in your room
    void saveMessage(PubSubMessage message);

    //send message to redis channel

    void sendMessageToRedis(String channel, String message);


}
