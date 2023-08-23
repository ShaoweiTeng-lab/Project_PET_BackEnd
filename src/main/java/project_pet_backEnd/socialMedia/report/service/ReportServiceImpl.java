package project_pet_backEnd.socialMedia.report.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.socialMedia.post.dao.PostDao;
import project_pet_backEnd.socialMedia.post.vo.POST;
import project_pet_backEnd.socialMedia.postMessage.dao.MessageDao;
import project_pet_backEnd.socialMedia.postMessage.vo.Message;
import project_pet_backEnd.socialMedia.report.dao.MesReportQueryDao;
import project_pet_backEnd.socialMedia.report.dao.PostReportQueryDao;
import project_pet_backEnd.socialMedia.report.dao.ReportDao;
import project_pet_backEnd.socialMedia.report.dto.req.ReportRequest;
import project_pet_backEnd.socialMedia.report.dto.req.ReviewReq;
import project_pet_backEnd.socialMedia.report.dto.res.MesRepRes;
import project_pet_backEnd.socialMedia.report.dto.res.MesReportDetails;
import project_pet_backEnd.socialMedia.report.dto.res.PostRepRes;
import project_pet_backEnd.socialMedia.report.dto.res.PostReportDetails;
import project_pet_backEnd.socialMedia.report.vo.MesReport;
import project_pet_backEnd.socialMedia.report.vo.PostReport;
import project_pet_backEnd.socialMedia.util.DateUtils;
import project_pet_backEnd.socialMedia.util.PageRes;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private MesReportQueryDao mesRepDao;
    @Autowired
    private PostReportQueryDao postRepDao;
    @Autowired
    private MessageDao messageDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private volatile String emailFrom;

    //================================  審核留言檢舉  ================================//

    /**
     * post and message status
     * 0: 審核中(貼文無法顯示)
     * 1: 審核通過
     * 2: 審核未過
     */
    @Override
    public ResultResponse<MesReport> reviewMesReportById(int mesRepId, ReviewReq reviewReq) {
        MesReport mesReport = mesRepDao.findById(mesRepId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "找不到此筆留言檢舉"));
        ResultResponse<MesReport> response = new ResultResponse<>();

        if (reviewReq.getStatus() == 1) {
            //檢舉審核通過
            String messageBody = "你們對留言" + reviewReq.getReportId() + "的檢舉已經成功了，留言已經下架";
            boolean fromResult = sendEmailToUsers(reviewReq.getFromUserEmail(), messageBody, reviewReq.getReportContent());
            String messageReportBody = "你的留言因" + reviewReq.getReportContent() + "情形已經被下架，請注意言論";
            boolean toResult = sendEmailToUsers(reviewReq.getToUserEmail(), messageReportBody, "你的留言被下架了");
            if (!fromResult || !toResult) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "郵件發送失敗");
        }
        if (reviewReq.getStatus() == 2) {
            //檢舉審核未通過，將留言改回預設狀態 0 上架
            Integer messageId = mesReport.getMessage().getMessageId();
            Message message = messageDao.findById(messageId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到此筆留言"));
            //改回上架狀態
            message.setMessageStatus(0);
            Message saveMes = messageDao.save(message);
        }
        mesReport.setMessageReportStatus(reviewReq.getStatus());
        MesReport saveResult = mesRepDao.save(mesReport);
        response.setMessage(saveResult);
        return response;
    }

    //================================  審核貼文檢舉  ================================//
    @Override
    public ResultResponse<PostReport> reviewPostReportById(int postRepId, ReviewReq reviewReq) {
        PostReport postReport = postRepDao.findById(postRepId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到此筆貼文檢舉"));
        ResultResponse<PostReport> response = new ResultResponse<>();
        if (reviewReq.getStatus() == 1) {
            //檢舉審核通過
            String messageBody = "你們對貼文" + reviewReq.getReportId() + "的檢舉已經成功了，留言已經移除";
            boolean fromResult = sendEmailToUsers(reviewReq.getFromUserEmail(), messageBody, reviewReq.getReportContent());
            String messageReportBody = "你的貼文因" + reviewReq.getReportContent() + "情形已經被移除，請注意言論";
            boolean toResult = sendEmailToUsers(reviewReq.getToUserEmail(), messageReportBody, "你的留言被移除了");
            if (!fromResult || !toResult) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "郵件發送失敗");
        }
        if (reviewReq.getStatus() == 2) {
            //檢舉審核未通過-將貼文改回上架狀態 default:0

            Integer postId = postReport.getPost().getPostId();
            POST post = postDao.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到此筆留言"));
            post.setPostStatus(0);
            POST savePost = postDao.save(post);

        }
        postReport.setPostRepostStatus(reviewReq.getStatus());
        PostReport saveResult = postRepDao.save(postReport);
        response.setMessage(saveResult);
        return response;
    }

    //================================  發送郵件   ================================//
    boolean sendEmailToUsers(String toEmail, String body, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        try {
            message.setFrom(emailFrom);
            message.setTo(toEmail);
            message.setText(body);
            message.setSubject(subject);
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    //================================  建立留言檢舉   ================================//
    @Override
    public ResultResponse<String> createMesRep(int userId, int messageId, ReportRequest reportRequest) {
        //查看要檢舉的留言是否存在
        Optional<Message> message = messageDao.findById(messageId);
        if (!message.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "此留言不存在");

        //檢查是否檢舉到自己的留言
        if (userId == message.get().getUserId())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "請不要檢舉自己的留言");

        //避免使用者重複檢舉
        MesReport reportExist = reportDao.checkMesExist(messageId);
        if (reportExist != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "此留言已被檢舉，請勿重複檢舉");
        }
        //發email通知被檢舉人 ...

        //將此留言暫時下架
        Message mesStatus = message.get();
        mesStatus.setMessageStatus(1);
        Message saveResult = messageDao.save(mesStatus);

        if (saveResult.getMessageStatus() != 1) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "留言下架失敗");
        }

        MesReport mesReport = new MesReport();
        mesReport.setMessageId(messageId);
        mesReport.setUserId(userId);
        mesReport.setMessageReportContent(reportRequest.getReportContent());
        // 預設狀態是0-代表審核中
        mesReport.setMessageReportStatus(0);
        ResultResponse<String> resultResponse = new ResultResponse<>();
        try {
            reportDao.createMesReport(mesReport);
            resultResponse.setMessage("檢舉成功");
        } catch (Exception e) {
            resultResponse.setMessage("檢舉失敗，請再檢舉一次");
        }
        return resultResponse;
    }

    //================================  建立貼文檢舉   ================================//
    @Override
    public ResultResponse<String> createPostRep(int userId, int postId, ReportRequest reportRequest) {
        //查看要檢舉的留言是否存在
        Optional<POST> post = postDao.findById(postId);
        if (!post.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "此貼文不存在");

        //檢查是否檢舉到自己的留言
        if (userId == post.get().getUserId())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "請不要檢舉自己的貼文");

        //避免使用者重複檢舉
        PostReport reportExist = reportDao.checkPostExist(postId);
        if (reportExist != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "此貼文已被檢舉，請勿重複檢舉");
        }

        //發email通知被檢舉人 ...

        //將此貼文暫時下架
        POST postStatus = post.get();
        postStatus.setPostStatus(1);
        POST saveResult = postDao.save(postStatus);

        if (saveResult.getPostStatus() != 1) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "貼文下架失敗");
        }
        PostReport postReport = new PostReport();
        postReport.setPostId(postId);
        postReport.setPostRepContent(reportRequest.getReportContent());
        postReport.setUserId(userId);
        // 將檢舉狀態預設為0
        postReport.setPostRepostStatus(0);
        ResultResponse<String> resultResponse = new ResultResponse<>();
        try {
            reportDao.createPostReport(postReport);
            resultResponse.setMessage("檢舉成功");
        } catch (Exception e) {
            resultResponse.setMessage("檢舉失敗，請再檢舉一次");
        }
        return resultResponse;
    }

    //================================  查詢所有留言檢舉   ================================//

    @Override
    public ResultResponse<PageRes<MesRepRes>> getAllMesReport(Integer page, Integer status) {
        Page<MesReport> mesReportPage = mesRepDao.findAllByMessageReportStatus(PageRequest.of
                (page, 10, Sort.Direction.DESC, "messageCreateTime"), status);

        List<MesReport> mesReports = new ArrayList<>();
        List<MesRepRes> mesRepResList = new ArrayList<>();

        if (mesReportPage != null && mesReportPage.hasContent()) {
            mesReports = mesReportPage.getContent();
        }

        for (MesReport mesReport : mesReports) {
            MesRepRes repRes = new MesRepRes();
            repRes.setMesRepId(mesReport.getMesRepId());
            repRes.setMesRepContent(mesReport.getMessageReportContent());
            repRes.setMesContent(mesReport.getMessage().getMessageContent());
            repRes.setReportTime(DateUtils.dateTimeSqlToStr(mesReport.getMessageCreateTime()));
            repRes.setReportStatus(mesReport.getMessageReportStatus());
            repRes.setMesStatus(mesReport.getMessage().getMessageStatus());

            mesRepResList.add(repRes);
        }

        PageRes pageRes = new PageRes();
        pageRes.setResList(mesRepResList);
        pageRes.setCurrentPageNumber(mesReportPage.getNumber());
        pageRes.setPageSize(mesReportPage.getSize());
        pageRes.setTotalPage(mesReportPage.getTotalPages());
        pageRes.setCurrentPageDataSize(mesReportPage.getNumberOfElements());

        ResultResponse<PageRes<MesRepRes>> response = new ResultResponse<>();
        response.setMessage(pageRes);
        return response;
    }


    //================================  查詢所有貼文檢舉   ================================//
    @Override
    public ResultResponse<PageRes<PostRepRes>> getAllPostReport(Integer page, Integer status) {

        Page<PostReport> postReportPage =
                postRepDao.findAllByPostRepostStatus(PageRequest.of
                        (page, 10, Sort.by("createTime").descending()), status);


        List<PostReport> postReports = new ArrayList<>();
        List<PostRepRes> postRepResList = new ArrayList<>();

        if (postReportPage != null && postReportPage.hasContent()) {
            postReports = postReportPage.getContent();
        }

        for (PostReport postReport : postReports) {
            PostRepRes postRepRes = new PostRepRes();
            postRepRes.setPostRepId(postReport.getPostRepId());
            postRepRes.setPostRepContent(postReport.getPostRepContent());
            postRepRes.setPostContent(postReport.getPost().getPostContent());
            postRepRes.setReportTime(DateUtils.dateTimeSqlToStr(postReport.getCreateTime()));
            postRepRes.setReportStatus(postReport.getPostRepostStatus());
            postRepRes.setPostStatus(postReport.getPost().getPostStatus());

            postRepResList.add(postRepRes);
        }

        PageRes pageRes = new PageRes();
        pageRes.setResList(postRepResList);
        pageRes.setCurrentPageNumber(postReportPage.getNumber());
        pageRes.setPageSize(postReportPage.getSize());
        pageRes.setTotalPage(postReportPage.getTotalPages());
        pageRes.setCurrentPageDataSize(postReportPage.getNumberOfElements());

        ResultResponse<PageRes<PostRepRes>> response = new ResultResponse<>();
        response.setMessage(pageRes);
        return response;
    }

    //================================  查詢留言檢舉細節   ================================//

    @Override
    public ResultResponse<MesReportDetails> getMesReportById(int mesRepId) {
        ResultResponse<MesReportDetails> response = new ResultResponse<>();
        MesReport mesReport = mesRepDao.findById(mesRepId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "找不到此留言檢舉"));

        //貼文內容
        String messageContent = mesReport.getMessage().getMessageContent();
        //檢舉內容
        String messageReportContent = mesReport.getMessageReportContent();
        //檢舉人
        String fromUserNickName = mesReport.getUser().getUserName();
        String fromUserEmail = mesReport.getUser().getUserEmail();
        //被檢舉人
        String toUserEmail = mesReport.getMessage().getUser().getUserEmail();
        String toUserNickName = mesReport.getMessage().getUser().getUserName();
        //貼文檢舉狀態
        Integer messageReportStatus = mesReport.getMessageReportStatus();
        //檢舉時間
        String createTime = DateUtils.dateTimeSqlToStr(mesReport.getMessageCreateTime());
        //目前貼文狀態
        Integer messageStatus = mesReport.getMessage().getMessageStatus();

        MesReportDetails mesReportDetails = new MesReportDetails();
        mesReportDetails.setMesReportId(mesRepId);
        mesReportDetails.setMesContent(messageContent);
        mesReportDetails.setMesReportContent(messageReportContent);
        mesReportDetails.setFromUserName(fromUserNickName);
        mesReportDetails.setFromUserEmail(fromUserEmail);
        mesReportDetails.setToUserName(toUserNickName);
        mesReportDetails.setToUserEmail(toUserEmail);
        mesReportDetails.setMesRepStatus(messageReportStatus);
        mesReportDetails.setCreateTime(createTime);
        mesReportDetails.setMesStatus(messageStatus);
        response.setMessage(mesReportDetails);
        return response;

    }

    //================================  查詢貼文檢舉細節   ================================//
    @Override
    public ResultResponse<PostReportDetails> getPostReportById(int postRepId) {
        ResultResponse<PostReportDetails> response = new ResultResponse<>();
        PostReportDetails postReportDetails = new PostReportDetails();
        PostReport report = postRepDao.findById(postRepId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "找不到此貼文檢舉"));
        //貼文內容
        String postContent = report.getPost().getPostContent();
        //檢舉內容
        String postRepContent = report.getPostRepContent();
        //檢舉人
        String fromUserNickName = report.getUser().getUserNickName();
        String fromUserEmail = report.getUser().getUserEmail();
        //被檢舉人
        String toUserEmail = report.getPost().getUser().getUserEmail();
        String toUserNickName = report.getPost().getUser().getUserNickName();
        //貼文檢舉狀態
        Integer postRepostStatus = report.getPostRepostStatus();
        //檢舉時間
        String createTime = DateUtils.dateTimeSqlToStr(report.getCreateTime());
        //目前貼文狀態
        Integer postStatus = report.getPost().getPostStatus();
        postReportDetails.setPostReportId(postRepId);
        postReportDetails.setFromUserName(fromUserNickName);
        postReportDetails.setFromUserEmail(fromUserEmail);
        postReportDetails.setToUserName(toUserNickName);
        postReportDetails.setToUserEmail(toUserEmail);
        postReportDetails.setCreateTime(createTime);
        postReportDetails.setPostStatus(postStatus);
        postReportDetails.setPostRepStatus(postRepostStatus);
        postReportDetails.setPostReportContent(postRepContent);
        postReportDetails.setPostContent(postContent);

        response.setMessage(postReportDetails);
        return response;
    }

    //================================  貼文res轉換   ================================//


}
