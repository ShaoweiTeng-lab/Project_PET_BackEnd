package project_pet_backEnd.socialMedia.post.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project_pet_backEnd.socialMedia.post.vo.MesReport;

@RestController
@RequestMapping("/user/social/report")
public class MesReportController {

    /**
     * user 檢舉留言
     */

    @PostMapping("/message/{messageId}")
    public MesReport create() {


        return null;
    }

    /**
     * user 檢舉留言
     */


}
