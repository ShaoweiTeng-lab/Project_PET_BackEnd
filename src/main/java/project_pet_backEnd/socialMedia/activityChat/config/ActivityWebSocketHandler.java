package project_pet_backEnd.socialMedia.activityChat.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import project_pet_backEnd.socialMedia.activityChat.dao.RoomDao;
import project_pet_backEnd.socialMedia.activityChat.dao.RoomsImpl;
import project_pet_backEnd.socialMedia.activityChat.dao.UserDao;
import project_pet_backEnd.socialMedia.activityChat.dto.ChatMessage;
import project_pet_backEnd.socialMedia.activityChat.dto.NotifyMessage;
import project_pet_backEnd.socialMedia.activityChat.dto.ReceiveMessage;
import project_pet_backEnd.socialMedia.activityChat.dto.UserActivity;
import project_pet_backEnd.socialMedia.activityChat.service.RedisMessagePublisher;
import project_pet_backEnd.socialMedia.activityChat.vo.PubSubMessage;
import project_pet_backEnd.socialMedia.util.DateUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ActivityWebSocketHandler extends TextWebSocketHandler {


    /**
     * concurrent hashmap 執行緒安全
     */

    public static final ConcurrentHashMap<String, WebSocketSession> acSessionMap = new ConcurrentHashMap<>();
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisMessagePublisher messagePublisher;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private UserDao userDao;

    /**
     * 建立websocket連結
     */

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //get userId  userId_
        String idString = (String) session.getAttributes().get("connect");
        if (idString.contains("userId_")) {
            String[] getUserId = idString.split("_");
            String userId = getUserId[1];
            //get username username_
            String userNameString = (String) session.getAttributes().get("sender");
            String[] getUserName = userNameString.split("_");
            String userName = getUserName[0];
            //當使用者上線後使用redis紀錄使用者狀態
            userDao.addUserToOnlineList(userId);
            //放入sessionMap中進行管理
            acSessionMap.putIfAbsent(userId, session);
            //發送的訊息格式 不需要儲存，只需顯示
            NotifyMessage notifyMessage = new NotifyMessage();
            notifyMessage.setMessage(userName + "已經上線了");
            notifyMessage.setUsername(userName);
            notifyMessage.setUserId(userId);
            notifyMessage.setDate(DateUtils.dateTimeSqlToStr(new Timestamp(System.currentTimeMillis())));
            // sendNotifyMessage(notifyMessage);
            String notifyMesJson = objectMapper.writeValueAsString(notifyMessage);
            messagePublisher.notify(notifyMesJson);
        } else if (idString.contains("managerId_")) {
            //管理員可以直接拿到大家的session
            acSessionMap.putIfAbsent("0", session);
            userDao.addUserToOnlineList("0");
        }
    }

    /**
     * 使用者上線通知
     */

