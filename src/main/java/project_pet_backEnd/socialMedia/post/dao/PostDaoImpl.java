package project_pet_backEnd.socialMedia.post.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project_pet_backEnd.socialMedia.post.dto.req.UpPostRequest;
import project_pet_backEnd.socialMedia.post.vo.POST;
import project_pet_backEnd.socialMedia.postMessage.vo.Message;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PostDaoImpl implements PostDao {

    private EntityManager entityManager;

    @Autowired
    public PostDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public POST create(POST post) {
        entityManager.persist(post);
        return post;
    }


    @Override
    @Transactional
    public POST update(int postId, UpPostRequest upPostRequest) {
        // find post by id
        POST post = entityManager.find(POST.class, postId);
        //update content
        post.setPostContent(upPostRequest.getPostContent());
        // save update
        POST newPost = entityManager.merge(post);
        return newPost;
    }

    @Override
    @Transactional
    public void delete(int postId) {
        POST post = entityManager.find(POST.class, postId);
        if (post != null) {

            //remove all association with post ---> remove message、
            List<Message> messages = post.getMessage();


            if (messages != null) {
                for (Message message : messages) {
                    int messageId = message.getMessageId();
                    Message mes = entityManager.find(Message.class, messageId);
                    entityManager.remove(mes);
                }
            }
//            List<PostCol> postColLists = post.getPostColList();
//            if (postColLists != null) {
//                for (PostCol postCol : postColLists) {
//                    Integer pcId = postCol.getPcId();
//                    PostCol postColl = entityManager.find(PostCol.class, pcId);
//                    entityManager.remove(postColl);
//                }
//            }

            //delete the post
            entityManager.remove(post);
        }
    }


    @Override
    public List<POST> getAllPosts() {
        TypedQuery<POST> postList = entityManager.createQuery("FROM POST", POST.class);
        return postList.getResultList();
    }

    @Override
    public POST getPostById(int postId) {
        POST post = entityManager.find(POST.class, postId);
        if (post != null) {
            return post;
        }
        return null;
    }
}
