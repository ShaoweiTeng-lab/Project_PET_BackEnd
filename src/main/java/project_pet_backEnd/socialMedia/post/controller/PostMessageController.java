package project_pet_backEnd.socialMedia.post.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.socialMedia.post.dto.req.MesReq;
import project_pet_backEnd.socialMedia.post.dto.req.UpMesReq;
import project_pet_backEnd.socialMedia.post.service.MesService;
import project_pet_backEnd.socialMedia.post.vo.Message;

import java.util.List;


@RestController
@RequestMapping("/user/social")
public class PostMessageController {

    @Autowired
    private MesService mesService;

    /**
     * 建立留言 @RequestAttribute("userId") Integer userId
     */
    @PostMapping("/message")
    public Message create(@RequestBody MesReq mesReq, @RequestAttribute("userId") Integer userId) {
        Message successMes = mesService.create(userId, mesReq);
        return successMes;
    }

    /**
     * 修改留言內容
     */
    @PutMapping(("/message/{messageId}"))
    public Message updateMessageById(@PathVariable("messageId") int mesId, @RequestBody UpMesReq upMesReq, @RequestAttribute("userId") Integer userId) {
        Message updateResult = mesService.update(userId, mesId, upMesReq);
        return updateResult;

    }


    /**
     * 刪除留言
     */

    @DeleteMapping("/message/{messageId}")
    public void deleteMessageById(@PathVariable("messageId") int mesId, @RequestAttribute("userId") Integer userId) {
        mesService.delete(userId, mesId);
    }

    /**
     * 拿到貼文的所有留言
     */

    @GetMapping("/message/post/{postId}")
    public List<Message> getMessagesByPostId(@PathVariable("postId") int postId) {
        List<Message> messageByPostId = mesService.findMessageByPostId(postId);
        return messageByPostId;
    }

    /**
     * 獲取單一留言內容
     */
    @GetMapping("/message/{messageId}")
    public Message getMessageById(@PathVariable("messageId") int messageId) {
        Message message = mesService.getMesById(messageId);
        return message;
    }
}
