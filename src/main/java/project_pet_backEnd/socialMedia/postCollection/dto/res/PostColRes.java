package project_pet_backEnd.socialMedia.postCollection.dto.res;

import lombok.Data;

@Data
public class PostColRes {
    byte[] postPic;
    Integer postPicNumber;
    Integer pcId;
    //貼文更新時間
    String createTime;
    //貼文作者
    String userName;
    //貼文內容
    String postContent;

}
