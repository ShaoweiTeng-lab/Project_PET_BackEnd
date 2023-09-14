package project_pet_backEnd.homepageManage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.homepage.vo.News;
import project_pet_backEnd.homepage.vo.NewsPic;
import project_pet_backEnd.userManager.dto.Sort;

import java.util.List;

@Repository
public interface NewsPicRepository extends JpaRepository<NewsPic, Integer> {

    NewsPic findByNewsNo(Integer newsNo);

    //  @Query


}
