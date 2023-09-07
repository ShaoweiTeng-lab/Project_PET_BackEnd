package project_pet_backEnd.socialMedia.postCollection.dto.res;

import lombok.Data;

@Data
public class TagRes {
    //分類標籤Id
    Integer pctId;
    //使用者
    Integer userId;
    //標籤分類名稱
    String categoryName;
    //標籤分類描述
    String description;
    //更新時間
    String updateTime;
}
