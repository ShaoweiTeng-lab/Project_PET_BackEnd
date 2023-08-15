package project_pet_backEnd.socialMedia.post.service;


import project_pet_backEnd.socialMedia.post.dto.req.MesReq;
import project_pet_backEnd.socialMedia.post.dto.req.UpMesReq;
import project_pet_backEnd.socialMedia.post.vo.Message;

import java.util.List;


public interface MesService {


    Message create(Integer userId, MesReq mesReq);

    Message update(Integer userId, Integer mesId, UpMesReq upMesReq);

    void delete(Integer userId, int messageId);

    List<Message> findMessageByPostId(int postId);

    Message getMesById(int messageId);


}
