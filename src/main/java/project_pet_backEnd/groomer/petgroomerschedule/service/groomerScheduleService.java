package project_pet_backEnd.groomer.petgroomerschedule.service;

import project_pet_backEnd.groomer.petgroomerschedule.dto.response.GetScheduleRes;
import java.util.List;

public interface groomerScheduleService {

    //按月份取得班表 for Man
    List<GetScheduleRes> getMonthScheduleForMan();




}
