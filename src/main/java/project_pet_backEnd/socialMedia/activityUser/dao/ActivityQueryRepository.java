package project_pet_backEnd.socialMedia.activityUser.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import project_pet_backEnd.socialMedia.activityManager.vo.Activity;

public interface ActivityQueryRepository extends
        PagingAndSortingRepository<Activity, Integer>,
        QueryByExampleExecutor<Activity>,
        JpaSpecificationExecutor<Activity> {

    Page<Activity> findByActivityTitle(String activityTitle, Pageable pageable);

    Page<Activity> findByActivityContentLike(String activityContent, Pageable pageable);

    Activity findByActivityId(Integer activityId);


}
