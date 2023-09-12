package project_pet_backEnd.userPushNotify;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project_pet_backEnd.socialMedia.activityManager.vo.Activity;
import project_pet_backEnd.userPushNotify.dao.ActivityRepository;
import project_pet_backEnd.userPushNotify.dao.PictureInfoRepository;
import project_pet_backEnd.userPushNotify.vo.PictureInfo;
import project_pet_backEnd.utils.AllDogCatUtils;

@Aspect
@Component
public class NotifyAspect {
    @Autowired
    private PictureInfoRepository pictureInfoRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private  UserNotifyWebSocketHandler userNotifyWebSocketHandler;
    //todo: 美容師 新增作品時
    @After("execution(* project_pet_backEnd.groomer.groomerworkmanager.service.*.insert(..))")
    public  void  groomerUpdateNotify() throws Exception {
             NotifyType notifyType =NotifyType.Groomer;
             PictureInfo pictureInfo= pictureInfoRepository.findFirstByOrderByPiDateDesc();
             NotifyMsg notifyMsg =new NotifyMsg(notifyType, AllDogCatUtils.base64Encode(pictureInfo.getPiPicture()),"有美容師發布作品了，趕快來看看喔~");
             userNotifyWebSocketHandler.publicNotifyMsg(notifyMsg);
    }
    //todo: 商城 新增商品時
    @After("execution(* project_pet_backEnd.productMall.productsmanage.service.*.insertProduct(..))")
    public  void  productUpdateNotify() throws Exception {
        NotifyType notifyType =NotifyType.Store;
        NotifyMsg notifyMsg =new NotifyMsg(notifyType,null,"商城有新的商品，趕快來看看喔~");
        userNotifyWebSocketHandler.publicNotifyMsg(notifyMsg);
        // System.out.println("執行 groomerUpdateNotify");
    }

    //todo: 社群 新增活動時
    @After("execution(* project_pet_backEnd.socialMedia.activityManager.service.*.create(..))")
    public  void  socialMediaUpdateNotify() throws Exception {
        NotifyType notifyType =NotifyType.Activity;
        Activity activity= activityRepository.findTopByOrderByActivityIdDesc();
        NotifyMsg notifyMsg =new NotifyMsg(notifyType,AllDogCatUtils.base64Encode(activity.getActivityPicture()),"社群有新活動，趕快來看看喔~");
        userNotifyWebSocketHandler.publicNotifyMsg(notifyMsg);
        // System.out.println("執行 groomerUpdateNotify");userNotifyWebSocketHandler.publicNotifyMsg(notifyMsg);
    }


    @After("execution(* project_pet_backEnd.user.service.*.localSignIn(..))")
    public  void  test() throws Exception {
        NotifyType notifyType =NotifyType.Store;
        //return;
        //todo 修復雲儀bug
//        PictureInfo pictureInfo= pictureInfoRepository.findFirstByOrderByPiDateDesc();
//        NotifyMsg notifyMsg =new NotifyMsg(notifyType, AllDogCatUtils.base64Encode(pictureInfo.getPiPicture()),"商城有新的商品，趕快來看看喔~");
//       // System.out.println("執行 groomerUpdateNotify");
//        userNotifyWebSocketHandler.publicNotifyMsg(notifyMsg);
    }

}
