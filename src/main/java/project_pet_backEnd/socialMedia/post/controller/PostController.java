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
import org.springframework.web.multipart.MultipartFile;
import project_pet_backEnd.socialMedia.post.dto.req.DeleteTagReq;
import project_pet_backEnd.socialMedia.post.dto.req.PostTagReq;
import project_pet_backEnd.socialMedia.post.dto.res.PostRes;
import project_pet_backEnd.socialMedia.post.dto.req.PostReq;
import project_pet_backEnd.socialMedia.post.dto.res.VideoRes;
import project_pet_backEnd.socialMedia.post.service.PostService;
import project_pet_backEnd.socialMedia.post.vo.MediaData;
import project_pet_backEnd.socialMedia.post.vo.POST;
import project_pet_backEnd.socialMedia.util.PageRes;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;

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
    public ResponseEntity<ResultResponse<PostRes>> create(@Valid @RequestBody PostReq postReq, @RequestAttribute("userId") Integer userId) {
        ResultResponse<PostRes> response = postService.create(userId, postReq);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @ApiOperation("User修改貼文內容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/{postId}")
    public ResponseEntity<ResultResponse<PostRes>> update(@PathVariable("postId") int postId, @RequestBody PostReq postReq, @RequestAttribute("userId") Integer userId) {
        ResultResponse<PostRes> response = postService.update(userId, postId, postReq);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation("User刪除單一貼文")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @DeleteMapping("/{postId}")
    public ResponseEntity<ResultResponse<String>> delete(@PathVariable("postId") int postId, @RequestAttribute("userId") Integer userId) {
        System.out.println(postId);
        ResultResponse<String> response = postService.delete(userId, postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @ApiOperation("User查看所有貼文")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/all")
    public ResponseEntity<ResultResponse<PageRes<PostRes>>> getAllPost(@RequestParam(value = "page", required = false, defaultValue = "0") int page) {
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

    //這邊要加userID

    @ApiOperation("User上傳檔案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/{postId}/upload")
    public ResponseEntity<ResultResponse<String>> uploadFiles(@RequestParam("file") MultipartFile[] files, @PathVariable("postId") int postId) {
        ResultResponse<String> response = postService.uploadFiles(files, postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation("User查看貼文檔案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/{postId}/files")
    public ResponseEntity<ResultResponse<List<MediaData>>> getMediaDataByPostId(@PathVariable("postId") int postId) {
        ResultResponse<List<MediaData>> response = postService.getMediaDataByPostId(postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation("User查看單一圖片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/{postMediaId}/Image")
    public ResponseEntity<ResultResponse<byte[]>> getImageByPostId(@PathVariable("postMediaId") int postMediaId) {
        ResultResponse<byte[]> response = postService.getImageDataById(postMediaId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String VIDEO_CONTENT = "video/";
    public static final String CONTENT_RANGE = "Content-Range";
    public static final String ACCEPT_RANGES = "Accept-Ranges";
    public static final String BYTES = "bytes";

    @ApiOperation("User查看影片串流")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/{postMediaId}/video")
    public ResponseEntity<byte[]> getVideoStreamByPostId(@PathVariable("postMediaId") int postMediaId, @RequestHeader(value = "Range", required = false) String range) throws IOException {
        VideoRes videoRes = postService.getVideoStreamById(postMediaId, range);
        return ResponseEntity.status(videoRes.getHttpStatus())
                .header(CONTENT_TYPE, VIDEO_CONTENT + videoRes.getFileType())
                .header(ACCEPT_RANGES, BYTES)
                .header(CONTENT_LENGTH, videoRes.getContentLength())
                .header(CONTENT_RANGE, BYTES + videoRes.getContentRange())
                .body(videoRes.getData());

    }


    @ApiOperation("User建立貼文標籤-redis 儲存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/{postId}/tags")
    public ResponseEntity<ResultResponse<String>> createTagsByPostId(@PathVariable("postId") int postId, @RequestBody PostTagReq postTagReq) {
        ResultResponse<String> response = postService.createPostTag(postId, postTagReq);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation("User刪除貼文標籤-redis儲存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @DeleteMapping("/{postId}/tags")
    public ResponseEntity<ResultResponse<?>> deleteTagsByPostId(@PathVariable("postId") int postId, @RequestBody DeleteTagReq deleteTagReq) {
        ResultResponse<String> response = postService.deletePostTag(postId, deleteTagReq);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @ApiOperation("User查詢貼文所有標籤-redis儲存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/{postId}/tags")
    public ResponseEntity<ResultResponse<Set<String>>> queryTagsByPostId(@PathVariable("postId") int postId) {
        ResultResponse<Set<String>> response = postService.queryAllTagsByPostId(postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @ApiOperation("User查詢標籤所有貼文-redis儲存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/posts")
    public ResponseEntity<ResultResponse<List<PostRes>>> queryPostsByTagName(@RequestParam("tagName") String tagName) {
        ResultResponse<List<PostRes>> response = postService.queryAllPostByTag(tagName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
