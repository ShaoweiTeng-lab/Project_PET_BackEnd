package project_pet_backEnd.socialMedia.report.dao;




import project_pet_backEnd.socialMedia.report.dto.req.ReviewReq;
import project_pet_backEnd.socialMedia.report.dto.res.MesReportDetails;
import project_pet_backEnd.socialMedia.report.vo.MesReport;
import project_pet_backEnd.socialMedia.report.vo.PostReport;

import java.util.List;

public interface ReportDao {

    //============= manager =============//

    String reviewMesReport(int mesRepId, ReviewReq reviewReq);

    PostReport reviewPostReport(PostReport postReport);

    MesReport checkMesExist(int messageId);

    PostReport checkPostExist(int postId);


    //============= user =============//

    MesReport createMesReport(MesReport messageReport);

    PostReport createPostReport(PostReport postReport);

}
