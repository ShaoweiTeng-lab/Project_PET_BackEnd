package project_pet_backEnd.socialMedia.postCollection.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import project_pet_backEnd.socialMedia.postCollection.vo.PostCol;

import java.util.List;

public interface PostCollectionDao extends JpaRepository<PostCol, Integer>,
        PagingAndSortingRepository<PostCol, Integer> {
    Page<PostCol> findAllByUserId(int userId, Pageable pageable);

    List<PostCol> findByUserIdAndPostId(int userId, int postId);

    List<PostCol> findAllByPostId(int postId);

    @Transactional
    void deleteByPostId(int postId);

}
