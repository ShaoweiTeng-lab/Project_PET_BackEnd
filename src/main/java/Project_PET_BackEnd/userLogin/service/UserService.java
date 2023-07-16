package Project_PET_BackEnd.userLogin.service;

import Project_PET_BackEnd.userLogin.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
}
