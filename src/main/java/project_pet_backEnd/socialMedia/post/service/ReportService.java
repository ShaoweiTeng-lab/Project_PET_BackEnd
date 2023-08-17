package project_pet_backEnd.socialMedia.post.service;

import project_pet_backEnd.socialMedia.post.dto.req.MesRepReq;
import project_pet_backEnd.socialMedia.post.dto.req.PostRepReq;
import project_pet_backEnd.socialMedia.post.vo.MesReport;
import project_pet_backEnd.socialMedia.post.vo.PostReport;

import java.util.List;

public interface ReportService {


    // ================= message report ================= //
    MesReport create(int userId, MesRepReq mesRepReq);


    // ================= post report ================= //
    PostReport create(int userId, PostRepReq postRepReq);


    // ================= manager report  ================= //
    List<MesReport> getAllMesReport();

    List<PostReport> getAllPostReport();

    MesReport getMesReportById(int mesRepId);

    PostReport getPostReportById(int postRepId);
}