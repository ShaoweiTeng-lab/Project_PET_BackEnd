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
    //貼文內容
    String postContent;
    //檢舉內容
    String postReportContent;
    //檢舉時間
    String createTime;
    //檢舉狀態
    Integer postRepStatus;
    //貼文狀態
    Integer postStatus;
}