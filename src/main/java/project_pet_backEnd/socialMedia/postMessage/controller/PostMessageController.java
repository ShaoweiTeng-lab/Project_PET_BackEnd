package project_pet_backEnd.socialMedia.postMessage.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.socialMedia.postMessage.dto.req.MesReq;
import project_pet_backEnd.socialMedia.postMessage.dto.res.MesRes;
import project_pet_backEnd.socialMedia.postMessage.service.MesService;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "貼文留言功能")
@RestController
@RequestMapping("/user/social/post")
@Validated
public class PostMessageController {

    @Autowired
    private MesService mesService;

    /**
     * 使用者 @RequestAttribute("userId") Integer userId
     */
    @ApiOperation("User發布貼文留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/{postId}/message")
    public ResponseEntity<ResultResponse<String>> create(@PathVariable("postId") int postId, @Valid @RequestBody MesReq mesReq, @RequestAttribute("userId") Integer userId) {
        ResultResponse<String> response = mesService.create(userId, postId, mesReq);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation("User修改貼文留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/message/{messageId}")
    public ResponseEntity<ResultResponse<MesRes>> updateMessageById(@PathVariable("messageId") int mesId, @Valid @RequestBody MesReq mesReq, @RequestAttribute("userId") Integer userId) {
        ResultResponse<MesRes> updateResult = mesService.update(userId, mesId, mesReq);
        return ResponseEntity.status(HttpStatus.OK).body(updateResult);

    }


    @ApiOperation("User刪除貼文留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @DeleteMapping("/message/{messageId}")
    public ResponseEntity<ResultResponse<String>> deleteMessageById(@PathVariable("messageId") int mesId, @RequestAttribute("userId") Integer userId) {
        ResultResponse<String> deleteResult = mesService.delete(userId, mesId);
        return ResponseEntity.status(HttpStatus.OK).body(deleteResult);
    }

    @ApiOperation("User查看單一留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/message/{messageId}")
    public ResponseEntity<ResultResponse<MesRes>> getMessageById(@PathVariable("messageId") int messageId) {
        ResultResponse<MesRes> message = mesService.getMesById(messageId);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }


    @ApiOperation("User查詢單一貼文所有留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/{postId}/messages")
    public ResponseEntity<ResultResponse<List<MesRes>>> getMessagesByPostId(@PathVariable("postId") int postId) {
        ResultResponse<List<MesRes>> messages = mesService.findMessageByPostId(postId);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }


}
