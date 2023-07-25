package project_pet_backEnd.manager.service;

import project_pet_backEnd.manager.dao.ManagerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    @Autowired
    private ManagerDao managerDao;
}
