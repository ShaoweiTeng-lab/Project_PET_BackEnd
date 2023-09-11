package project_pet_backEnd.groomer.pgPushNotify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PgNotifyMsg {
    private PgNotifyType notifyType;
    private  String msg;
}
