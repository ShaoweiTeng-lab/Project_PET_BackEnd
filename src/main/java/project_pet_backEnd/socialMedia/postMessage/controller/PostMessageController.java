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
import project_pet_backEnd.socialMedia.postMessage.dto.req.MessageRequest;
import project_pet_backEnd.socialMedia.postMessage.dto.req.UpMesReq;
import project_pet_backEnd.socialMedia.postMessage.service.MesService;
import project_pet_backEnd.socialMedia.postMessage.vo.Message;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "貼文留言功能")
@RestController
@RequestMapping("/user/social")
@Validated
public class PostMessageController {

    @Autowired
    private MesService mesService;

    /**
     * 使用者 @RequestAttribute("userId") Integer userId
     */
    @ApiOperation("User發布留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/message")
    public ResponseEntity<ResultResponse<Message>> create(@Valid @RequestBody MessageRequest messageRequest, @RequestAttribute("userId") Integer userId) {
        ResultResponse successMes = mesService.create(userId, messageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(successMes);
    }

    @ApiOperation("User修改留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/message/{messageId}")
    public ResponseEntity<ResultResponse<Message>> updateMessageById(@PathVariable("messageId") int mesId, @Valid @RequestBody UpMesReq upMesReq, @RequestAttribute("userId") Integer userId) {
        ResultResponse updateResult = mesService.update(userId, mesId, upMesReq);
        return ResponseEntity.status(HttpStatus.OK).body(updateResult);

    }


    @ApiOperation("User刪除留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @DeleteMapping("/message/{messageId}")
    public ResponseEntity<ResultResponse<String>> deleteMessageById(@PathVariable("messageId") int mesId, @RequestAttribute("userId") Integer userId) {
        ResultResponse<String> deleteResult = mesService.delete(userId, mesId);
        return ResponseEntity.status(HttpStatus.OK).body(deleteResult);
    }


    @ApiOperation("User查詢單一貼文所有留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/message/post/{postId}")
    public ResponseEntity<ResultResponse<List<Message>>> getMessagesByPostId(@PathVariable("postId") int postId) {
        ResultResponse<List<Message>> messages = mesService.findMessageByPostId(postId);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

    @ApiOperation("User查看單一留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/message/{messageId}")
    public ResponseEntity<ResultResponse<Message>> getMessageById(@PathVariable("messageId") int messageId) {
        ResultResponse<Message> message = mesService.getMesById(messageId);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
