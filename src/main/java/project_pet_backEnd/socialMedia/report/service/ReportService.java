package project_pet_backEnd.socialMedia.report.service;



import project_pet_backEnd.socialMedia.report.dto.req.MessageReportRequest;
import project_pet_backEnd.socialMedia.report.dto.req.PostReportRequest;
import project_pet_backEnd.socialMedia.report.dto.req.ReportRequest;
import project_pet_backEnd.socialMedia.report.dto.req.ReviewReq;
import project_pet_backEnd.socialMedia.report.dto.res.MesRepRes;
import project_pet_backEnd.socialMedia.report.dto.res.MesReportDetails;
import project_pet_backEnd.socialMedia.report.dto.res.PostRepRes;
import project_pet_backEnd.socialMedia.report.dto.res.PostReportDetails;
import project_pet_backEnd.socialMedia.report.vo.MesReport;
import project_pet_backEnd.socialMedia.report.vo.PostReport;
import project_pet_backEnd.socialMedia.util.PageRes;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

public interface ReportService {


    // =================== user report =================== //
    ResultResponse<String> createMesRep(int userId, int messageId, ReportRequest reportRequest);

    ResultResponse<String> createPostRep(int userId, int postId, ReportRequest reportRequest);


    // ================= manager report  ================= //

    ResultResponse<PageRes<MesRepRes>> getAllMesReport(Integer page, Integer status);

    ResultResponse<PageRes<PostRepRes>> getAllPostReport(Integer page, Integer status);

    ResultResponse<MesReportDetails> getMesReportById(int mesRepId);

    ResultResponse<PostReportDetails> getPostReportById(int postRepId);

    ResultResponse<MesReport> reviewMesReportById(int mesRepId, ReviewReq reviewReq);

    ResultResponse<PostReport> reviewPostReportById(int postRepId, ReviewReq reviewReq);


}