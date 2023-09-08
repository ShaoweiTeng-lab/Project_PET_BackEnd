package project_pet_backEnd.socialMedia.activityChat.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.socialMedia.activityChat.dto.ChatMessage;
import project_pet_backEnd.socialMedia.activityChat.dto.UserRoom;
import project_pet_backEnd.socialMedia.activityChat.service.RoomService;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

@Api(tags = "管理員聊天室功能")
@RestController
@RequestMapping("/manager/activity/chatroom")
@Validated
public class ChatManagerController {

    @Autowired
    private RoomService roomService;

    //接收使用者聊天資訊
    @ApiOperation("查詢活動聊天室列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('社群管理')")
    @GetMapping("/roomList")
    public ResponseEntity<ResultResponse<List<UserRoom>>> getRoomLists() {
        ResultResponse<List<UserRoom>> response = roomService.getAllRoomList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation("查詢聊天室訊息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('社群管理')")
    @GetMapping("/{roomId}")
    public ResponseEntity<ResultResponse<List<ChatMessage>>> getMessages(@PathVariable("roomId") int roomId, @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        ResultResponse<List<ChatMessage>> response = roomService.getMessageByRoomId(roomId, page, 8);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
