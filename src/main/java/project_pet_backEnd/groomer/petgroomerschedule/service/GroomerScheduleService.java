package project_pet_backEnd.groomer.petgroomerschedule.service;

import project_pet_backEnd.groomer.petgroomerschedule.dto.response.GetScheduleRes;
import project_pet_backEnd.groomer.petgroomerschedule.dto.response.ListForScheduleRes;

import java.util.List;

public interface GroomerScheduleService {

    //取得美容師清單for 班表
    List<ListForScheduleRes> getAllGroomerForSchedule();


    //按月份取得班表 for Man
    List<GetScheduleRes> getMonthScheduleForMan(Integer pgId,Integer month);


}
