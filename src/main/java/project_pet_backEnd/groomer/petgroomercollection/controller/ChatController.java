package project_pet_backEnd.groomer.petgroomercollection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.response.ChatRes;
import project_pet_backEnd.groomer.petgroomercollection.service.ChatService;
import project_pet_backEnd.groomer.petgroomercollection.vo.Chat;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@RestController
@Validated
public class ChatController {

    @Autowired
    ChatService service;

    /**
     * 新增聊天记录
     *
     * @param chat
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @PostMapping("/chat/insert")
    public ResponseEntity<?> insert(
            @RequestBody Chat chat
    ) {
        Chat rest = new Chat();
        rest.setUserId(chat.getUserId());
        rest.setPgId(chat.getPgId());
        rest.setChatStatus(chat.getChatStatus());
        rest.setChatText(chat.getChatText());
        rest.setChatCreated(new Date());
        ResultResponse resultResponse = service.insert(rest);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    /**
     * 更新聊天记录
     * @param chatNo
     * @param userId
     * @param pgId
     * @param chatStatus
     * @param chatText
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @PostMapping("/chat/update")
    public ResponseEntity<?> update(
            @RequestParam @NotNull Integer chatNo,
            @RequestParam @NotNull Integer userId,
            @RequestParam @NotNull Integer pgId,
            @RequestParam @NotBlank String chatStatus,
            @RequestParam @NotBlank String chatText
    ) {
        Chat rest = new Chat();
        rest.setChatNo(chatNo);
        rest.setUserId(userId);
        rest.setPgId(pgId);
        rest.setChatStatus(chatStatus);
        rest.setChatText(chatText);
        rest.setChatCreated(new Date());
        ResultResponse resultResponse = service.update(rest);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    /**
     * 删除聊天记录
     * @param chatNo
     * @return
     */
    //    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @PostMapping("/chat/delete")
    public ResponseEntity<?> delete(
            @RequestParam @NotNull Integer chatNo
    ) {
        Chat rest = new Chat();
        rest.setChatNo(chatNo);
        ResultResponse resultResponse = service.delete(rest);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    /**
     * 聊天记录列表
     * @param chat
     * @return
     */
    @PostMapping("/chat/list")
    public ResponseEntity<Page<List<ChatRes>>> list(
            @RequestBody Chat chat) {
        PGQueryParameter pgQueryParameter = new PGQueryParameter();
        pgQueryParameter.setUserId(chat.getUserId());
        pgQueryParameter.setPgId(chat.getPgId());
        Page<List<ChatRes>> list = service.list(pgQueryParameter);
        return ResponseEntity.status(200).body(list);
    }

    @PostMapping("/chat/userList")
    public ResponseEntity<Page<List<ChatRes>>> userList(
            @RequestBody Chat chat) {
        PGQueryParameter pgQueryParameter = new PGQueryParameter();
        pgQueryParameter.setPgId(chat.getPgId());
        Page<List<ChatRes>> list = service.userList(pgQueryParameter);
        return ResponseEntity.status(200).body(list);
    }
}
