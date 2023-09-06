package project_pet_backEnd.userPushNotify.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.userPushNotify.vo.PictureInfo;

import java.util.List;

@Repository
public interface PictureInfoRepository extends JpaRepository<PictureInfo,Integer> {
    PictureInfo findFirstByOrderByPiDateDesc();
    List<PictureInfo> findByPorId(Integer id);
}
