package project_pet_backEnd.socialMedia.activityUser.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.socialMedia.activityManager.dto.ActivityRes;
import project_pet_backEnd.socialMedia.activityManager.vo.Activity;
import project_pet_backEnd.socialMedia.activityUser.dto.JoinReq;
import project_pet_backEnd.socialMedia.activityUser.service.UserActivityService;
import project_pet_backEnd.socialMedia.activityUser.vo.JoinActivity;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

@Api(tags = "使用者活動功能")
@RestController
@RequestMapping("/user/activity")
@Validated
public class UserActivityController {
    @Autowired
    private UserActivityService userActivityService;


    //範例: https://www.pettalk.tw/activity/category/%E5%AF%B5%E7%89%A9%E6%B4%BB%E5%8B%95

    @ApiOperation("User查詢熱門活動(限制5筆、使用參加人數進行排序)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/hot")
    public ResponseEntity<ResultResponse<List<ActivityRes>>> getHotActivities() {
        ResultResponse<List<ActivityRes>> hotActivities = userActivityService.getHotActivities();
        return ResponseEntity.status(HttpStatus.OK).body(hotActivities);

    }

    @ApiOperation("查看所有活動(分頁 每頁10筆, sort by活動Id)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/all")
    public ResponseEntity<ResultResponse<Page<Activity>>> getAllActivitiesSortAndPaging(@RequestParam("page") int page) {
        ResultResponse<Page<Activity>> activities = userActivityService.getAllActivities(page);
        return ResponseEntity.status(HttpStatus.OK).body(activities);

    }

    @ApiOperation("活動關鍵字搜尋(活動內容)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/search")
    public ResponseEntity<ResultResponse<Page<Activity>>> searchActivity(@RequestParam("activityContent") String content) {
        System.out.println(content);
        ResultResponse<Page<Activity>> searchResult = userActivityService.queryWithText(content);
        return ResponseEntity.status(HttpStatus.OK).body(searchResult);

    }


    @ApiOperation("查看單一活動資訊")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/{activityId}")
    public ResponseEntity<ResultResponse<Activity>> findActivityById(@PathVariable("activityId") int activityId) {
        ResultResponse<Activity> activity = userActivityService.getActivityById(activityId);
        return ResponseEntity.status(HttpStatus.OK).body(activity);

    }


    @ApiOperation("User加入活動")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/join/{userId}")
    public ResponseEntity<ResultResponse<String>> joinActivity(@PathVariable("userId") int userId, @RequestBody JoinReq joinReq) {
        ResultResponse<String> response = userActivityService.joinActivity(userId, joinReq);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


    @ApiOperation("User退出活動")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/leave/{userId}")
    public ResponseEntity<ResultResponse<String>> leaveActivity(@PathVariable("userId") int userId, @RequestBody JoinReq joinReq) {
        ResultResponse<String> response = userActivityService.leaveActivity(userId, joinReq);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


    @ApiOperation("User參加活動清單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/joinList/{userId}")
    public ResponseEntity<ResultResponse<Page<JoinActivity>>> getUerJoinDetails(@PathVariable("userId") int userId) {

        return ResponseEntity.status(HttpStatus.OK).body(null);

    }


}
