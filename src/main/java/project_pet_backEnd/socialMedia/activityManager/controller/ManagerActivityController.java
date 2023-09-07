package project_pet_backEnd.socialMedia.activityManager.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.socialMedia.activityManager.dto.ActivityReq;
import project_pet_backEnd.socialMedia.activityManager.dto.ActivityRes;
import project_pet_backEnd.socialMedia.activityManager.service.ActivityService;
import project_pet_backEnd.socialMedia.util.PageRes;
import project_pet_backEnd.utils.commonDto.ResultResponse;


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
    @PreAuthorize("hasAnyAuthority('社群管理')")
    @PostMapping
    public ResponseEntity<ResultResponse<ActivityRes>> createActivity(@RequestBody ActivityReq activityReq) {
        ResultResponse<ActivityRes> activityResult = activityService.create(activityReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(activityResult);

    }

    @ApiOperation("社群管理員修改活動內容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('社群管理')")
    @PutMapping("/{activityId}")
    public ResponseEntity<ResultResponse<ActivityRes>> updateActivity(@PathVariable("activityId") int activityId, @RequestBody ActivityReq activityReq) {
        ResultResponse<ActivityRes> updateActivity = activityService.update(activityId, activityReq);
        return ResponseEntity.status(HttpStatus.OK).body(updateActivity);
    }


    @ApiOperation("社群管理員取消活動")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('社群管理')")
    @PutMapping("/cancel/{activityId}")
    public ResponseEntity<ResultResponse<String>> cancelActivity(@PathVariable("activityId") int activityId) {
        ResultResponse<String> cancelResult = activityService.cancel(activityId);
        return ResponseEntity.status(HttpStatus.OK).body(cancelResult);

    }

    @ApiOperation("社群管理員查詢單一活動資訊")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('社群管理')")
    @GetMapping("/{activityId}")
    public ResponseEntity<ResultResponse<ActivityRes>> getActivityById(@PathVariable("activityId") int activityId) {
        ResultResponse<ActivityRes> activity = activityService.findActivityById(activityId);
        return ResponseEntity.status(HttpStatus.OK).body(activity);
    }

    //@RequestParam Optional<Integer> page
    @ApiOperation("社群管理員查詢活動清單(包含歷史清單)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('社群管理')")
    @GetMapping("/all")
    public ResponseEntity<ResultResponse<PageRes<ActivityRes>>> getAllActivities(@RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        ResultResponse<PageRes<ActivityRes>> activities = activityService.getAllActivities(page, 7);
        return ResponseEntity.status(HttpStatus.OK).body(activities);

    }


    @ApiOperation("社群管理員查詢活動狀態")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('社群管理')")
    @GetMapping("/status")
    public ResponseEntity<ResultResponse<PageRes<ActivityRes>>> getActivitiesByStatus(@RequestParam(value = "page", required = false, defaultValue = "0") int page, @RequestParam("status") int status) {
        ResultResponse<PageRes<ActivityRes>> activities = activityService.getAcByStatus(status, page);
        return ResponseEntity.status(HttpStatus.OK).body(activities);

    }

}

