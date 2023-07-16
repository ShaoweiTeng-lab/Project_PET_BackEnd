package Project_PET_BackEnd.managerLogin.service;

import Project_PET_BackEnd.managerLogin.dao.ManagerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    @Autowired
    private ManagerDao managerDao;
}
