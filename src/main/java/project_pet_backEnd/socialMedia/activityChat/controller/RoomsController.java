package project_pet_backEnd.socialMedia.activityChat.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.socialMedia.activityChat.dto.request.CreateRoomRequest;
import project_pet_backEnd.socialMedia.activityChat.dto.response.GetMessagesResponse;
import project_pet_backEnd.socialMedia.activityChat.dto.response.GetUserRoomIdsSetResponse;
import project_pet_backEnd.socialMedia.activityChat.dto.response.ResponseMessage;
import project_pet_backEnd.socialMedia.activityChat.service.ChatService;
import project_pet_backEnd.socialMedia.activityChat.service.RoomsService;


@RestController
@RequestMapping("/user/activityChatroom")
public class RoomsController {

    @Autowired
    private RoomsService roomsService;

    @Autowired
    private ChatService chatService;


    /*
     * Get rooms for specific user id.
     */
    @GetMapping("/getRoomsId")
    public ResponseEntity<GetUserRoomIdsSetResponse> getRooms(@RequestAttribute("userId") Integer userId) {
        GetUserRoomIdsSetResponse rs = roomsService.getUserGroupRoomIds(userId);
        return ResponseEntity.status(200).body(rs);
    }

    /*
     * Create chat room for every activity.
     */
    @PostMapping("activity")
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomRequest createRoomRequest) {
        roomsService.createGroupRoom(createRoomRequest.getActivityId(), createRoomRequest.getActivityName());
        ResponseMessage rs = new ResponseMessage();
        rs.setMessage("建立成功");
        return ResponseEntity.status(200).body(rs);

    }


    /*
     * Get Messages By RoomId.
     */
    @GetMapping("messages/{roomId}")
    public ResponseEntity<GetMessagesResponse> getMessages(@PathVariable String roomId, @RequestParam int offset, @RequestParam int size) {

        GetMessagesResponse rs = chatService.getMessages(roomId, offset, size);
        return ResponseEntity.status(200).body(rs);
    }


    /*
     * delete room for when activity done.
     */


}
