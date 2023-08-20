package project_pet_backEnd.userManager.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_pet_backEnd.user.vo.User;
import project_pet_backEnd.userManager.dao.UserManagerDao;
import project_pet_backEnd.userManager.dto.UserDetailProfileResponse;
import project_pet_backEnd.userManager.dto.UserQueryParameter;
import project_pet_backEnd.userManager.service.UserManagerService;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.Page;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserManagerServiceImp implements UserManagerService {
    @Autowired
    private UserManagerDao userManagerDao;

    public Page<List<UserDetailProfileResponse>> getUsers(UserQueryParameter userQueryParameter){
        List<User> userList=userManagerDao.getUsers(userQueryParameter);
        List<UserDetailProfileResponse> rsList=new ArrayList<>();
        for(int i=0;i<userList.size();i++){
            User user=userList.get(i);
            UserDetailProfileResponse userDetailProfileResponse =new UserDetailProfileResponse();
            userDetailProfileResponse.setUserId(user.getUserId());
            userDetailProfileResponse.setUserName(user.getUserName());
            userDetailProfileResponse.setUserNickName(user.getUserNickName());
            int gender=user.getUserGender();
            switch (gender){
                case 0:
                    userDetailProfileResponse.setUserGender("女性");
                    break;
                case 1:
                    userDetailProfileResponse.setUserGender("男性");
                    break;
                case 2:
                    userDetailProfileResponse.setUserGender("尚未設定");
                    break;
            }
            userDetailProfileResponse.setUserPhone(user.getUserPhone());
            userDetailProfileResponse.setUserAddress(user.getUserAddress());
            userDetailProfileResponse.setUserBirthday(user.getUserBirthday());
            userDetailProfileResponse.setUserPoint(user.getUserPoint());
            userDetailProfileResponse.setUserPic(AllDogCatUtils.base64Encode(user.getUserPic()));
            userDetailProfileResponse.setIdentityProvider(user.getIdentityProvider());
            userDetailProfileResponse.setUserCreated(AllDogCatUtils.timestampToDateFormat(user.getUserCreated()));
            rsList.add(userDetailProfileResponse);
        }

        Page page =new Page();
        page.setLimit(userQueryParameter.getLimit());
        page.setOffset(userQueryParameter.getOffset());
        //得到總筆數，方便實作頁數
        Integer total =  userManagerDao.countUser(userQueryParameter);
        page.setTotal(total);
        page.setRs(rsList);
        return page;
    }
}
