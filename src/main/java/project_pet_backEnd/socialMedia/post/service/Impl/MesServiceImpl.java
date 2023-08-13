package project_pet_backEnd.socialMedia.post.service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project_pet_backEnd.socialMedia.post.dao.MessageDao;
import project_pet_backEnd.socialMedia.post.service.MesService;
import project_pet_backEnd.socialMedia.post.vo.Message;

import java.util.Optional;

@Service
public class MesServiceImpl implements MesService {

    @Autowired
    private MessageDao messageDao;

    @Override
    public Message create(Message message) {
        Message mes = messageDao.save(message);
        return mes;
    }

    @Override
    public boolean update(Message message) {
        Optional<Message> id = messageDao.findById(message.getMessageId());
        return false;
    }

    @Override
    @Transactional
    public boolean delete(int messageId) {


        messageDao.deleteById(messageId);
        return false;
    }

    @Override
    public int checkMessageUserId(int messageId) {
        Optional<Message> message = messageDao.findById(messageId);
        if (message.isPresent()) {
            Message userMes = message.get();
            return userMes.getUserId();
        } else {
            return 0;
        }

    }
}