//    public void sendNotifyMessage(NotifyMessage message) throws Exception {
//        //拿到使用者的room list
//        Set<String> roomIds = roomDao.getUserGroupRoomIds(Integer.parseInt(message.getUserId()));
//        //對擁不同伺服器訂閱相同channel(roomId)裡面的使用者session進行通知
//        //websocket收到訊息 判斷使用者是否訂閱相同redis channel
//        //不同伺服器在websocket上建立不同的session，這些session無法共享，但可以透過訂閱redis來處理此問題，
//
//        //這邊我們想要通知所有在不同聊天室群中有上線的user我已經上線的情形，要找到對應的session來發送訊息
//        //1. 取得上線用戶、和聊天室裡的使用者清單 --> 用來拿到使用者通知清單
//        Set<Integer> onlineUsers = roomDao.getOnlineUsers();
//        List<String> onlineUserLists = new ArrayList<>();
//        for (Integer online : onlineUsers) {
//            onlineUserLists.add(String.valueOf(online));
//        }
//        Set<String> notifyUserIds = new HashSet<>();
//
//        for (String roomId : roomIds) {
//            Set<String> userList = roomDao.getRoomUserList(roomId);
//            for (String user : userList) {
//                if (onlineUserLists.contains(user)) {
//                    notifyUserIds.add(message.getUserId());
//                }
//            }
//        }
//        //通知使用者已經上線 -先透過 userId list 拿到session發送通知
//        List<WebSocketSession> sessionList = new ArrayList<>();
//        for (String notifyUser : notifyUserIds) {
//            //拿到所有session
//            WebSocketSession socketSession = acSessionMap.get(notifyUser);
//            sessionList.add(socketSession);
//        }
//        for (WebSocketSession userSession : sessionList) {
//            String sendMessage = objectMapper.writeValueAsString(message);
//            userSession.sendMessage(new TextMessage(sendMessage));
//        }
//
//    }


    /**
     * 處理接收到的訊息
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        //get userId  userId_
        String idString = (String) session.getAttributes().get("connect");
        if (idString.contains("userId_")) {
            String[] getUserId = idString.split("_");
            String userId = getUserId[1];
            //get username username_
            String userNameString = (String) session.getAttributes().get("sender");
            String[] getUserName = userNameString.split("_");
            String userName = getUserName[0];
            ReceiveMessage receiveMessage = objectMapper.readValue(message.getPayload().toString(), ReceiveMessage.class);
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessage(receiveMessage.getContent());
            chatMessage.setUserPic("");
            chatMessage.setUsername(userName);
            chatMessage.setRoomId(receiveMessage.getRoomId());
            chatMessage.setDate(DateUtils.dateTimeSqlToStr(new Timestamp(System.currentTimeMillis())));
            chatMessage.setUserId(userId);
            //sendMessageToRoomChannel(chatMessage);
            //轉換成pub sub到redis處理  傳送給相同頻道的所有使用者
            String jsonString = objectMapper.writeValueAsString(chatMessage);
            messagePublisher.publish(jsonString);
        } else if (idString.contains("managerId_")) {
            ReceiveMessage receiveMessage = objectMapper.readValue(message.getPayload().toString(), ReceiveMessage.class);
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessage(receiveMessage.getContent());
            chatMessage.setUserPic("");
            chatMessage.setUsername("社群管理員");
            chatMessage.setRoomId(receiveMessage.getRoomId());
            chatMessage.setDate(DateUtils.dateTimeSqlToStr(new Timestamp(System.currentTimeMillis())));
            chatMessage.setUserId("0");
            //轉換成pub sub到redis處理  傳送給相同頻道的所有使用者
            String jsonString = objectMapper.writeValueAsString(chatMessage);
            messagePublisher.publish(jsonString);
        }


    }

    /**
     * 發送訊息到指定的聊天室
     */

//    public void sendMessageToRoomChannel(ChatMessage message) throws Exception {
//
//        String roomId = message.getRoomId();
//        List<UserActivity> currentRoomUserOnlineList = roomDao.getCurrentRoomUserOnlineList(roomId);
//        List<String> userIds = new ArrayList<>();
//        for (UserActivity user : currentRoomUserOnlineList) {
//            String userId = String.valueOf(user.getUserId());
//            userIds.add(userId);
//        }
//        //通知使用者已經上線 -先透過 userId list 拿到session發送通知
//        List<WebSocketSession> sessionList = new ArrayList<>();
//        for (String user : userIds) {
//            //拿到所有session
//            WebSocketSession socketSession = acSessionMap.get(user);
//            sessionList.add(socketSession);
//        }
//        for (WebSocketSession userSession : sessionList) {
//            String sendMessage = objectMapper.writeValueAsString(message);
//            userSession.sendMessage(new TextMessage(sendMessage));
//        }
//    }

    /**
     * 處理收到的錯誤消息
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    /**
     * 關閉WebSocket觸發方法
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        //只移除user session
        String idString = (String) session.getAttributes().get("connect");
        if (idString.contains("userId_")) {
            String[] getUserId = idString.split("_");
            String userId = getUserId[1];
            acSessionMap.remove(userId);
            //移除 redis online
            userDao.removeUserFromOnlineList(userId);
        } else {
            //移除管理員
            acSessionMap.remove("0");
            userDao.removeUserFromOnlineList("0");
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}