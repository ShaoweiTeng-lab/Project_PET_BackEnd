package project_pet_backEnd.socialMedia.activityUser.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.socialMedia.activityManager.vo.Activity;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

@Api(tags = "使用者活動功能")
@RestController
@RequestMapping("/user/activity")
@Validated
public class UserActivityController {

    //範例: https://www.pettalk.tw/activity/category/%E5%AF%B5%E7%89%A9%E6%B4%BB%E5%8B%95

    @ApiOperation("User查詢熱門活動(限制5筆、使用參加人數進行排序)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/hot")
    public ResponseEntity<ResultResponse<List<Activity>>> getHotActivities() {
        return null;

    }

    @ApiOperation("查看所有活動(分頁 每頁10筆, sort by活動建立時間)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/all")
    public ResponseEntity<ResultResponse<List<Activity>>> getAllActivitiesSortAndPaging() {
        return null;

    }

    @ApiOperation("活動關鍵字搜尋(活動內容or 活動title 做查詢依據)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/search")
    public ResponseEntity<ResultResponse<List<Activity>>> searchActivity() {
        return null;

    }


    @ApiOperation("查看單一活動資訊")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/{activityId}")
    public ResponseEntity<ResultResponse<Activity>> findActivityById(int activityId) {
        return null;

    }


    @ApiOperation("User加入活動")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/{activityId}")
    public ResponseEntity<ResultResponse<Activity>> joinActivity(@PathVariable("activityId") int activityId) {
        return null;

    }


    @ApiOperation("User退出活動")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/{activityId}")
    public ResponseEntity<ResultResponse<Activity>> leaveActivity(@PathVariable("activityId") int activityId) {
        return null;

    }


}
