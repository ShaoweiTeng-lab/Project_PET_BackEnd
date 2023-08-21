package project_pet_backEnd.socialMedia.report.dto.res;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PostReportDetails {
    Integer postReportId;
    // 檢舉人
    String fromUserName;
    String fromUserEmail;
    //被檢舉人
    String toUserName;
    String toUserEmail;
    //檢舉內容
    String postReportContent;
    //檢舉時間
    Timestamp createTime;
    //檢舉狀態
    Integer postStatus;
}
