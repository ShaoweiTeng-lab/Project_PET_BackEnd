package project_pet_backEnd.userPushNotify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotifyMsg {
    private  NotifyType notifyType;
    private  String image;
    private  String msg;
}
