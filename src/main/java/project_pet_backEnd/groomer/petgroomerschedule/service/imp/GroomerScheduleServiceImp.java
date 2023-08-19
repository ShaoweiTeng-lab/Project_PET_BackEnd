package project_pet_backEnd.groomer.petgroomerschedule.service.imp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.groomer.petgroomer.dao.PetGroomerDao;
import project_pet_backEnd.groomer.petgroomer.vo.PetGroomer;
import project_pet_backEnd.groomer.petgroomerschedule.dao.PetScheduleRepository;
import project_pet_backEnd.groomer.petgroomerschedule.dto.response.GetScheduleRes;
import project_pet_backEnd.groomer.petgroomerschedule.dto.response.ListForScheduleRes;
import project_pet_backEnd.groomer.petgroomerschedule.service.GroomerScheduleService;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroomerScheduleServiceImp implements GroomerScheduleService {
    @Autowired
    PetScheduleRepository petScheduleRepository;

    @Autowired
    PetGroomerDao petGroomerDao;

    @Override
    public ResultResponse<List<ListForScheduleRes>> getAllGroomerForSchedule() {
        List<PetGroomer> allGroomer = petGroomerDao.getAllGroomer();
        if(allGroomer==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "未找到任一美容師");

        List<ListForScheduleRes> getAllGroomerList = new ArrayList<>();

        for(PetGroomer groomer : allGroomer){
            ListForScheduleRes resList = new ListForScheduleRes();

            resList.setPgId(groomer.getPgId());
            resList.setPgName(groomer.getPgName());
            resList.setPgPic(AllDogCatUtils.base64Encode(groomer.getPgPic()));
            getAllGroomerList.add(resList);
        }
        ResultResponse<List<ListForScheduleRes>> resultResponse = new ResultResponse<>();
        resultResponse.setMessage(getAllGroomerList);
        return resultResponse;
    }

    @Override
    public ResultResponse<List<GetScheduleRes>>getMonthScheduleForMan(Integer year,Integer pgId, Integer month) {
        List<String> dataByYearMonthAndPgId = petScheduleRepository.findDataByYearMonthAndPgId(year, month, pgId);

        List<GetScheduleRes> scheduleResList = new ArrayList<>();
        for (String data : dataByYearMonthAndPgId) {
            String[] parts = data.split(",");

            if (parts.length == 5) {
                GetScheduleRes scheduleRes = new GetScheduleRes();
                scheduleRes.setPgsId(Integer.parseInt(parts[0]));
                scheduleRes.setPgName(parts[1]);
                scheduleRes.setPgId(Integer.parseInt(parts[2]));
                scheduleRes.setPgsDate(parts[3]);
                scheduleRes.setPgsState(parts[4]);

                scheduleResList.add(scheduleRes);
            }
        }
        ResultResponse<List<GetScheduleRes>> resultResponse = new ResultResponse<>();
        resultResponse.setMessage(scheduleResList);
        return resultResponse;
    }
}
