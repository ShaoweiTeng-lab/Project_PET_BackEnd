package project_pet_backEnd.productMall.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String type;
    private String sender;
    private String receiver;
    private String message;
}
