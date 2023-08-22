package project_pet_backEnd.socialMedia.activityChat.service;


import project_pet_backEnd.socialMedia.activityChat.dto.PubSubMessage;
import project_pet_backEnd.socialMedia.activityChat.dto.response.GetMessagesResponse;

public interface ChatService {

    // get messages by room id
    GetMessagesResponse getMessages(String roomId, int offset, int size);


    // save message in your redis
    void saveMessage(PubSubMessage pubSubMessage);


    //redis publisher
    void sendMessageToRedis(String channel, String message);


}
