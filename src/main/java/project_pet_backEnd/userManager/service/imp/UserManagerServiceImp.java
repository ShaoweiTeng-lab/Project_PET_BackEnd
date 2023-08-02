package project_pet_backEnd.userManager.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.user.dto.UserProfileResponse;
import project_pet_backEnd.user.vo.User;
import project_pet_backEnd.userManager.dao.UserManagerDao;
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

    public Page<List<UserProfileResponse>> getUsers(UserQueryParameter userQueryParameter){
        List<User> userList=userManagerDao.getUsers(userQueryParameter);
        List<UserProfileResponse> rsList=new ArrayList<>();
        for(int i=0;i<userList.size();i++){
            User user=userList.get(i);
            if(user==null)
                throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"找不到使用者");
            UserProfileResponse userProfileResponse =new UserProfileResponse();
            userProfileResponse.setUserName(user.getUserName());
            userProfileResponse.setUserNickName(user.getUserNickName());
            int gender=user.getUserGender();
            switch (gender){
                case 0:
                    userProfileResponse.setUserGender("女性");
                    break;
                case 1:
                    userProfileResponse.setUserGender("男性");
                    break;
                case 2:
                    userProfileResponse.setUserGender("尚未設定");
                    break;
            }
            userProfileResponse.setUserPhone(user.getUserPhone());
            userProfileResponse.setUserAddress(user.getUserAddress());
            userProfileResponse.setUserBirthday(user.getUserBirthday());
            userProfileResponse.setUserPoint(user.getUserPoint());
            userProfileResponse.setUserPic(AllDogCatUtils.base64Encode(user.getUserPic()));
            userProfileResponse.setIdentityProvider(user.getIdentityProvider());
            userProfileResponse.setUserCreated(AllDogCatUtils.timestampToDateFormat(user.getUserCreated()));
            rsList.add(userProfileResponse);
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
