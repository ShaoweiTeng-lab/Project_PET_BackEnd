package project_pet_backEnd.socialMedia.activityManager.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.socialMedia.activityManager.dto.ActivityReq;
import project_pet_backEnd.socialMedia.activityManager.service.ActivityService;
import project_pet_backEnd.socialMedia.activityManager.vo.Activity;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

@Api(tags = "活動管理")
@RestController
@RequestMapping("/manager/activity")
@Validated
public class ManagerActivityController {

    //需使用的attribute  @RequestAttribute("managerId") Integer managerId
    @Autowired
    private ActivityService activityService;

    @ApiOperation("社群管理員建立活動")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    //@PreAuthorize("hasAnyAuthority('社群管理')")
    @PostMapping
    public ResponseEntity<ResultResponse<Activity>> createActivity(@RequestBody ActivityReq activityReq) {
        ResultResponse<Activity> activityResult = activityService.create(activityReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(activityResult);

    }

    @ApiOperation("社群管理員修改活動內容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })

    //@PreAuthorize("hasAnyAuthority('社群管理')")
    @PutMapping("/{activityId}")
    public ResponseEntity<ResultResponse<Activity>> updateActivity(@RequestBody ActivityReq activityReq) {
        return null;

    }


    @ApiOperation("社群管理員取消活動")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })

    //@PreAuthorize("hasAnyAuthority('社群管理')")
    @PutMapping("/cancel/{activityId}")
    public ResponseEntity<ResultResponse<Activity>> cancelActivity() {
        return null;

    }

    @ApiOperation("社群管理員查詢活動清單(包含歷史清單)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })

    //@PreAuthorize("hasAnyAuthority('社群管理')")
    public ResponseEntity<ResultResponse<List<Activity>>> getAllActivities() {
        return null;

    }

}
