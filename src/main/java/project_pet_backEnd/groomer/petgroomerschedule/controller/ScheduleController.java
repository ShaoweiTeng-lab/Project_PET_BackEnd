package project_pet_backEnd.groomer.petgroomerschedule.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project_pet_backEnd.groomer.petgroomerschedule.dto.response.ListForScheduleRes;
import project_pet_backEnd.groomer.petgroomerschedule.service.GroomerScheduleService;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

@Api(tags = "美容師班表功能")
@RestController
@Validated
public class ScheduleController {

    @Autowired
    GroomerScheduleService groomerScheduleService;


    @ApiOperation("Man排班頁面表列美容師")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @GetMapping("/manager/schedulePage")
    public ResultResponse<List<ListForScheduleRes>> getAllGroomerForSchedule(){
        List<ListForScheduleRes> allGroomerForSchedule = groomerScheduleService.getAllGroomerForSchedule();
        ResultResponse<List<ListForScheduleRes>> resultResponse = new ResultResponse<>();
        resultResponse.setMessage(allGroomerForSchedule);
        return resultResponse;
    }

}
