package project_pet_backEnd.socialMedia.post.controller;



import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.socialMedia.post.dto.req.PostRepReq;
import project_pet_backEnd.socialMedia.post.vo.PostReport;

@RestController
@RequestMapping("/user/social/report")
public class PostReportController {

    /**
     * 貼文檢舉 user attribute
     */

    @PostMapping("/post")
    public PostReport create(@RequestBody PostRepReq postRepReq, @RequestAttribute("userId") Integer userId) {

        return null;
    }
}
