package project_pet_backEnd.socialMedia.postCollection.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.socialMedia.postCollection.vo.PostCol;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

@Api(tags = "使用者貼文收藏功能")
@RestController
@RequestMapping("user/social/collection")
@Validated
public class PostCollectionController {


    @ApiOperation("User收藏貼文")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/{postId}")
    public ResponseEntity<ResponseEntity<PostCol>> create(@PathVariable("postId") int postId) {

        return null;
    }


    @ApiOperation("User刪除收藏貼文")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @DeleteMapping
    public ResponseEntity<ResponseEntity<String>> deletePostColl() {

        return null;
    }


    @ApiOperation("User查詢收藏貼文")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping
    public ResponseEntity<ResultResponse<List<PostCol>>> getPosts() {


        return null;
    }


}
