package project_pet_backEnd.socialMedia.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.socialMedia.post.dto.req.PostReq;
import project_pet_backEnd.socialMedia.post.dto.req.UpPostReq;
import project_pet_backEnd.socialMedia.post.dto.res.PostRes;
import project_pet_backEnd.socialMedia.post.service.PostService;
import project_pet_backEnd.socialMedia.post.vo.POST;

import java.util.List;

@RestController
@RequestMapping("user/social/post")
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * user發布貼文 @RequestAttribute("userId") Integer userId
     */
    @PostMapping
    public PostRes create(@RequestBody PostReq postReq) {
        POST post = new POST();
        //set info
        post.setUserId(postReq.getUserId());
        post.setPostContent(postReq.getContent());
        post.setPostStatus(0);

        POST newPost = postService.create(post);

        PostRes postRes = new PostRes();
        postRes.setPostId(newPost.getPostId());
        postRes.setUserId(newPost.getUserId());
        postRes.setPostContent(newPost.getPostContent());
        postRes.setUpdateTime(newPost.getUpdateTime());
        postRes.setPostStatus(newPost.getPostStatus());
        return postRes;
    }


    /**
     * user修改貼文內容 @RequestAttribute("userId") Integer userId
     */

    @PutMapping("/{postId}")
    public PostRes update(@PathVariable("postId") int postId, @RequestBody UpPostReq upPostReq) {
        POST updatePost = postService.update(postId, upPostReq);
        PostRes postRes = new PostRes();
        postRes.setPostId(updatePost.getPostId());
        postRes.setUserId(updatePost.getUserId());
        postRes.setPostContent(updatePost.getPostContent());
        postRes.setUpdateTime(updatePost.getUpdateTime());
        postRes.setPostStatus(updatePost.getPostStatus());
        return postRes;
    }

    /**
     * 刪除貼文 @RequestAttribute("userId") Integer userId
     */
    @DeleteMapping("/{postId}")
    public void delete(@PathVariable("postId") int postId) {
        // 關聯問題 目前無法刪除
        System.out.println(postId);
        postService.delete(postId);

    }


    /**
     * 獲取所有貼文
     */
    @GetMapping
    public List<POST> getAllPost() {
        List<POST> allPosts = postService.getAllPosts();
        return allPosts;
    }


    /**
     * 根據Id獲取單一貼文
     */
    @GetMapping("/{postId}")
    public PostRes getPostById(@PathVariable("postId") int postId) {
        POST post = postService.getPostById(postId);
        PostRes postRes = new PostRes();
        postRes.setUserId(post.getUserId());
        postRes.setPostId(post.getPostId());
        postRes.setPostContent(post.getPostContent());
        postRes.setPostStatus(post.getPostStatus());
        postRes.setUpdateTime(post.getUpdateTime());
        return postRes;
    }


}
