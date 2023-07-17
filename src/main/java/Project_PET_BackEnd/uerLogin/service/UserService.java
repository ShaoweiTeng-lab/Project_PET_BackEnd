package Project_PET_BackEnd.uerLogin.service;

import Project_PET_BackEnd.uerLogin.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
}
