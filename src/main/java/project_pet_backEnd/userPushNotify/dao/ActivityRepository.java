package project_pet_backEnd.userPushNotify.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.socialMedia.activityManager.vo.Activity;
@Repository
public interface ActivityRepository extends JpaRepository<Activity,Integer> {
    Activity findTopByOrderByActivityIdDesc();

}
