package project_pet_backEnd.homepage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project_pet_backEnd.homepage.service.HomepageService;
import project_pet_backEnd.homepage.vo.NewsPic;
import project_pet_backEnd.user.dto.ResultResponse;

import java.util.List;

@RestController
@RequestMapping("/customer/homePage")
public class HomepageController {
    /**
     * 取得輪播圖片
     * */
    @Autowired
    private HomepageService homepageService;

    @GetMapping("/getRocPic")
    public ResponseEntity<List<String>> getRotePic(){
        return ResponseEntity.status(200).body(homepageService.getRotePic());
    }



    /**
     * 取得最新消息
     * */
    @GetMapping("/getNews")
    public ResponseEntity<ResultResponse> getNews(){
        ResultResponse rs =homepageService.getNews();
        return ResponseEntity.status(200).body(rs);
    }

    /**
     * 取得最新消息圖片
     * */

    @GetMapping("/getNewsPic")
    public List<NewsPic> getNewsPic(){ return homepageService.getNewsPic();}

}
