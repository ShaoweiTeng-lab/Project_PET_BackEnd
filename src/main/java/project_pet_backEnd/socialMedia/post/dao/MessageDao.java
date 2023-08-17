package project_pet_backEnd.socialMedia.post.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import project_pet_backEnd.socialMedia.post.vo.Message;


public interface MessageDao extends JpaRepository<Message, Integer> {
}
