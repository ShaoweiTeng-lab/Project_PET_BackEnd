package project_pet_backEnd.userManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project_pet_backEnd.user.dto.ResultResponse;
import project_pet_backEnd.user.dto.UserProfileResponse;
import project_pet_backEnd.user.service.UserService;
import project_pet_backEnd.user.vo.User;
import project_pet_backEnd.userManager.dto.Sort;
import project_pet_backEnd.userManager.dto.UserOrderBy;
import project_pet_backEnd.userManager.dto.UserQueryParameter;
import project_pet_backEnd.userManager.service.UserManagerService;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
@Validated
@RestController
@RequestMapping("/manager")
@PreAuthorize("hasAnyAuthority('會員管理')")
public class UserManagerController {
    @Autowired
    private UserManagerService userManagerService;
    /**
     *
     * */
    @GetMapping("/users")
    public ResponseEntity<List<UserProfileResponse>>  getUsers(
            @RequestParam(required = false)String search,
            @RequestParam(required = false, defaultValue = "USER_CREATED") UserOrderBy orderBy,
            @RequestParam(required = false,defaultValue = "desc") Sort sort,
            @RequestParam(defaultValue = "5")@Max(50) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0")@Min(0)Integer offset){
        UserQueryParameter userQueryParameter=new UserQueryParameter();
        userQueryParameter.setSearch(search);
        userQueryParameter.setOrder(orderBy);
        userQueryParameter.setSort(sort);
        userQueryParameter.setLimit(limit);
        userQueryParameter.setOffset(offset);
        List<UserProfileResponse> userList = userManagerService.getUsers(userQueryParameter);
        return ResponseEntity.status(200).body(userList);
    }
//    public  ResponseEntity<ResultResponse> adjustUser

}
