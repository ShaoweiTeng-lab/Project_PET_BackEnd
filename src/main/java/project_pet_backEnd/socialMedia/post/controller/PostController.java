package project_pet_backEnd.socialMedia.post.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.socialMedia.post.dto.res.PostRes;
import project_pet_backEnd.socialMedia.post.dto.req.PostReq;
import project_pet_backEnd.socialMedia.post.service.PostService;
import project_pet_backEnd.socialMedia.util.PageRes;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.Valid;

@Api(tags = "貼文功能")
@RestController
@RequestMapping("/user/social/post")
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
    public ResponseEntity<ResultResponse<String>> create(@Valid @RequestBody PostReq postReq, @RequestParam("userId") Integer userId) {
        ResultResponse<String> response = postService.create(userId, postReq);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @ApiOperation("User修改貼文內容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/{postId}")
    public ResponseEntity<ResultResponse<PostRes>> update(@PathVariable("postId") int postId, @RequestBody PostReq postReq, @RequestParam("userId") Integer userId) {
        ResultResponse<PostRes> response = postService.update(userId, postId, postReq);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation("User刪除單一貼文")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> delete(@PathVariable("postId") int postId, @RequestParam("userId") Integer userId) {
        // 關聯問題 目前無法刪除
        System.out.println(postId);
        postService.delete(userId, postId);
        return ResponseEntity.status(HttpStatus.OK).body("刪除成功");

    }

    @ApiOperation("User查看所有貼文")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/all")
    public ResponseEntity<ResultResponse<PageRes<PostRes>>> getAllPost(@RequestParam("page") int page) {
        ResultResponse<PageRes<PostRes>> posts = postService.getAllPosts(page);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @ApiOperation("User查看單一貼文")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/{postId}")
    public ResponseEntity<ResultResponse<PostRes>> getPostById(@PathVariable("postId") int postId) {
        ResultResponse<PostRes> post = postService.getPostById(postId);
        return ResponseEntity.status(HttpStatus.OK).body(post);
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
    @PostMapping("/{postId}/uploadVideo")
    public ResponseEntity<?> uploadVideo() {


        return null;

    }

    @ApiOperation("User查看貼文影片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/{postId}/video")
    public ResponseEntity<ResultResponse<?>> getVideoByPostId(@PathVariable("postId") int postId) {

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


    @ApiOperation("User建立貼文標籤")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/{postId}/tags")
    public ResponseEntity<ResultResponse<?>> createTagsByPostId(@PathVariable("postId") int postId) {

        return null;
    }

    @ApiOperation("User刪除貼文標籤")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @DeleteMapping("/{postId}/tags")
    public ResponseEntity<ResultResponse<?>> deleteTagsByPostId(@PathVariable("postId") int postId) {

        return null;
    }

    @ApiOperation("User查詢貼文所有標籤")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/{postId}/tags")
    public ResponseEntity<ResultResponse<?>> queryTagsByPostId(@PathVariable("postId") int postId) {

        return null;
    }


}
