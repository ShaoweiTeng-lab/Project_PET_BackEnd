package project_pet_backEnd.homepageManage.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project_pet_backEnd.homepage.vo.News;
import project_pet_backEnd.homepage.vo.NewsPic;
import project_pet_backEnd.homepage.vo.PicRot;
import project_pet_backEnd.homepageManage.dto.*;
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
@RequestMapping("/manager/homepageManage")
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
            @RequestParam String picRotStart,
            @RequestParam String picRotEnd

    ) {
        AddRotePicRequest addRotePicRequest = new AddRotePicRequest();
        addRotePicRequest.setPicLocateUrl(picLocateUrl);
        addRotePicRequest.setPic(AllDogCatUtils.convertMultipartFileToByteArray(pic));
        addRotePicRequest.setPicRotStatus(picRotStatus);
        addRotePicRequest.setPicRotStart(picRotStart);
        addRotePicRequest.setPicRotEnd(picRotEnd);
        homepageManageService.addRotePic(addRotePicRequest);
        ResultResponse rs = new ResultResponse();
        rs.setMessage("新增成功");
        return ResponseEntity.status(201).body(rs);
    }

    /**
     * 修改輪播圖
     **/

    @PutMapping("/editRotePicByPicNo")
    public ResponseEntity<ResultResponse<String>> editRotePicByPicNo(@RequestBody @Valid AdjustRotePicRequest adjustRotePicRequest) {
        ResultResponse rs = homepageManageService.editRotePicByPicNo(adjustRotePicRequest);
        return ResponseEntity.status(201).body(rs);
    }

    /**
     * 刪除輪播圖
     **/

    @DeleteMapping("/deleteRotePicByPicNo")
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
     * 查詢單一輪播圖
     **/
    @GetMapping("/getOneRotePic")
    public ResponseEntity<ResultResponse<PicRotRes>> getOneRotePic(@RequestParam("picNo") int picNo) {
        ResultResponse<PicRotRes> rs = homepageManageService.getOneRotePic(picNo);
        return ResponseEntity.status(200).body(rs);

    }

    /**
     * 新增最新消息
     **/
    @PostMapping("/addNews")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<ResultResponse<Integer>> addNews(@RequestBody @Valid AddNewsRequest addNewsRequest) {
        ResultResponse rs = homepageManageService.addNews(addNewsRequest);
        return ResponseEntity.status(200).body(rs);
    }

    /**
     * 修改最新消息
     **/
    @PutMapping("/editNews")
    public ResponseEntity<ResultResponse<String>> editNewsByNewsNo(@RequestBody @Valid AdjustNewsRequest adjustNewsRequest) {
        ResultResponse rs = homepageManageService.editNewsByNewsNo(adjustNewsRequest);
        return ResponseEntity.status(200).body(rs);
    }

    /**
     * 刪除最新消息
     **/
    @DeleteMapping("/deleteNewsByNewsNo")
    public ResponseEntity<ResultResponse<String>> deleteNewsByPicNo(@RequestParam("newsNo") int newsNo) {
        homepageManageService.deleteNewsByNewsNo(newsNo);
        ResultResponse rs = new ResultResponse();
        rs.setMessage("刪除成功");
        return ResponseEntity.status(201).body(rs);
    }

    /**
     * 查詢最新消息列表
     **/
    @GetMapping("/getAllNews")
    public ResponseEntity<ResultResponse<List<NewsRes>>> getAllNews() {
        List<NewsRes> newsRes = homepageManageService.getAllNews();
        ResultResponse<List<NewsRes>> rs = new ResultResponse<>();
        rs.setMessage(newsRes);
        return ResponseEntity.status(200).body(rs);
    }

    /**
     * 查詢單一最新消息
     **/
    @GetMapping("/getOneNews")
    public ResponseEntity<ResultResponse<News>> getOneNews(@RequestParam("newsNo") int newsNo) {
        ResultResponse<News> rs = homepageManageService.getOneNews(newsNo);
        return ResponseEntity.status(200).body(rs);
    }

    /**
     * 查詢首頁最新消息
     **/
    @GetMapping("/getHomepageNewsPic")
    public ResponseEntity<ResultResponse<List<HomepageNewsRes>>> getHomepageNews() {
        ResultResponse<List<HomepageNewsRes>> rs = homepageManageService.getHomePageNews();
        return ResponseEntity.status(200).body(rs);
    }

    /**
     * 新增最新消息圖片
     **/
    @PostMapping("/addNewsPic")
    public ResponseEntity<ResultResponse<String>> addNewsPic(
            @RequestParam @NotNull Integer newsNo,
            @RequestParam @NotNull MultipartFile pic
    ) {
        AddNewsPicRequest addNewsPicRequest = new AddNewsPicRequest();
        addNewsPicRequest.setNewsNo(newsNo);
        addNewsPicRequest.setPic(AllDogCatUtils.convertMultipartFileToByteArray(pic));
        ResultResponse rs = homepageManageService.addNewsPic(addNewsPicRequest);
        return ResponseEntity.status(201).body(rs);
    }


    /**
     * 修改最新消息圖片
     **/
    @PutMapping("/editNewsPic")
    public ResponseEntity<ResultResponse<String>> editNewsPicByPicNo(@ModelAttribute AdjustNewsPicRequest adjustNewsPicRequest) {
        ResultResponse rs = homepageManageService.editNewsPicByPicNo(adjustNewsPicRequest);
        rs.setMessage("修改成功");
        return ResponseEntity.status(201).body(rs);
    }


    /**
     * 刪除最新消息圖片
     **/
    @DeleteMapping("/deleteNewsPicByPicNo")
    public ResponseEntity<ResultResponse<String>> deleteNewsPicByPicNo(@RequestParam("newsPicNo") int newsPicNo) {
        homepageManageService.deleteNewsPicByPicNo(newsPicNo);
        ResultResponse rs = new ResultResponse();
        rs.setMessage("刪除成功");
        return ResponseEntity.status(201).body(rs);
    }




    /**
     * 查詢單一最新消息圖片
     **/
    @GetMapping("/getOneNewsPic")
    public ResponseEntity<ResultResponse<NewsPicRes>> getOneNewsPic(@RequestParam("newsNo") int newsNo) {
        ResultResponse<NewsPicRes> rs = homepageManageService.getOneNewsPicByNewsNo(newsNo);
        return ResponseEntity.status(200).body(rs);

    }

}
