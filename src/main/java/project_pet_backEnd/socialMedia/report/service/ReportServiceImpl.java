package project_pet_backEnd.socialMedia.report.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project_pet_backEnd.socialMedia.report.dao.ReportDao;
import project_pet_backEnd.socialMedia.report.dto.req.MessageReportRequest;
import project_pet_backEnd.socialMedia.report.dto.req.PostReportRequest;
import project_pet_backEnd.socialMedia.report.vo.MesReport;
import project_pet_backEnd.socialMedia.report.vo.PostReport;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private volatile String emailFrom;


    @Override
    public ResultResponse<MesReport> create(int userId, MessageReportRequest messageReportRequest) {
        MesReport mesReport = new MesReport();
        mesReport.setMessageId(messageReportRequest.getMessageId());
        mesReport.setUserId(messageReportRequest.getUserId());
        mesReport.setMessageReportContent(messageReportRequest.getMesRepContent());
        /**
         * message status
         * 0: 審核通過
         * 1: 審核未過
         * 2: 審核中
         */
        mesReport.setMessageReportStatus(2);
        MesReport newMesReport = reportDao.createMesReport(mesReport);

        ResultResponse<MesReport> resultResponse = new ResultResponse<>();
        resultResponse.setMessage(newMesReport);
        return resultResponse;
    }

    @Override
    public ResultResponse<PostReport> create(int userId, PostReportRequest postReportRequest) {
        PostReport postReport = new PostReport();
        postReport.setPostId(postReportRequest.getPostId());
        postReport.setPostRepContent(postReportRequest.getReportContent());
        postReport.setUserId(postReportRequest.getUserId());
        /**
         * post status
         * 0: 審核通過
         * 1: 審核未過
         * 2: 審核中
         */
        postReport.setPostRepostStatus(2);
        PostReport newPostReport = reportDao.createPostReport(postReport);

        ResultResponse<PostReport> resultResponse = new ResultResponse<>();
        resultResponse.setMessage(newPostReport);
        return resultResponse;
    }

    /**
     * manager (page 10 size and sort by report count) - 分頁查詢 + 排序
     */
    @Override
    public ResultResponse<List<MesReport>> getAllMesReport(Integer managerId) {
        List<MesReport> allMesReport = reportDao.getAllMesReport();
        ResultResponse<List<MesReport>> resultResponse = new ResultResponse<>();
        resultResponse.setMessage(allMesReport);
        return resultResponse;
    }

    @Override
    public ResultResponse<List<PostReport>> getAllPostReport(Integer managerId) {
        List<PostReport> allPostReport = reportDao.getAllPostReport();
        ResultResponse<List<PostReport>> resultResponse = new ResultResponse<>();
        resultResponse.setMessage(allPostReport);
        return resultResponse;
    }

    /**
     *查詢檢舉細節 列出
     */

    @Override
    public ResultResponse<MesReport> getMesReportById(int mesRepId, Integer managerId) {
        //get message report by id
//        reportDao.
        return null;
    }

    @Override
    public ResultResponse<PostReport> getPostReportById(int postRepId, Integer managerId) {
        return null;
    }

    @Override
    public ResultResponse<MesReport> reviewReportById(int mesRepId, Integer managerId) {
        return null;
    }

    @Override
    public ResultResponse<String> sendEmailToUsers(String[] toEmail, String body, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        try {
            for (String email : toEmail) {
                message.setFrom(emailFrom);
                message.setTo(email);
                message.setText(body);
                message.setSubject(subject);
                mailSender.send(message);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ResultResponse<String> resultResponse = new ResultResponse<>();
        resultResponse.setMessage("發送成功");

        return resultResponse;
    }

}
