package project_pet_backEnd.socialMedia.post.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project_pet_backEnd.socialMedia.post.dao.PostDao;
import project_pet_backEnd.socialMedia.post.vo.POST;

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
    public POST update(POST post) {
        return null;
    }

    @Override
    public boolean delete(int postId) {
        return false;
    }

    @Override
    public boolean reportPost(int postId) {
        return false;
    }

    @Override
    public List<POST> getAllPosts() {
        TypedQuery<POST> postList = entityManager.createQuery("FROM POST", POST.class);
        return postList.getResultList();
    }
}
