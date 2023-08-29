package project_pet_backEnd.socialMedia.postCollection.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import project_pet_backEnd.socialMedia.postCollection.vo.TagJoinKey;
import project_pet_backEnd.socialMedia.postCollection.vo.UserTagCategory;

import java.util.List;

public interface UserTagCategoryDao extends JpaRepository<UserTagCategory, TagJoinKey>,
        PagingAndSortingRepository<UserTagCategory, TagJoinKey> {

    //查詢此收藏否已經存在此標籤分類中
    @Override
    boolean existsById(TagJoinKey tagJoinKey);

    //透過tagId和userId查詢所有在此標籤分類中的貼文資訊
    List<UserTagCategory> findAllByUserIdAndPctId(int userId, int pctId);
}
