package project_pet_backEnd.homepage.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import project_pet_backEnd.socialMedia.activityManager.vo.Activity;

import java.util.List;

public interface ActivityHomeDao extends JpaRepository<Activity, Integer> {
    List<Activity> findAllByStatus(Integer status, Sort sort);
}
