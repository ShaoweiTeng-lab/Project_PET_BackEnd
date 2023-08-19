package project_pet_backEnd.groomer.petgroomerschedule.service.imp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.groomer.petgroomer.dao.PetGroomerDao;
import project_pet_backEnd.groomer.petgroomer.dto.response.GetAllGroomerListSortResForUser;
import project_pet_backEnd.groomer.petgroomer.vo.PetGroomer;
import project_pet_backEnd.groomer.petgroomerschedule.dao.PetScheduleRepository;
import project_pet_backEnd.groomer.petgroomerschedule.dto.response.GetScheduleRes;
import project_pet_backEnd.groomer.petgroomerschedule.service.GroomerScheduleService;

import java.util.List;

@Service
public class GroomerScheduleServiceImp implements GroomerScheduleService {
    @Autowired
    PetScheduleRepository petScheduleRepository;

    @Autowired
    PetGroomerDao petGroomerDao;

    @Override
    public List<GetAllGroomerListSortResForUser> getAllGroomerForSchedule() {
        List<PetGroomer> allGroomer = petGroomerDao.getAllGroomer();


        return null;
    }

    @Override
    public List<GetScheduleRes> getMonthScheduleForMan(Integer pgId, Integer month) {



        return null;
    }
}
