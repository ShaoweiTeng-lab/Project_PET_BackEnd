package project_pet_backEnd.socialMedia.postCollection.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import project_pet_backEnd.socialMedia.postCollection.vo.PostCol;

public interface PostCollectionDao extends JpaRepository<PostCol,Integer> {
}
