package project_pet_backEnd.socialMedia.postMessage.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.socialMedia.postMessage.dao.MessageDao;
import project_pet_backEnd.socialMedia.postMessage.dto.req.MesReq;
import project_pet_backEnd.socialMedia.postMessage.dto.res.MesRes;
import project_pet_backEnd.socialMedia.postMessage.vo.Message;
import project_pet_backEnd.socialMedia.util.DateUtils;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class MesServiceImpl implements MesService {

    @Autowired
    private MessageDao messageDao;


    @Override
    public ResultResponse<String> create(Integer userId, Integer postId, MesReq mesReq) {
        try {
            Message message = new Message();
            message.setPostId(postId);
            message.setUserId(userId);
            //get data by client
            message.setMessageContent(mesReq.getMesContent());
            //set default value
            message.setMessageStatus(0);
            Message createMessage = messageDao.save(message);
            ResultResponse<String> response = new ResultResponse<>();
            if (createMessage.getMessageId() != null) {
                response.setMessage("建立成功");
                return response;
            } else {
                response.setMessage("建立失敗");
                return response;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public ResultResponse<MesRes> update(Integer userId, Integer mesId, MesReq mesReq) {
        Optional<Message> optionalMessage = messageDao.findById(mesId);
        if (!optionalMessage.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "此留言不存在");
        Message updateMes = optionalMessage.get();
        if (updateMes.getUserId() != userId)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "你沒有權限修改此留言");
        updateMes.setMessageContent(mesReq.getMesContent());
        Message newMes = messageDao.save(updateMes);
        ResultResponse<MesRes> response = convertToMesRes(newMes);
        return response;
    }

    @Override
    public ResultResponse<String> delete(Integer userId, int messageId) {
        ResultResponse<String> resultResponse = new ResultResponse<>();
        Optional<Message> message = messageDao.findById(messageId);
        if (!message.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "此留言不存在，無法刪除");
        if (message.get().getUserId() != userId)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "你沒有權限刪除自己的留言");
        try {
            messageDao.deleteById(messageId);
            resultResponse.setMessage("留言刪除成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "留言刪除失敗");
        }
        return resultResponse;
    }

    //  ==========================查詢貼文所有留言 ==========================  //
    @Override
    public ResultResponse<List<MesRes>> findMessageByPostId(int postId) {
        List<Message> messageList = messageDao.findByPostId(postId);
        ResultResponse<List<MesRes>> response = convertToMesResList(messageList);
        return response;
    }

    //  ========================== 查詢單一留言內容 ==========================  //
    @Override
    public ResultResponse<MesRes> getMesById(int messageId) {
        Optional<Message> message = messageDao.findById(messageId);
        if (!message.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "此留言不存在");
        ResultResponse<MesRes> response = convertToMesRes(message.get());
        return response;
    }

    //  ========================== 留言轉換 ==========================  //
    public ResultResponse<MesRes> convertToMesRes(Message mes) {
        MesRes mesRes = new MesRes();
        try {
            mesRes.setMessageId(mes.getMessageId());
            mesRes.setUserId(mes.getUserId());
            mesRes.setPostId(mes.getPostId());
            mesRes.setUserName(mes.getUser().getUserName());
            mesRes.setMessageContent(mes.getMessageContent());
            mesRes.setUpdateTime(DateUtils.dateTimeSqlToStr(mes.getUpdateTime()));
            mesRes.setMessageStatus(mes.getMessageStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResultResponse<MesRes> messageResultResponse = new ResultResponse<>();
        messageResultResponse.setMessage(mesRes);
        return messageResultResponse;
    }
    //  ========================== 留言清單轉換 ==========================  //

    public ResultResponse<List<MesRes>> convertToMesResList(List<Message> messageList) {

        List<MesRes> mesResList = new ArrayList<>();
        for (Message mes : messageList) {
            MesRes mesRes = new MesRes();
            mesRes.setMessageId(mes.getMessageId());
            mesRes.setUserId(mes.getUserId());
            mesRes.setPostId(mes.getPostId());
            mesRes.setUserName(mes.getUser().getUserName());
            mesRes.setMessageContent(mes.getMessageContent());
            mesRes.setUpdateTime(DateUtils.dateTimeSqlToStr(mes.getUpdateTime()));
            mesRes.setMessageStatus(mes.getMessageStatus());
            mesResList.add(mesRes);
        }

        ResultResponse<List<MesRes>> response = new ResultResponse<>();
        response.setMessage(mesResList);
        return response;
    }


}
