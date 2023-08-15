package project_pet_backEnd.socialMedia.post.service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.socialMedia.post.dao.MessageDao;
import project_pet_backEnd.socialMedia.post.dao.PostDao;
import project_pet_backEnd.socialMedia.post.dto.req.MesReq;
import project_pet_backEnd.socialMedia.post.dto.req.UpMesReq;
import project_pet_backEnd.socialMedia.post.service.MesService;
import project_pet_backEnd.socialMedia.post.vo.Message;
import project_pet_backEnd.socialMedia.post.vo.POST;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class MesServiceImpl implements MesService {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private PostDao postDao;

    @Override
    public Message create(Integer userId, MesReq mesReq) {

        if (mesReq.getUserId() != userId) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "你沒有權限建立貼文");
        }

        //already check user list [1,2,3]
        List<Integer> userList = new ArrayList<>();
        userList.add(1);
        userList.add(2);
        userList.add(3);
        //check user existed
        boolean checkUser = false;
        for (Integer user : userList) {
            System.out.println(user);
            if (mesReq.getUserId() == user) {
                checkUser = true;
            }
        }
        if (!checkUser) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "無此使用者");

        } else {
            Message message = new Message();

            //get data by client
            message.setPostId(mesReq.getPostId());
            message.setUserId(mesReq.getUserId());
            message.setMessageContent(mesReq.getMesContent());

            //set default value
            message.setMessageStatus(0);
            Message mes = messageDao.save(message);
            return mes;
        }


    }

    @Override
    public Message update(Integer userId, Integer mesId, UpMesReq upMesReq) {
        Message message = new Message();

        //find entity by id
        Message updateMes = messageDao.findById(mesId).get();
        //update entity data
        if (upMesReq.getUserId() == updateMes.getUserId() && updateMes != null && upMesReq.getUserId() == userId) {
            updateMes.setMessageContent(upMesReq.getMesContent());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "你沒有權限修改此留言");
        }

        //save update
        Message newMes = messageDao.save(updateMes);

        return newMes;
    }

    @Override
    @Transactional
    public void delete(Integer userId, int messageId) {

        //find entity by id
        Message removeMes = messageDao.findById(messageId).get();
        //update entity data
        if (userId == removeMes.getUserId() && removeMes != null) {
            messageDao.deleteById(messageId);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "你沒有權限刪除此留言");
        }
    }


    @Override
    public List<Message> findMessageByPostId(int postId) {
        POST result = postDao.getPostById(postId);


        List<Message> messageList = new ArrayList<>();
        if (result != null) {
            List<Message> messages = messageDao.findAll(Sort.by(Sort.Direction.DESC, "updateTime"));

            for (Message message : messages) {
                if (message.getPostId() == postId) {
                    messageList.add(message);
                }
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "此貼文不存在");
        }
        return messageList;
    }

    @Override
    public Message getMesById(int messageId) {
        Optional<Message> message = messageDao.findById(messageId);
        if (!message.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "此留言不存在");
        }
        return message.get();
    }
}
