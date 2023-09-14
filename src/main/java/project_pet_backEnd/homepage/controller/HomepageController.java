package project_pet_backEnd.homepage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project_pet_backEnd.homepage.service.HomepageService;
import project_pet_backEnd.homepage.vo.NewsPic;
import project_pet_backEnd.homepage.vo.PicRot;
import project_pet_backEnd.homepageManage.dto.HomepageNewsRes;
import project_pet_backEnd.homepageManage.dto.NewsRes;
import project_pet_backEnd.homepageManage.service.HomepageManageService;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

@RestController
@RequestMapping("/customer/homePage")
public class HomepageController {
    /**
     * 取得輪播圖片
     * */
    @Autowired
    private HomepageService homepageService;

    @Autowired
    private HomepageManageService homepageManageService;

    /**
     * 取得最新消息
     * */
    @GetMapping("/getNews")
    public ResponseEntity<ResultResponse> getNews(){
        ResultResponse rs =homepageService.getNews();
        return ResponseEntity.status(200).body(rs);
    }


    /**
     * 取得Google Map Api key
     * */
    @GetMapping("/mapApiKey")
    public ResultResponse<String> getMapApiKey(){
        ResultResponse rs =new ResultResponse();
        rs.setMessage(homepageService.getGoogleMapApiKey());
        return rs;
    }

    /**
     * 查詢最新消息圖片
     **/
    @GetMapping("/getNewsPic")
    public ResponseEntity<ResultResponse<List<NewsPic>>> getAllNewsPic() {
        List<NewsPic> newsPic = homepageManageService.getAllNewsPic();
        ResultResponse<List<NewsPic>> rs = new ResultResponse<>();
        rs.setMessage(newsPic);
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
     * 查詢最新消息列表
     **/
    @GetMapping("/getAllNews")
    public ResponseEntity<ResultResponse<List<NewsRes>>> getAllNews() {
        List<NewsRes> newsRes = homepageManageService.getAllNews();
        ResultResponse<List<NewsRes>> rs = new ResultResponse<>();
        rs.setMessage(newsRes);
        return ResponseEntity.status(200).body(rs);
    }



}
