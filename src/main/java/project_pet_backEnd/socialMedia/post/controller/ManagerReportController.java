package project_pet_backEnd.socialMedia.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.socialMedia.post.service.ReportService;
import project_pet_backEnd.socialMedia.post.vo.MesReport;
import project_pet_backEnd.socialMedia.post.vo.PostReport;

import java.util.List;

@RestController
@RequestMapping("/manager/activity/report")
public class ManagerReportController {

    @Autowired
    private ReportService reportService;

    //============  manager api ============//

    /**
     * 獲取所有貼文檢舉清單
     */

    @GetMapping("/posts")
    public ResponseEntity<List<PostReport>> getAllPostReportLists() {
        List<PostReport> postReportList = reportService.getAllPostReport();
        return ResponseEntity.status(HttpStatus.OK).body(postReportList);
    }

    /**
     * 獲取所有留言檢舉清單
     */

    @GetMapping("/messages")
    public ResponseEntity<List<MesReport>> getAllMesReportLists() {
        List<MesReport> mesReportList = reportService.getAllMesReport();
        return ResponseEntity.status(HttpStatus.OK).body(mesReportList);
    }


    /**
     * 獲取此貼文檢舉次數與相關內容
     */
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostReport> getPostReportById(@PathVariable("postId") int messageId) {


        return null;
    }


    /**
     * 獲取此留言檢舉次數與內容
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<MesReport> getMesReportById(@PathVariable("messageId") int messageId) {


        return null;
    }


    /**
     * 修改留言檢舉狀態(審核) && 審核完通知檢舉人
     */

    @PutMapping("/message/{messageId}")
    public ResponseEntity<MesReport> updateMesReportStatus(@PathVariable("messageId") int messageId) {

        return null;
    }

}
