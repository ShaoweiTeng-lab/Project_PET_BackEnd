package project_pet_backEnd.userPushNotify;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NotifyAspect {
    @Autowired
    private  UserNotifyWebSocketHandler userNotifyWebSocketHandler;

}
