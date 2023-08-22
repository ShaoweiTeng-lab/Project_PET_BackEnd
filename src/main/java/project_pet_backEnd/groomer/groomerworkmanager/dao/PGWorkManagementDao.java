package project_pet_backEnd.groomer.groomerworkmanager.dao;

import project_pet_backEnd.groomer.petgroomercollection.vo.Portfolio;

import java.util.List;

public interface PGWorkManagementDao {

//    個人作品集管理(後台僅美容師可用)功能：
//        進入介面後可透過MAN_ID打開自己的作品集管理(增 刪 改 查)
//        針對選取的作品集做管理

    public List<Portfolio> getPortfoliosByManagerId(Integer managerId);



}
