package project_pet_backEnd.socialMedia.post.dao;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import project_pet_backEnd.socialMedia.post.vo.POST;

import java.util.List;

@Repository
public interface PostDao extends
        PagingAndSortingRepository<POST, Integer>
        , JpaRepository<POST, Integer> {

    Page<POST> findAllByPostStatus(Pageable pageable, Integer postStatus);


    //    POST create(POST post);
//
//    List<POST> getAllPosts();
//
//    POST getPostById(int postId);
//
//    POST update(int postId, UpPostRequest upPostRequest);
//
//    void delete(int postId);


}