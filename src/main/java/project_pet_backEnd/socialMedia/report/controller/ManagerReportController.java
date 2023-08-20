package project_pet_backEnd.socialMedia.report.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.socialMedia.report.service.ReportService;
import project_pet_backEnd.socialMedia.report.vo.MesReport;
import project_pet_backEnd.socialMedia.report.vo.PostReport;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;
@Api(tags = "活動檢舉管理功能")
@RestController
@RequestMapping("/manager/activity/report")
@Validated
public class ManagerReportController {

    @Autowired
    private ReportService reportService;



    @ApiOperation("查詢所有貼文檢舉清單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    //@PreAuthorize("hasAnyAuthority('社群管理')")
    @GetMapping("/posts/{managerId}")
    public ResponseEntity<ResultResponse<List<PostReport>>> getAllPostReportLists(@PathVariable("managerId") Integer managerId) {
        ResultResponse postReportList = reportService.getAllPostReport(managerId);
        return ResponseEntity.status(HttpStatus.OK).body(postReportList);
    }


    @ApiOperation("查詢所有留言檢舉清單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    //@PreAuthorize("hasAnyAuthority('社群管理')")
    @GetMapping("/messages/{managerId}")
    public ResponseEntity<ResultResponse<List<MesReport>>> getAllMesReportLists(@PathVariable("managerId") Integer managerId) {
        ResultResponse mesReportList = reportService.getAllMesReport(managerId);
        return ResponseEntity.status(HttpStatus.OK).body(mesReportList);
    }



    @ApiOperation("查詢此貼文檢舉次數與相關內容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    //@PreAuthorize("hasAnyAuthority('社群管理')")
    @GetMapping("/posts/{postId}")
    public ResponseEntity<ResultResponse<PostReport>> getPostReportById(@PathVariable("postId") int messageId) {


        return null;
    }



    @ApiOperation("查詢單一留言檢舉內容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    //@PreAuthorize("hasAnyAuthority('社群管理')")
    @GetMapping("/messages/{mesReportId}")
    public ResponseEntity<ResultResponse<MesReport>> getMesReportById(@PathVariable("mesReportId") int mesReportId) {


        return null;
    }


    @ApiOperation("修改單一留言檢舉狀態(審核) && 審核完通知檢舉人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    //@PreAuthorize("hasAnyAuthority('社群管理')")
    @PutMapping("/message/{mesReportId}")
    public ResponseEntity<ResultResponse<MesReport>> updateMesReportStatus(@PathVariable("mesReportId") int mesReportId) {

        return null;
    }

}
