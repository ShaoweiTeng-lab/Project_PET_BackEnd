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

import java.util.ArrayList;
import java.util.List;

@Service
public class GroomerScheduleServiceImp implements GroomerScheduleService {
    @Autowired
    PetScheduleRepository petScheduleRepository;

    @Autowired
    PetGroomerDao petGroomerDao;

    @Override
    public List<ListForScheduleRes> getAllGroomerForSchedule() {
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

        return getAllGroomerList;
    }

    @Override
    public List<GetScheduleRes> getMonthScheduleForMan(Integer pgId, Integer month) {



        return null;
    }
}
