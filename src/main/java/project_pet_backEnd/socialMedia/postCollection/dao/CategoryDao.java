package project_pet_backEnd.socialMedia.postCollection.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import project_pet_backEnd.socialMedia.postCollection.vo.ColCategory;

import java.util.List;

public interface CategoryDao extends JpaRepository<ColCategory, Integer>,
        PagingAndSortingRepository<ColCategory, Integer>,
        QueryByExampleExecutor<ColCategory> {

    //找出user建立的標籤分類
    List<ColCategory> findAllByUserId(Integer userId);

    //確認user是否有預設標籤
    @Query("FROM ColCategory c where categoryName=:categoryName and c.userId=:userId")
    List<ColCategory> findColCategoryByCategoryNameAndUserId(@Param("categoryName") String categoryName, @Param("userId") int userId);

}
