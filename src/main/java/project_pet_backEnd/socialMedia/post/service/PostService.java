package project_pet_backEnd.socialMedia.post.service;


import project_pet_backEnd.socialMedia.post.dto.req.UpPostReq;
import project_pet_backEnd.socialMedia.post.vo.POST;

import java.util.List;


public interface PostService {

    /**
     * user發布貼文
     */
    POST create(POST post);


    /**
     * user修改貼文內容
     */

    POST update(int postId, UpPostReq upPostReq);

    /**
     * user刪除貼文
     */

    void delete(int postId);


    /**
     * 獲取所有貼文
     */

    List<POST> getAllPosts();


    /**
     * 獲取單一貼文
     */

    POST getPostById(int postId);
}
