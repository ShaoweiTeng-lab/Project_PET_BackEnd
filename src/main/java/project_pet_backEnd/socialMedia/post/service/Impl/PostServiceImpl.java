package project_pet_backEnd.socialMedia.post.service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.socialMedia.post.dao.PostDao;
import project_pet_backEnd.socialMedia.post.dto.req.UpPostReq;
import project_pet_backEnd.socialMedia.post.service.PostService;
import project_pet_backEnd.socialMedia.post.vo.POST;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Override
    public POST create(POST post) {
        //already check user list [1,2,3]
        List<Integer> userList = new ArrayList<>();
        userList.add(1);
        userList.add(2);
        userList.add(3);
        //check user existed
        boolean checkUser = false;
        for (Integer user : userList) {
            System.out.println(user);
            if (post.getUserId() == user) {
                checkUser = true;
            }
        }
        if (!checkUser) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "無此使用者");

        }
        postDao.create(post);
        return post;
    }

    @Override
    public POST update(int postId, UpPostReq upPostReq) {
        //find entity by id
        POST updatePostResult = postDao.update(postId, upPostReq);
        return updatePostResult;
    }

    @Override
    public void delete(int postId) {
        postDao.delete(postId);

    }


    @Override
    public List<POST> getAllPosts() {
        List<POST> allPosts = postDao.getAllPosts();
        return allPosts;
    }

    @Override
    public POST getPostById(int postId) {
        POST post = postDao.getPostById(postId);
        return post;
    }
}
