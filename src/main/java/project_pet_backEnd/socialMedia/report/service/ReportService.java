package project_pet_backEnd.socialMedia.report.service;



import project_pet_backEnd.socialMedia.report.dto.req.MessageReportRequest;
import project_pet_backEnd.socialMedia.report.dto.req.PostReportRequest;
import project_pet_backEnd.socialMedia.report.vo.MesReport;
import project_pet_backEnd.socialMedia.report.vo.PostReport;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

public interface ReportService {


    // ================= message report ================= //
    ResultResponse<MesReport> create(int userId, MessageReportRequest messageReportRequest);


    // ================= post report ================= //
    ResultResponse<PostReport> create(int userId, PostReportRequest postReportRequest);


    // ================= manager report  ================= //
    ResultResponse<List<MesReport>> getAllMesReport(Integer managerId);

    ResultResponse<List<PostReport>> getAllPostReport(Integer managerId);

    ResultResponse<MesReport> getMesReportById(int mesRepId, Integer managerId);

    ResultResponse<PostReport> getPostReportById(int postRepId, Integer managerId);

    ResultResponse<MesReport> reviewReportById(int mesRepId, Integer managerId);

    ResultResponse<String> sendEmailToUsers(String[] toEmail, String body, String subject);


}
