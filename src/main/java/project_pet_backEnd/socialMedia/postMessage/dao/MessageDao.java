package project_pet_backEnd.socialMedia.postMessage.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import project_pet_backEnd.socialMedia.postMessage.vo.Message;

import java.util.List;


public interface MessageDao extends JpaRepository<Message,Integer> {
    List<Message> findByPostId(Integer postId);
}
