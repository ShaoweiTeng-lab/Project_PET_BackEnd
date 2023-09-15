package project_pet_backEnd.homepageManage.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.homepage.vo.News;
import project_pet_backEnd.homepage.vo.NewsPic;


import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

    // 這裡不需要再添加其他方法，JpaRepository 已提供了基本的 CRUD 操作

    List<News> findAllByNewsStatus(Integer newsStatus, Sort sort);

}
