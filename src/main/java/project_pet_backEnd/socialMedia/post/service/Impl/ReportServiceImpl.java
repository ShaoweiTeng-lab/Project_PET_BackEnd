package project_pet_backEnd.socialMedia.post.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_pet_backEnd.socialMedia.post.dao.ReportDao;
import project_pet_backEnd.socialMedia.post.dto.req.MesRepReq;
import project_pet_backEnd.socialMedia.post.dto.req.PostRepReq;
import project_pet_backEnd.socialMedia.post.service.ReportService;
import project_pet_backEnd.socialMedia.post.vo.MesReport;
import project_pet_backEnd.socialMedia.post.vo.PostReport;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;


    @Override
    public MesReport create(int userId, MesRepReq mesRepReq) {
        MesReport mesReport = new MesReport();
        mesReport.setMessageId(mesRepReq.getMessageId());
        mesReport.setUserId(mesRepReq.getUserId());
        mesReport.setMessageReportContent(mesRepReq.getMesRepContent());
        /**
         * message status
         * 0: 審核通過
         * 1: 審核未過
         * 2: 審核中
         */
        mesReport.setMessageReportStatus(2);
        MesReport newMesReport = reportDao.createMesReport(mesReport);
        return newMesReport;
    }

    @Override
    public PostReport create(int userId, PostRepReq postRepReq) {
        PostReport postReport = new PostReport();
        postReport.setPostId(postRepReq.getPostId());
        postReport.setPostRepContent(postRepReq.getReportContent());
        postReport.setUserId(postRepReq.getUserId());
        /**
         * post status
         * 0: 審核通過
         * 1: 審核未過
         * 2: 審核中
         */
        postReport.setPostRepostStatus(2);
        PostReport newPostReport = reportDao.createPostReport(postReport);
        return newPostReport;
    }

    /**
     * manager
     */
    @Override
    public List<MesReport> getAllMesReport() {
        List<MesReport> allMesReport = reportDao.getAllMesReport();
        return allMesReport;
    }

    @Override
    public List<PostReport> getAllPostReport() {
        List<PostReport> allPostReport = reportDao.getAllPostReport();
        return allPostReport;
    }

    @Override
    public MesReport getMesReportById(int mesRepId) {
        return null;
    }

    @Override
    public PostReport getPostReportById(int postRepId) {
        return null;
    }
}
