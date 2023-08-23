package project_pet_backEnd.socialMedia.report.dto.req;

import lombok.Data;

@Data
public class ReviewReq {
    /**
     * post and message status
     * 0: 審核中
     * 1: 審核通過
     * 2: 審核未過
     */
    Integer reportId;
    // 檢舉人
    String fromUserName;
    String fromUserEmail;
    //被檢舉人
    String toUserName;
    String toUserEmail;
    //檢舉內容
    String reportContent;
    Integer status;
}