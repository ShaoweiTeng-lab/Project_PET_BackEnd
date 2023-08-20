package project_pet_backEnd.socialMedia.post.post.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.socialMedia.post.post.dao.PostDao;
import project_pet_backEnd.socialMedia.post.post.dto.req.UpPostRequest;
import project_pet_backEnd.socialMedia.post.post.vo.POST;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    /**
     * user發布貼文
     */
    @Override
    public ResultResponse<POST> create(POST post) {
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
        POST newPost = postDao.create(post);
        ResultResponse<POST> resultResponse = new ResultResponse<>();
        resultResponse.setMessage(newPost);
        return resultResponse;
    }

    /**
     * user修改貼文內容
     */
    @Override
    public ResultResponse<POST> update(int postId, UpPostRequest upPostRequest) {
        //find entity by id
        POST updatePostResult = postDao.update(postId, upPostRequest);

        ResultResponse<POST> resultResponse = new ResultResponse<>();
        resultResponse.setMessage(updatePostResult);

        return resultResponse;
    }

    /**
     * user刪除貼文
     */
    @Override
    public ResultResponse<String> delete(int postId) {
        //晚點修改
        postDao.delete(postId);
        ResultResponse<String> resultResponse = new ResultResponse<>();
        resultResponse.setMessage("刪除成功");
        return resultResponse;
    }

    /**
     * 獲取所有貼文
     */


    @Override
    public ResultResponse<List<POST>> getAllPosts() {
        List<POST> posts = postDao.getAllPosts();
        ResultResponse<List<POST>> resultResponse = new ResultResponse<>();
        resultResponse.setMessage(posts);
        return resultResponse;
    }

    /**
     * 獲取單一貼文
     */

    @Override
    public ResultResponse<POST> getPostById(int postId) {
        POST post = postDao.getPostById(postId);
        ResultResponse<POST> resultResponse = new ResultResponse<>();
        resultResponse.setMessage(post);
        return resultResponse;
    }
}
