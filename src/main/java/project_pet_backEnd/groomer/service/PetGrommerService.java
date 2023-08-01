package project_pet_backEnd.groomer.service;

import org.springframework.beans.factory.annotation.Autowired;
import project_pet_backEnd.groomer.dao.PetGroomerDao;
import project_pet_backEnd.groomer.vo.PetGroomer;
import project_pet_backEnd.user.dto.ResultResponse;

public class PetGrommerService {

    @Autowired
    PetGroomerDao petGroomerDao;

    public ResultResponse insertGroomer(PetGroomer petGroomer){


    }


}
