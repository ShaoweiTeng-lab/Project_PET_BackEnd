package project_pet_backEnd.socialMedia.post.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.socialMedia.post.dto.req.PostReq;
import project_pet_backEnd.socialMedia.post.service.PostService;
import project_pet_backEnd.socialMedia.post.vo.POST;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("user/social/post")
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * user發布貼文
     */
    @PostMapping
    public POST create(@RequestBody PostReq postReq) {
        POST post = new POST();
        //get data from req
        post.setUserId(postReq.getUserId());
        post.setPostContent(postReq.getContent());

        //default setting

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        post.setPostStatus(0);
        post.setCreateTime(timestamp);
        post.setUpdateTime(null);

        POST newPost = postService.create(post);
        return newPost;
    }


    /**
     * user修改貼文內容
     */

    @PutMapping
    public POST update(@RequestBody POST post) {


        return post;
    }

    /**
     * user 檢舉貼文
     */


    /**
     * 獲取所有貼文
     */
    @GetMapping
    public List<POST> getAllPost() {
        List<POST> allPosts = postService.getAllPosts();
        return allPosts;
    }


}
