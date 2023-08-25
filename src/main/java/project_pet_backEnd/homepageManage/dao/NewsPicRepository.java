package project_pet_backEnd.homepageManage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.homepage.vo.News;
import project_pet_backEnd.homepage.vo.NewsPic;

import java.util.List;

@Repository
public interface NewsPicRepository extends JpaRepository<NewsPic, Integer> {

    List<NewsPic> findByNewsNo(Integer newsNo);

  //  @Query


}
