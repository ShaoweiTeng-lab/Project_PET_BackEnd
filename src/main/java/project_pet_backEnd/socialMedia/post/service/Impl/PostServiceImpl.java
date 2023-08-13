package project_pet_backEnd.socialMedia.post.service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_pet_backEnd.socialMedia.post.dao.PostDao;
import project_pet_backEnd.socialMedia.post.service.PostService;
import project_pet_backEnd.socialMedia.post.vo.POST;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Override
    public POST create(POST post) {
        postDao.create(post);
        return post;
    }

    @Override
    public POST update(POST post) {
        postDao.update(post);
        return post;
    }

    @Override
    public boolean delete(int postId) {
        boolean delete = postDao.delete(postId);
        return delete;
    }



    @Override
    public List<POST> getAllPosts() {
        List<POST> allPosts = postDao.getAllPosts();
        return allPosts;
    }
}
