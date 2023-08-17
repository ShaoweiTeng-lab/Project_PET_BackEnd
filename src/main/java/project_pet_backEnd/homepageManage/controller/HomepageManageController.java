package project_pet_backEnd.homepageManage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project_pet_backEnd.homepage.vo.PicRot;
import project_pet_backEnd.homepageManage.dto.AddNewsRequest;
import project_pet_backEnd.homepageManage.dto.AddRotePicRequest;
import project_pet_backEnd.homepageManage.dto.AdjustRotePicRequest;
import project_pet_backEnd.homepageManage.service.HomepageManageService;
import project_pet_backEnd.manager.dto.AdjustManagerRequest;
import project_pet_backEnd.manager.dto.CreateManagerRequest;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/manager/homepageManager")
@Validated
@PreAuthorize("hasAnyAuthority('首頁管理')")
public class HomepageManageController {
    @Autowired
    private HomepageManageService homepageManageService;

    /**
     * 新增輪播圖
     **/

    @PostMapping("/addRotePic")
    public ResponseEntity<ResultResponse<String>> addRotePic(
            @RequestParam @NotBlank String picLocateUrl,
            @RequestParam @NotNull MultipartFile pic,
            @RequestParam @NotNull Integer picRotStatus,
            @RequestParam Date picRotStart,
            @RequestParam Date picRotEnd,
            @RequestParam @NotBlank String rotePicPicLocateUrl

    ) {
        AddRotePicRequest addRotePicRequest = new AddRotePicRequest();
        addRotePicRequest.setPicLocateUrl(picLocateUrl);
        addRotePicRequest.setPic(AllDogCatUtils.convertMultipartFileToByteArray(pic));
        addRotePicRequest.setPicRotStatus(picRotStatus);
        addRotePicRequest.setPicRotStart(picRotStart);
        addRotePicRequest.setPicRotEnd(picRotEnd);
        addRotePicRequest.setRotePicPicLocateUrl(rotePicPicLocateUrl);
        homepageManageService.addRotePic(addRotePicRequest);
        ResultResponse rs = new ResultResponse();
        rs.setMessage("新增成功");
        return ResponseEntity.status(201).body(rs);
    }

    /**
     * 修改輪播圖
     **/

    @PostMapping("/editRotePicByPicNo")
    public ResponseEntity<ResultResponse<Void>> editRotePicByPicNo(@RequestBody @Valid AdjustRotePicRequest adjustRotePicRequest) {
        ResultResponse rs = homepageManageService.editRotePicByPicNo(adjustRotePicRequest);
        return ResponseEntity.status(201).body(rs);
    }

    /**
     * 刪除輪播圖
     **/

    @GetMapping("/deleteRotePicByPicNo")
    public ResponseEntity<ResultResponse<String>> deleteRotePicByPicNo(@RequestParam("picNo") int picNo) {
        homepageManageService.deleteRotePicByPicNo(picNo);
        ResultResponse rs = new ResultResponse();
        rs.setMessage("刪除成功");
        return ResponseEntity.status(201).body(rs);
    }

    /**
     * 查詢輪播圖
     **/

    @GetMapping("/getRotePic")
    public ResponseEntity<ResultResponse<List<PicRot>>> getAllRotePic() {
        List<PicRot> rotePics = homepageManageService.getAllRotePic();
        ResultResponse<List<PicRot>> rs = new ResultResponse<>();
        rs.setMessage(rotePics);
        return ResponseEntity.status(200).body(rs);
    }

    /**
     * 新增最新消息
     **/
    @PostMapping("/addNews")
    public ResponseEntity<ResultResponse<Void>> addNews(@RequestBody @Valid AddNewsRequest addNewsRequest) {
        ResultResponse rs = homepageManageService.addNews(addNewsRequest);
        return ResponseEntity.status(201).body(rs);
    }

    /**
     * 修改最新消息
     **/

    /**
     * 刪除最新消息
     **/

    /**
     * 查詢最新消息列表
     **/


    /**
     * 新增最新消息圖片
     **/

    /**
     * 修改最新消息圖片
     **/

    /**
     * 刪除最新消息圖片
     **/

    /**
     * 查詢最新消息圖片
     **/


}
