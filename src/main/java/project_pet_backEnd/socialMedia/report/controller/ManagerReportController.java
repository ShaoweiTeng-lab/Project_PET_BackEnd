package project_pet_backEnd.socialMedia.report.controller;


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
import project_pet_backEnd.socialMedia.report.dto.req.ReviewReq;
import project_pet_backEnd.socialMedia.report.dto.res.MesRepRes;
import project_pet_backEnd.socialMedia.report.dto.res.MesReportDetails;
import project_pet_backEnd.socialMedia.report.dto.res.PostRepRes;
import project_pet_backEnd.socialMedia.report.dto.res.PostReportDetails;
import project_pet_backEnd.socialMedia.report.service.ReportService;
import project_pet_backEnd.socialMedia.report.vo.MesReport;
import project_pet_backEnd.socialMedia.report.vo.PostReport;
import project_pet_backEnd.socialMedia.util.PageRes;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

@Api(tags = "社群檢舉管理功能")
@RestController
@RequestMapping("/manager/social/report")
@Validated
public class ManagerReportController {

    @Autowired
    private ReportService reportService;


    @ApiOperation("查詢所有貼文檢舉清單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('社群管理')")
    @GetMapping("/posts")
    public ResponseEntity<ResultResponse<PageRes<PostRepRes>>> getAllPostReportLists(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                                     @RequestParam(value = "status", required = false, defaultValue = "0") Integer status) {
        ResultResponse<PageRes<PostRepRes>> postReportList = reportService.getAllPostReport(page, status);
        return ResponseEntity.status(HttpStatus.OK).body(postReportList);
    }


    @ApiOperation("查詢所有留言檢舉清單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('社群管理')")
    @GetMapping("/messages")
    public ResponseEntity<ResultResponse<PageRes<MesRepRes>>> getAllMesReportLists(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                                   @RequestParam(value = "status", required = false, defaultValue = "0") Integer status) {
        ResultResponse<PageRes<MesRepRes>> mesReportList = reportService.getAllMesReport(page, status);
        return ResponseEntity.status(HttpStatus.OK).body(mesReportList);
    }


    @ApiOperation("查詢單一貼文檢舉細節")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('社群管理')")
    @GetMapping("/posts/{postReportId}")
    public ResponseEntity<ResultResponse<PostReportDetails>> getPostReportById(@PathVariable("postReportId") int postReportId) {
        ResultResponse<PostReportDetails> reportDetail = reportService.getPostReportById(postReportId);
        return ResponseEntity.status(HttpStatus.OK).body(reportDetail);
    }


    @ApiOperation("查詢單一留言檢舉細節")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('社群管理')")
    @GetMapping("/messages/{mesReportId}")
    public ResponseEntity<ResultResponse<MesReportDetails>> getMesReportById(@PathVariable("mesReportId") int mesReportId) {
        ResultResponse<MesReportDetails> reportDetail = reportService.getMesReportById(mesReportId);
        return ResponseEntity.status(HttpStatus.OK).body(reportDetail);
    }

    @ApiOperation("審核貼文檢舉")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('社群管理')")
    @PutMapping("/post/{postReportId}")
    public ResponseEntity<ResultResponse<PostReport>> updatePostReportStatus(@PathVariable("postReportId") int postReportId, @RequestBody ReviewReq reviewReq) {
        ResultResponse<PostReport> response = reportService.reviewPostReportById(postReportId, reviewReq);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation("審核留言檢舉")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('社群管理')")
    @PutMapping("/message/{mesReportId}")
    public ResponseEntity<ResultResponse<MesReport>> updateMesReportStatus(@PathVariable("mesReportId") int mesReportId, @RequestBody ReviewReq reviewReq) {
        ResultResponse<MesReport> response = reportService.reviewMesReportById(mesReportId, reviewReq);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
