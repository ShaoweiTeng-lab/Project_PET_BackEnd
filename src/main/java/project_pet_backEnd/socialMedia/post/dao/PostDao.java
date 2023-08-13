package project_pet_backEnd.socialMedia.post.dao;



import project_pet_backEnd.socialMedia.post.vo.POST;

import java.util.List;

public interface PostDao {

    /**
     * user發布貼文
     */

    public POST create(POST post);


    /**
     * 獲取所有貼文
     */

    public List<POST> getAllPosts();



    /**
     * user修改貼文內容
     */

    public POST update(POST post);

    /**
     * user刪除貼文
     */

    public boolean delete(int postId);

    /**
     * user檢舉貼文
     */

    public boolean reportPost(int postId);




}
