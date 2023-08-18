package project_pet_backEnd.socialMedia.activityManager.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import project_pet_backEnd.socialMedia.activityManager.vo.Activity;

public interface ActivityDao extends JpaRepository<Activity, Integer>
        , PagingAndSortingRepository<Activity, Integer>
        , QueryByExampleExecutor<Activity>,
        JpaSpecificationExecutor<Activity> {

//    Activity create(Activity activity);
//
//    Activity update(Activity activity);
//
//    Activity cancel(Activity activity);
//
//    /*
//     * 查詢活動列表-反向排序+分頁
//     */
//
//    Page<Activity> getAllActivities(Integer managerId);


}
