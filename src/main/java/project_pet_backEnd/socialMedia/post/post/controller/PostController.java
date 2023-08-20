package project_pet_backEnd.socialMedia.post.post.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.socialMedia.post.post.dto.req.PostReq;
import project_pet_backEnd.socialMedia.post.post.dto.req.UpPostRequest;
import project_pet_backEnd.socialMedia.post.post.dto.res.PostRes;
import project_pet_backEnd.socialMedia.post.post.service.PostService;
import project_pet_backEnd.socialMedia.post.post.vo.POST;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

@Api(tags = "貼文功能")
@RestController
@RequestMapping("user/social/post")
@Validated
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * 使用屬性 @RequestAttribute("userId") Integer userId
     */
    @ApiOperation("User發布貼文")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping
    public ResponseEntity<ResultResponse<PostRes>> create(@RequestBody PostReq postReq) {
        POST post = new POST();
        //set info
        post.setUserId(postReq.getUserId());
        post.setPostContent(postReq.getContent());
        post.setPostStatus(0);


        ResultResponse<POST> response = postService.create(post);

        //改變回傳結果
        PostRes postRes = new PostRes();
        postRes.setPostStatus(response.getMessage().getPostStatus());
        postRes.setPostContent(response.getMessage().getPostContent());
        postRes.setUpdateTime(response.getMessage().getCreateTime());
        postRes.setUserId(response.getMessage().getUserId());
        postRes.setPostId(response.getMessage().getPostId());

        ResultResponse<PostRes> resultResponse = new ResultResponse<>();
        resultResponse.setMessage(postRes);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }


    @ApiOperation("User修改貼文內容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/{postId}")
    public ResponseEntity<PostRes> update(@PathVariable("postId") int postId, @RequestBody UpPostRequest upPostRequest) {
        ResultResponse<POST> resultResponse = postService.update(postId, upPostRequest);
        postService.update(postId, upPostRequest);
//        PostRes postRes = new PostRes();
//        postRes.setPostId(updatePost.getPostId());
//        postRes.setUserId(updatePost.getUserId());
//        postRes.setPostContent(updatePost.getPostContent());
//        postRes.setUpdateTime(updatePost.getUpdateTime());
//        postRes.setPostStatus(updatePost.getPostStatus());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @ApiOperation("User刪除單一貼文")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> delete(@PathVariable("postId") int postId) {
        // 關聯問題 目前無法刪除
        System.out.println(postId);
        postService.delete(postId);
        return ResponseEntity.status(HttpStatus.OK).body("刪除成功");

    }

    @ApiOperation("User查看所有貼文")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping
    public ResponseEntity<ResultResponse<List<POST>>> getAllPost() {
        ResultResponse<List<POST>> posts = postService.getAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @ApiOperation("User查看單一貼文")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/{postId}")
    public ResponseEntity<ResultResponse<PostRes>> getPostById(@PathVariable("postId") int postId) {
        ResultResponse<POST> post = postService.getPostById(postId);
        PostRes postRes = new PostRes();
//        postRes.setUserId(post.getUserId());
//        postRes.setPostId(post.getPostId());
//        postRes.setPostContent(post.getPostContent());
//        postRes.setPostStatus(post.getPostStatus());
//        postRes.setUpdateTime(post.getUpdateTime());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


    //圖片可以多張
    @ApiOperation("User上傳圖片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/{postId}/uploadImage")
    public ResponseEntity<?> uploadImage() {


        return null;

    }

    /*
     * 一個貼文只能上傳個影片，若有重複就覆蓋
     */
    @ApiOperation("User上傳影片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/{postId}/uploadVedio")
    public ResponseEntity<?> uploadVedio() {


        return null;

    }

    @ApiOperation("User查看貼文影片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/{postId}/vedio")
    public ResponseEntity<ResultResponse<?>> getVedioByPostId(@PathVariable("postId") int postId) {

        return null;
    }


    /**
     * 透過postId 拿到此貼文圖片(分成base64編碼 or 檔案格式)
     */
    @ApiOperation("User查看貼文圖片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/{postId}/image")
    public ResponseEntity<ResultResponse<?>> getImageByPostId(@PathVariable("postId") int postId) {

        return null;
    }


}
