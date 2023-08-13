package project_pet_backEnd.socialMedia.post.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.socialMedia.post.dto.req.MesReq;
import project_pet_backEnd.socialMedia.post.dto.res.CheckRes;
import project_pet_backEnd.socialMedia.post.service.MesService;
import project_pet_backEnd.socialMedia.post.vo.Message;


@RestController
@RequestMapping("/user/social/message")
public class MessageController {

    @Autowired
    private MesService mesService;

    /**
     * 建立留言
     */
    @PostMapping
    public Message getMessageById(@RequestBody MesReq mesReq) {
        Message message = new Message();

        //get data by client
        message.setPostId(mesReq.getPostId());
        message.setUserId(mesReq.getUserId());
        message.setMessageContent(mesReq.getMesContent());

        //set default value
        message.setMessageStatus(0);


        //get by db
        //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Message successMes = mesService.create(message);

        return successMes;
    }

    /**
     * 修改留言內容
     */
    @PutMapping(("/{messageId}"))
    public CheckRes updateMessageById(@PathVariable("messageId") int mesId, @RequestBody MesReq mesReq, @RequestAttribute("userId") Integer userId) {

        Message message = new Message();
        message.setMessageId(mesId);
        message.setUserId(mesReq.getUserId());
        message.setPostId(mesReq.getPostId());
        message.setMessageContent(message.getMessageContent());


        CheckRes checkRes = null;
        if (userId == mesReq.getUserId()) {
            boolean updateResult = mesService.update(message);

            checkRes = new CheckRes();
            checkRes.setResult(updateResult);
            if (updateResult) {
                checkRes.setBody("修改成功");
            } else {
                checkRes.setBody("修改失敗");

            }


        }

        return checkRes;


    }




    /**
     * 刪除留言
     */

    @DeleteMapping("/{mesId}")
    public boolean deleteMessageById(@PathVariable("mesId") int mesId, @RequestAttribute("userId") Integer userId) {

        //查詢是否有此mes
        int result = mesService.checkMessageUserId(mesId);


        // userId 是否匹配,不能匹配無法刪除
        if (result != 0 && result == userId) {
            boolean deleteResult = mesService.delete(mesId);
            return deleteResult;
        } else {
            System.out.println("你沒有權限可以刪除留言");
            return false;
        }
    }
}
