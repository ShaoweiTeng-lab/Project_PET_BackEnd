package project_pet_backEnd.socialMedia.post.dao;

import project_pet_backEnd.socialMedia.post.vo.MesReport;
import project_pet_backEnd.socialMedia.post.vo.PostReport;

import java.util.List;

public interface ReportDao {

    /*
     * manager - message report
     *
     */

    List<MesReport> getAllMesReport();

    MesReport reviewMesReport(MesReport messageReport);

    void notifyUserByEmail(int userId);



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

