package project_pet_backEnd.socialMedia.activityUser.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import project_pet_backEnd.socialMedia.activityManager.vo.Activity;
import project_pet_backEnd.socialMedia.activityUser.vo.JoinActivity;
import project_pet_backEnd.socialMedia.activityUser.vo.JoinKey;

public interface UserActivityDao extends JpaRepository<JoinActivity, JoinKey>,
        PagingAndSortingRepository<JoinActivity, JoinKey>,
        QueryByExampleExecutor<JoinActivity>,
        JpaSpecificationExecutor<JoinActivity> {

    JoinActivity findByActivityIdAndUserId(int userId, int activityId);

    //查看活動清單歷史
    Page<JoinActivity> findAllByUserId(int userId, Pageable pageable);
}