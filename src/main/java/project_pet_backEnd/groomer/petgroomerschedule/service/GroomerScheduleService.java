package project_pet_backEnd.groomer.petgroomerschedule.service;

import project_pet_backEnd.groomer.petgroomer.dto.response.GetAllGroomerListSortResForUser;
import project_pet_backEnd.groomer.petgroomerschedule.dto.response.GetScheduleRes;
import project_pet_backEnd.groomer.petgroomerschedule.dto.response.ListForScheduleRes;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

public interface GroomerScheduleService {

    //取得美容師清單for 班表
    ResultResponse<List<ListForScheduleRes>> getAllGroomerForSchedule();

    //按月份取得班表 for Man
    public ResultResponse<List<GetScheduleRes>> getMonthScheduleForMan(Integer year,Integer pgId, Integer month);

    }
