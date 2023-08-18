package project_pet_backEnd.groomer.petgroomerschedule.service;

import project_pet_backEnd.groomer.petgroomer.dto.response.GetAllGroomerListSortResForUser;
import project_pet_backEnd.groomer.petgroomerschedule.dto.response.GetScheduleRes;
import java.util.List;

public interface GroomerScheduleService {

    //取得美容師清單for 班表
    List<GetAllGroomerListSortResForUser> getAllGroomerForSchedule();


    //按月份取得班表 for Man
    List<GetScheduleRes> getMonthScheduleForMan(Integer pgId,Integer month);


}
