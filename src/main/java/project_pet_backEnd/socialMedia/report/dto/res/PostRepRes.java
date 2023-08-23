package project_pet_backEnd.socialMedia.report.dto.res;

import lombok.Data;

@Data
public class PostRepRes {
    //檢舉清單
    Integer postRepId;
    String postRepContent;
    String postContent;
    String reportTime;
    Integer postStatus;
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

