package project_pet_backEnd.socialMedia.chat.service;


import project_pet_backEnd.socialMedia.chat.dto.PubSubMessage;
import project_pet_backEnd.socialMedia.chat.dto.response.GetMessagesResponse;

public interface ChatService {

    // get messages by room id
    GetMessagesResponse getMessages(String roomId, int offset, int size);


    // save message in your redis
    void saveMessage(PubSubMessage pubSubMessage);


    //redis publisher
    void sendMessageToRedis(String channel, String message);


}
