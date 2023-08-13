package project_pet_backEnd.socialMedia.post.service;


import project_pet_backEnd.socialMedia.post.vo.POST;

import java.util.List;


public interface PostService {
    /**
     * user發布貼文
     */

    public POST create(POST post);


    /**
     * user修改貼文內容
     */

    public POST update(POST post);

    /**
     * user刪除貼文
     */

    public boolean delete(int postId);


    /**
     * 獲取所有貼文
     */

    public List<POST> getAllPosts();
}
