package project_pet_backEnd.userManager.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project_pet_backEnd.user.dao.UserRepository;
import project_pet_backEnd.user.vo.User;
import project_pet_backEnd.userManager.dao.UserManagerDao;
import project_pet_backEnd.userManager.dto.UserDetailProfileResponse;
import project_pet_backEnd.userManager.dto.UserQueryParameter;
import project_pet_backEnd.userManager.service.UserManagerService;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.ResponsePage;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserManagerServiceImp implements UserManagerService {
    @Autowired
    private UserManagerDao userManagerDao;
    @Autowired
    private  UserRepository userRepository;

    public ResponsePage<List<UserDetailProfileResponse>> getUsers(UserQueryParameter userQueryParameter){

        Sort sort =null ;//設定排序方式
        switch (userQueryParameter.getSort()){
            case asc:
                sort = Sort.by(Sort.Order.asc(userQueryParameter.getOrder().name()));
                break;
            case desc:
                sort = Sort.by(Sort.Order.desc(userQueryParameter.getOrder().name()));
                break;
        }
        Pageable pageable = PageRequest.of(userQueryParameter.getPage()-1, userQueryParameter.getSize(), sort);
        Page userPage = userRepository.findByUserAccount(userQueryParameter.getSearch(), pageable);
        //List<User> userList=userManagerDao.getUsers(userQueryParameter);

        List<User> userList=userPage.getContent();
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

        ResponsePage rspage =new ResponsePage();
        rspage.setPage(userQueryParameter.getPage());
        rspage.setSize(pageable.getPageSize());
        rspage.setTotal((int)userPage.getTotalElements());
        rspage.setBody(rsList);
        return rspage;
    }
}
