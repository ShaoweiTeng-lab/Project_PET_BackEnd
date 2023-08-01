package project_pet_backEnd.groomer.service;

import org.springframework.beans.factory.annotation.Autowired;
import project_pet_backEnd.groomer.dao.PetGroomerDao;

public class PetGrommerService {

    @Autowired
    PetGroomerDao petGroomerDao;
}
