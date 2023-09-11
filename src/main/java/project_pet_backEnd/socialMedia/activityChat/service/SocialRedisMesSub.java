package project_pet_backEnd.socialMedia.activityChat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import project_pet_backEnd.socialMedia.activityChat.config.ActivityWebSocketHandler;
import project_pet_backEnd.socialMedia.activityChat.dao.RoomDao;
import project_pet_backEnd.socialMedia.activityChat.dao.RoomsImpl;
import project_pet_backEnd.socialMedia.activityChat.dto.ChatMessage;
import project_pet_backEnd.socialMedia.activityChat.dto.NotifyMessage;
import project_pet_backEnd.socialMedia.activityChat.dto.UserActivity;
import project_pet_backEnd.socialMedia.activityChat.vo.PubSubMessage;
import project_pet_backEnd.socialMedia.util.DateUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SocialRedisMesSub implements MessageListener {


    @Autowired
    private RoomDao roomDao;

    @Autowired
    private ObjectMapper objectMapper;

//    這邊等紹偉幫我加上建構子就可以執行了
//    public SocialRedisMesSub(ObjectMapper objectMapper, RoomDao roomDao) {
//        this.objectMapper = objectMapper;
//        this.roomDao = roomDao;
//    }


    //redis接收到訊息後的處理
    @Override
    public void onMessage(Message message, byte[] bytes) {
        //check message from which channel
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();

        String channelStr = new String(channel, StandardCharsets.UTF_8);
        String bodyStr = new String(body, StandardCharsets.UTF_8);
        if (bodyStr.contains("roomId")) {
            try {
                ChatMessage chatMessage = objectMapper.readValue(message.getBody(), ChatMessage.class);
                PubSubMessage pubSubMessage = new PubSubMessage();
                pubSubMessage.setUserPic("");
                pubSubMessage.setUsername(chatMessage.getUsername());
                pubSubMessage.setContent(chatMessage.getMessage());
                pubSubMessage.setDate(System.currentTimeMillis());
                pubSubMessage.setRoomId(chatMessage.getRoomId());
                //將message儲存到redis中
                roomDao.saveMessage(pubSubMessage);
                //透過websocket session 將message及時回傳給使用者
                sendMessageToRoomChannel(chatMessage);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "伺服器出錯");
            }
        } else if (bodyStr.contains("已經上線了")) {
            try {
                NotifyMessage notifyMessage = objectMapper.readValue(message.getBody(), NotifyMessage.class);
                sendNotifyMessage(notifyMessage);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "伺服器出錯");
            }

        }


    }

    /**
     * 使用者上線通知
     */

    public void sendNotifyMessage(NotifyMessage message) throws Exception {
        //拿到使用者的room list
        Set<String> roomIds = roomDao.getUserGroupRoomIds(Integer.parseInt(message.getUserId()));
        //對擁不同伺服器訂閱相同channel(roomId)裡面的使用者session進行通知
        //websocket收到訊息 判斷使用者是否訂閱相同redis channel
        //不同伺服器在websocket上建立不同的session，這些session無法共享，但可以透過訂閱redis來處理此問題，

        //這邊我們想要通知所有在不同聊天室群中有上線的user我已經上線的情形，要找到對應的session來發送訊息
        //1. 取得上線用戶、和聊天室裡的使用者清單 --> 用來拿到使用者通知清單
        Set<Integer> onlineUsers = roomDao.getOnlineUsers();
        List<String> onlineUserLists = new ArrayList<>();
        for (Integer online : onlineUsers) {
            onlineUserLists.add(String.valueOf(online));
        }
        //System.out.println("online用戶清單" + onlineUserLists);
        Set<String> notifyUserIds = new HashSet<>();

        for (String roomId : roomIds) {
            //每個聊天室的使用者
            Set<String> userList = roomDao.getRoomUserList(roomId);
            //System.out.println("聊天室" + roomId + "使用者Id" + userList);
            for (String user : userList) {
                //System.out.println("使用者: " + user);
                if (onlineUserLists.contains(user)) {
                    notifyUserIds.add(user);
                }
            }
        }
        //System.out.println("使用者清單: " + notifyUserIds);
        //通知使用者已經上線 -先透過 userId list 拿到session發送通知
        List<WebSocketSession> sessionList = new ArrayList<>();
        for (String notifyUser : notifyUserIds) {
            //拿到所有session
            WebSocketSession socketSession = ActivityWebSocketHandler.acSessionMap.get(notifyUser);
            //拿到此使用者的Session
            //System.out.println("拿到此使用者的Session: " + socketSession);
            sessionList.add(socketSession);
        }
        //System.out.println("所有要通知的使用者session: " + sessionList);
        for (WebSocketSession userSession : sessionList) {
            String sendMessage = objectMapper.writeValueAsString(message);
            //當user session不為空
            if (userSession != null) {
                userSession.sendMessage(new TextMessage(sendMessage));
            }

        }

    }

    /**
     * 發送訊息到指定的聊天室
     */

    public void sendMessageToRoomChannel(ChatMessage message) throws Exception {

        String roomId = message.getRoomId();
        List<UserActivity> currentRoomUserOnlineList = roomDao.getCurrentRoomUserOnlineList(roomId);
        List<String> userIds = new ArrayList<>();
        for (UserActivity user : currentRoomUserOnlineList) {
            String userId = String.valueOf(user.getUserId());
            userIds.add(userId);
        }
        //通知使用者已經上線 -先透過 userId list 拿到session發送通知
        List<WebSocketSession> sessionList = new ArrayList<>();
        for (String user : userIds) {
            //拿到所有session
            WebSocketSession socketSession = ActivityWebSocketHandler.acSessionMap.get(user);
            //System.out.println("使用者發送訊息session: " + socketSession);
            //新增判斷，因為socketSession有可能為null
            if (socketSession != null) {
                sessionList.add(socketSession);
            }
        }
        System.out.println(sessionList);
        for (WebSocketSession userSession : sessionList) {
            String sendMessage = objectMapper.writeValueAsString(message);
            userSession.sendMessage(new TextMessage(sendMessage));
        }

    }

    //拿到聊天頻道
    public String getRoomId(String channel) {
        for (String s : channel.split("\\*", 2)) {
            if (!s.contains("social-media-channel.")) {
                return s;
            }
        }
        return null;
    }
}
