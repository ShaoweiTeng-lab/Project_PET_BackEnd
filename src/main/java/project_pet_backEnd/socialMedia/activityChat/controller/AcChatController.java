package project_pet_backEnd.socialMedia.activityChat.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.socialMedia.activityChat.dto.ChatMessage;
import project_pet_backEnd.socialMedia.activityChat.dto.UserActivity;
import project_pet_backEnd.socialMedia.activityChat.dto.UserRoom;
import project_pet_backEnd.socialMedia.activityChat.service.RoomService;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

@Api(tags = "使用者聊天室功能")
@RestController
@RequestMapping("/user/activity/chatroom")
@Validated
public class AcChatController {

    @Autowired
    private RoomService roomService;

    @ApiOperation("User拿到聊天室訊息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/{roomId}")
    public ResponseEntity<ResultResponse<List<ChatMessage>>> getMessages(@PathVariable("roomId") int roomId, @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        ResultResponse<List<ChatMessage>> response = roomService.getMessageByRoomId(roomId, page, 8);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation("User拿到聊天室列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/roomList")
    public ResponseEntity<ResultResponse<List<UserRoom>>> getUserRoomLists(@RequestAttribute("userId") Integer userId) {
        ResultResponse<List<UserRoom>> response = roomService.getUserRoomList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation("User拿到聊天室上線使用者")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/{roomId}/onlineUsers")
    public ResponseEntity<ResultResponse<List<UserActivity>>> getUserRoomOnlineUsers(@PathVariable("roomId") int roomId) {
        ResultResponse<List<UserActivity>> response = roomService.getOnlineUserList(roomId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}


