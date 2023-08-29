package project_pet_backEnd.socialMedia.postCollection.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.socialMedia.postCollection.dto.req.PostTagReq;
import project_pet_backEnd.socialMedia.postCollection.dto.req.CategoryReq;
import project_pet_backEnd.socialMedia.postCollection.dto.res.ColCategoryRes;
import project_pet_backEnd.socialMedia.postCollection.dto.res.PostColRes;
import project_pet_backEnd.socialMedia.postCollection.dto.res.TagRes;
import project_pet_backEnd.socialMedia.postCollection.service.CategoryService;
import project_pet_backEnd.socialMedia.postCollection.service.PostCollService;
import project_pet_backEnd.socialMedia.postCollection.vo.PostCol;
import project_pet_backEnd.socialMedia.util.PageRes;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "使用者貼文收藏功能")
@RestController
@RequestMapping("user/social/collection")
@Validated
public class PostCollectionController {

    @Autowired
    private PostCollService postCollService;

    @Autowired
    private CategoryService categoryService;


    @ApiOperation("User收藏貼文")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping
    public ResponseEntity<ResultResponse<String>> create(@RequestParam("postId") int postId, @RequestParam("userId") Integer userId) {
        ResultResponse<String> response = postCollService.create(postId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @ApiOperation("User刪除收藏貼文")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @DeleteMapping("/{pcId}")
    public ResponseEntity<ResultResponse<String>> deletePostColl(@PathVariable("pcId") int pcId, @RequestParam("userId") Integer userId) {
        ResultResponse<String> response = postCollService.delete(pcId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @ApiOperation("User查詢收藏貼文清單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/posts")
    public ResponseEntity<ResultResponse<PageRes<PostColRes>>> getPosts(@RequestParam("userId") Integer userId) {
        ResultResponse<PageRes<PostColRes>> response = postCollService.getPostCol(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @ApiOperation("User建立貼文收藏分類")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/tags")
    public ResponseEntity<ResultResponse<String>> createTagsByColId(@Valid @RequestBody CategoryReq categoryReq, @RequestParam("userId") Integer userId) {
        ResultResponse<String> response = categoryService.createCategory(userId, categoryReq);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation("User刪除貼文收藏分類")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @DeleteMapping("/tags/{pctId}")
    public ResponseEntity<ResultResponse<String>> deleteTagsByColId(@PathVariable("pctId") int pctId, @RequestParam("userId") Integer userId) {
        ResultResponse<String> response = categoryService.deleteCategory(userId, pctId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation("User查看所有分類")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/tags")
    public ResponseEntity<ResultResponse<List<TagRes>>> queryAllTags(@RequestParam("userId") Integer userId) {
        ResultResponse<List<TagRes>> response = categoryService.queryAllTags(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @ApiOperation("User使用標籤查詢所有收藏內容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/tags/{pctId}")
    public ResponseEntity<ResultResponse<List<PostColRes>>> queryColByTagId(@PathVariable("pctId") int pctId, @RequestParam("userId") Integer userId) {
        ResultResponse<List<PostColRes>> response = categoryService.queryPostCollByTagId(userId, pctId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation("User修改收藏分類")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/tags/{pctId}")
    public ResponseEntity<ResultResponse<ColCategoryRes>> updateTagsByTagId(@RequestBody CategoryReq categoryReq, @PathVariable("pctId") int pctId, @RequestParam("userId") Integer userId) {
        ResultResponse<ColCategoryRes> response = categoryService.updateCategory(userId, pctId, categoryReq);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @ApiOperation("User為收藏加入分類主題")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/{pcId}/tags")
    public ResponseEntity<ResultResponse<?>> addTagByPostId(@PathVariable("pcId") int pcId, @RequestBody PostTagReq postTagReq, @RequestParam("userId") Integer userId) {
        ResultResponse<String> response = categoryService.createPostTagCol(pcId, userId, postTagReq);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation("User為收藏刪除分類")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @DeleteMapping("/{pcId}/tags")
    public ResponseEntity<ResultResponse<?>> deleteTagByPostId(@PathVariable("pcId") int pcId, @RequestBody PostTagReq postTagReq, @RequestParam("userId") Integer userId) {
        ResultResponse<String> response = categoryService.removePostTagCol(pcId, userId, postTagReq);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
