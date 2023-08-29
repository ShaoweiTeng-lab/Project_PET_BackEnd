package project_pet_backEnd.userManager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project_pet_backEnd.userManager.dto.UserDetailProfileResponse;
import project_pet_backEnd.utils.commonDto.ResponsePage;
import project_pet_backEnd.utils.commonDto.ResultResponse;
import project_pet_backEnd.user.dto.UserProfileResponse;
import project_pet_backEnd.userManager.dto.Sort;
import project_pet_backEnd.userManager.dto.UserOrderBy;
import project_pet_backEnd.userManager.dto.UserQueryParameter;
import project_pet_backEnd.userManager.service.imp.UserManagerServiceImp;
import project_pet_backEnd.utils.commonDto.Page;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
@Slf4j
@Api(tags = "會員管理")
@Validated
@RestController
@RequestMapping("/manager")
@PreAuthorize("hasAnyAuthority('會員管理')")
public class UserManagerController {
    @Autowired
    private UserManagerServiceImp userManagerService;
    /**
     *會員查詢
     * */
    @ApiOperation("會員查詢")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/users")
    public ResultResponse<ResponsePage<List<UserDetailProfileResponse>>>  getUsers(
            @RequestParam(required = false)String search,
            @RequestParam(required = false, defaultValue = "USER_CREATED") UserOrderBy orderBy,
            @RequestParam(required = false,defaultValue = "desc") Sort sort,
            @RequestParam(defaultValue = "1")@Min(1) Integer page,
            @RequestParam(defaultValue = "5")@Min(1)Integer size){
        UserQueryParameter userQueryParameter=new UserQueryParameter();
        userQueryParameter.setSearch(search);
        userQueryParameter.setOrder(orderBy);
        userQueryParameter.setSort(sort);
        userQueryParameter.setPage(page);
        userQueryParameter.setSize(size);
        ResponsePage<List<UserDetailProfileResponse>> userList = userManagerService.getUsers(userQueryParameter);
        ResultResponse rs =new ResultResponse();
        rs.setMessage(userList);
        return rs;
    }
//    public  ResponseEntity<ResultResponse> adjustUser

}
