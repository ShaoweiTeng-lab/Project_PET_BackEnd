package project_pet_backEnd.productMall.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import project_pet_backEnd.productMall.chat.dto.UserData;
import project_pet_backEnd.productMall.chat.service.MallRedisHandleMessageService;
import project_pet_backEnd.user.dao.UserRepository;
import project_pet_backEnd.user.vo.User;
import project_pet_backEnd.utils.AllDogCatUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class ProductMallWebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private MallRedisHandleMessageService mallRedisHandleMessageService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private  UserRepository userRepository;
    private static final ConcurrentHashMap<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //得到格式為userId_1
        String connector = (String) session.getAttributes().get("connect");
        //如果連線者為user
        if(connector.contains("userId")){
            String id =connector.split("_")[1];//得到ID
            String userNickName =(String) session.getAttributes().get("sender");
            //格式 userId_1-姓名
            sessionMap.put(connector + "-" + userNickName, session);
            System.out.println("連接: "+connector + "-" + userNickName);
            return;
        }
        //連線者為Manager
        System.out.println("商品管理者進入連線");
        sessionMap.put("PdManager", session);
    }
    /**
     * 處理收到的WebSocket消息
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // 解析訊息
        // 當連線後先去 redis 拿對應的 history
        String senderKey = AllDogCatUtils.getKeyByValue(sessionMap, session);
        ChatMessage  chatMessage = objectMapper.readValue(message.getPayload().toString(), ChatMessage.class);

        if(senderKey.contains("user")){
            chatMessage.setReceiver("商城管理員");
            //格式 userId_1-姓名
            UserHandleMessage(senderKey,session,message,chatMessage);
            return;
        }
        ManagerHandleMessage(senderKey,session,message,chatMessage);

    }

    public  void UserHandleMessage(String senderKey,WebSocketSession session, WebSocketMessage<?> message,ChatMessage chatMessage) throws IOException {
        String sender = senderKey.split("-")[1];//拿當前session的使用者名字
        String userId=senderKey.split("-")[0];
        String receiver = "PdManager";//商城只固定跟管理員聊天
        if ("getIdentity".equals(chatMessage.getType())){
            //得到個人訊息
            ChatMessage cmIdentity = new ChatMessage("getIdentity", userId,receiver, sender);
            String msg =objectMapper.writeValueAsString(cmIdentity);
            TextMessage textMessage = new TextMessage(msg);
            session.sendMessage(textMessage);
            return;
        }
        //取得歷史訊息
        if ("history".equals(chatMessage.getType())){
            //使用userId拿回訊息
            List<String> historyData = mallRedisHandleMessageService.getHistoryMsg(userId, receiver);
            String historyMsg = objectMapper.writeValueAsString(historyData);
            ChatMessage cmHistory = new ChatMessage("history", userId,receiver, historyMsg);
            if (session != null && session.isOpen()) {
                String msg =objectMapper.writeValueAsString(cmHistory);
                TextMessage textMessage = new TextMessage(msg);
                session.sendMessage(textMessage);
                System.out.println("history = " + msg);
                return;
            }
        }


        //拿到接收方的session
        WebSocketSession receiverSession = sessionMap.get(receiver);
        String msg =objectMapper.writeValueAsString(chatMessage);
        TextMessage textMessage = new TextMessage(msg);
        if (receiverSession != null && receiverSession.isOpen()) {
            receiverSession.sendMessage(textMessage);
        }
        //發送訊息
        session.sendMessage(textMessage);
        //存訊息
        mallRedisHandleMessageService.saveChatMessage(userId, receiver, msg);
        System.out.println("Message received: " + message);
    }

    public  void ManagerHandleMessage(String senderKey,WebSocketSession session, WebSocketMessage<?> message,ChatMessage chatMessage) throws IOException {
        String sender = "PdManager";//拿當前session的使用者名字
        String receiver = chatMessage.getReceiver();//商城只固定跟管理員聊天
        if("getUserList".equals(chatMessage.getType())){
            //顯示聊天列表
           Set keys= mallRedisHandleMessageService.getAllKeys("ProductMallChat:PdManager:userId");
           List<UserData> userDataList=new ArrayList<>();

           PdManagerChatList pdManagerChatList =new PdManagerChatList();
           keys.forEach(data->{
               //拿到userId_1
               String uId =data.toString().split("PdManager:")[1];
               User user=userRepository.findById(Integer.parseInt(uId.split("_")[1])).orElse(null);
               //得到使用者名稱
               String userNickName=user.getUserNickName();
               UserData userData =new UserData();
               userData.setUserId(uId);
               userData.setUserName(userNickName);
               userDataList.add(userData);
           });
            pdManagerChatList.setUserDataList(userDataList);
            pdManagerChatList.setType("getUserList");
            String msg =objectMapper.writeValueAsString(pdManagerChatList);
            TextMessage textMessage = new TextMessage(msg);
            session.sendMessage(textMessage);
            return;
        }

        //取得歷史訊息
        if ("history".equals(chatMessage.getType())){
            //使用userId拿回訊息
            List<String> historyData = mallRedisHandleMessageService.getHistoryMsg(sender, receiver);
            String historyMsg = objectMapper.writeValueAsString(historyData);
            ChatMessage cmHistory = new ChatMessage("history", sender,receiver, historyMsg);
            if (session != null && session.isOpen()) {
                String msg =objectMapper.writeValueAsString(cmHistory);
                TextMessage textMessage = new TextMessage(msg);
                session.sendMessage(textMessage);
                System.out.println("history = " + msg);
                return;
            }
        }

        //拿到接收方的session
        WebSocketSession receiverSession = sessionMap.get(receiver);
        String msg =objectMapper.writeValueAsString(chatMessage);
        TextMessage textMessage = new TextMessage(msg);
        if (receiverSession != null && receiverSession.isOpen()) {
            receiverSession.sendMessage(textMessage);
        }
        //發送訊息
        session.sendMessage(textMessage);
        //存訊息
        mallRedisHandleMessageService.saveChatMessage(sender, receiver, msg);
        System.out.println("Message received: " + message);
    }

    /**
     * 處理收到的錯誤消息
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    /**
     * 關閉WebSocket連結後觸發方法
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String key =AllDogCatUtils.getKeyByValue(sessionMap,session);
        sessionMap.remove(key);
    }

}
