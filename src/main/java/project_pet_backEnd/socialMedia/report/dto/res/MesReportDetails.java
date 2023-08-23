package project_pet_backEnd.socialMedia.report.dto.res;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MesReportDetails {
    Integer mesReportId;
    // 檢舉人
    String fromUserName;
    String fromUserEmail;
    //被檢舉人
    String toUserName;
    String toUserEmail;
    //檢舉內容
    String mesReportContent;
    //留言內容
    String mesContent;
    //檢舉時間
    String createTime;
    //檢舉狀態
    Integer mesRepStatus;
    //留言狀態
    Integer mesStatus;
}
