package project_pet_backEnd.socialMedia.post.service;


import project_pet_backEnd.socialMedia.post.vo.Message;

public interface MesService {


    /**
     * create message by postId
     */

    public Message create(Message message);

    public boolean update(Message message);

    public boolean delete(int messageId);

    public int checkMessageUserId(int messageId);



}
