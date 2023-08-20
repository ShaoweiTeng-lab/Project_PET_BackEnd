package project_pet_backEnd.groomer.petgroomerschedule.service;

import project_pet_backEnd.groomer.petgroomerschedule.dto.request.ScheduleInsertReq;
import project_pet_backEnd.groomer.petgroomerschedule.dto.request.ScheduleModifyReq;
import project_pet_backEnd.groomer.petgroomerschedule.dto.response.GetScheduleRes;
import project_pet_backEnd.groomer.petgroomerschedule.dto.response.ListForScheduleRes;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

public interface GroomerScheduleService {

    //取得美容師清單 ->班表 for Man
    ResultResponse<List<ListForScheduleRes>> getAllGroomerForSchedule();

    //按月份取得班表 for Man
    ResultResponse<List<GetScheduleRes>> getMonthScheduleForMan(Integer year, Integer pgId, Integer month);
    //修改班表 for Man
    ResultResponse<String> modifySchedule(ScheduleModifyReq scheduleModifyReq);


    ResultResponse<String> insertNewSchedule(ScheduleInsertReq scheduleInsertReq);

}
