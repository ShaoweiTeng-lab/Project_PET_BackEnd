package project_pet_backEnd.socialMedia.report.dao;




import project_pet_backEnd.socialMedia.report.dto.res.MesReportDetails;
import project_pet_backEnd.socialMedia.report.vo.MesReport;
import project_pet_backEnd.socialMedia.report.vo.PostReport;

import java.util.List;

public interface ReportDao {

    /*
     * manager - message report
     *
     */

    List<MesReport> getAllMesReport();

    MesReport reviewMesReport(MesReport messageReport);

    MesReportDetails findMesReportById(int mesReportId);


    /*
     * manager - post report
     */
    List<PostReport> getAllPostReport();

    //review report status - tell reporter result
    PostReport reviewPostReport(PostReport postReport);


    /*
     * user
     */

    MesReport createMesReport(MesReport messageReport);

    PostReport createPostReport(PostReport postReport);

}
