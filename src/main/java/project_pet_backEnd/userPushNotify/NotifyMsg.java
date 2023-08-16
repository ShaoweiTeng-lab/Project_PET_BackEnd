package project_pet_backEnd.userPushNotify;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotifyMsg {
    private  NotifyType notifyType;
    private  String msg;
    public  NotifyMsg(){};
}
