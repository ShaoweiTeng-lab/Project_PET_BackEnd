package project_pet_backEnd.groomer.petgroomercollection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.orderby.PGOrderBy;
import project_pet_backEnd.groomer.petgroomer.dto.response.ChatRes;
import project_pet_backEnd.groomer.petgroomercollection.service.ChatService;
import project_pet_backEnd.groomer.petgroomercollection.vo.Chat;
import project_pet_backEnd.userManager.dto.Sort;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
     * 新增聊天紀錄
     * @param userId
     * @param pgId
     * @param chatStatus
     * @param chatText
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @PostMapping("/user/chat/insert")
    public ResponseEntity<?> insert(
            @RequestParam @NotNull Integer userId,
            @RequestParam @NotNull Integer pgId,
            @RequestParam @NotBlank String chatStatus,
            @RequestParam @NotBlank String chatText
    ) {
        Chat rest = new Chat();
        rest.setUserId(userId);
        rest.setPgId(pgId);
        rest.setChatStatus(chatStatus);
        rest.setChatText(chatText);
        ResultResponse resultResponse = service.insert(rest);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    /**
     * 更新聊天紀錄
     * @param chatNo
     * @param userId
     * @param pgId
     * @param chatStatus
     * @param chatText
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @PostMapping("/user/chat/update")
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
        ResultResponse resultResponse = service.update(rest);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    /**
     * 删除聊天紀錄
     * @param chatNo
     * @return
     */
    //    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @PostMapping("/user/chat/delete")
    public ResponseEntity<?> delete(
            @RequestParam @NotNull Integer chatNo
    ) {
        Chat rest = new Chat();
        rest.setChatNo(chatNo);
        ResultResponse resultResponse = service.delete(rest);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    /**
     * 聊天紀錄列表
     * @param userId
     * @param pgId
     * @param search
     * @param orderBy
     * @param sort
     * @param limit
     * @param offset
     * @return
     */
    @GetMapping("/user/chat/list")
    public ResponseEntity<Page<List<ChatRes>>> list(
            @RequestParam @NotNull Integer userId,
            @RequestParam @NotNull Integer pgId,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "orderBy", required = false, defaultValue = "NUM_APPOINTMENTS") PGOrderBy orderBy,
            @RequestParam(value = "sort", required = false, defaultValue = "desc") Sort sort,
            @RequestParam(value = "limit", defaultValue = "10") @Max(50) @Min(0) Integer limit,
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset) {
        PGQueryParameter pgQueryParameter = new PGQueryParameter();
        pgQueryParameter.setUserId(userId);
        pgQueryParameter.setPgId(pgId);
        pgQueryParameter.setSearch(search);
        pgQueryParameter.setOrder(orderBy);
        pgQueryParameter.setSort(sort);
        pgQueryParameter.setLimit(limit);
        pgQueryParameter.setOffset(offset);
        Page<List<ChatRes>> list = service.list(pgQueryParameter);
        return ResponseEntity.status(200).body(list);
    }
}
