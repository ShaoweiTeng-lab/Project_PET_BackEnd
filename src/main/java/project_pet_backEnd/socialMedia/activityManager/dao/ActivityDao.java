package project_pet_backEnd.socialMedia.activityManager.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import project_pet_backEnd.socialMedia.activityManager.vo.Activity;

public interface ActivityDao extends JpaRepository<Activity, Integer>
        , PagingAndSortingRepository<Activity, Integer>
        , QueryByExampleExecutor<Activity>,
        JpaSpecificationExecutor<Activity> {

    Page<Activity> findAll(Pageable pageable);

    /**
     * 使用者關鍵字搜尋
     */

    Page<Activity> findByActivityContentContaining(String activityContent, Pageable pageable);

    Activity findFirstByOrderByActivityIdDesc();


}
