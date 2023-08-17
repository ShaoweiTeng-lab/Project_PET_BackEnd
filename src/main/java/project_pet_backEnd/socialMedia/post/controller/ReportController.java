package project_pet_backEnd.socialMedia.post.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.socialMedia.post.dto.req.MesRepReq;
import project_pet_backEnd.socialMedia.post.dto.req.PostRepReq;
import project_pet_backEnd.socialMedia.post.service.ReportService;
import project_pet_backEnd.socialMedia.post.vo.MesReport;
import project_pet_backEnd.socialMedia.post.vo.PostReport;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer/social/report")
public class ReportController {


    @Autowired
    private ReportService reportService;

    //============  user report api ============//

    /**
     * 檢舉留言 user attribute
     */

    @PostMapping("/message/{messageId}/{userId}")
    public ResponseEntity<MesReport> create(@Valid @RequestBody MesRepReq mesRepReq, @PathVariable("userId") int userId) {
        MesReport mesReport = reportService.create(userId, mesRepReq);
        return ResponseEntity.status(HttpStatus.OK).body(mesReport);
    }


    /**
     * 檢舉貼文 user attribute @RequestAttribute("userId") Integer userId
     */

    @PostMapping("/post/{postId}/{userId}")
    public ResponseEntity<PostReport> create(@RequestBody PostRepReq postRepReq, @PathVariable("userId") int userId) {
        PostReport postReport = reportService.create(userId, postRepReq);
        return ResponseEntity.status(HttpStatus.OK).body(postReport);
    }

}
