package project_pet_backEnd.socialMedia.post.dao;

import project_pet_backEnd.socialMedia.post.dto.req.DeleteTagReq;
import project_pet_backEnd.socialMedia.post.dto.req.PostTagReq;
import project_pet_backEnd.socialMedia.post.vo.POST;

import java.util.List;
import java.util.Set;

public interface PostTagDao {
    //建立貼文標籤
    void createPostTag(int postId, PostTagReq postTagReq);

    //刪除貼文標籤
    void deletePostTag(int postId, DeleteTagReq deleteTagReq);

    //查詢貼文標籤
    Set<String> queryAllTagsByPostId(int postId);

    //用標籤列出相關貼文(透過redis快取增加速度)
    List<POST> queryAllPostByTag(String tag);
}

