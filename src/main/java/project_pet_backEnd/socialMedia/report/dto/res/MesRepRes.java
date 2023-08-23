package project_pet_backEnd.socialMedia.report.dto.res;

import lombok.Data;

@Data
public class MesRepRes {
    //留言檢舉清單
    Integer mesRepId;
    String mesRepContent;
    String mesContent;
    String reportTime;
    Integer mesStatus;
    /**
     * 0 上架 1 下架
     */
    Integer reportStatus;
    /**
     * 0: 審核中
     * 1: 審核未過
     * 2: 審核通過
     */
}