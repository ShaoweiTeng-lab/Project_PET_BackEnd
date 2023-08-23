package project_pet_backEnd.userPushNotify.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.userPushNotify.vo.PictureInfo;

@Repository
public interface PictureInfoRepository extends JpaRepository<PictureInfo,Integer> {
    PictureInfo findFirstByOrderByPiDateDesc();
}
