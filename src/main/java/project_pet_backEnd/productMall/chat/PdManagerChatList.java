package project_pet_backEnd.productMall.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project_pet_backEnd.productMall.chat.dto.UserData;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdManagerChatList {
    private  String type;
    private List<UserData> userDataList;
}
