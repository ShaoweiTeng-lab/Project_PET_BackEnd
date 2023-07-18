package project_pet_backEnd.userLogin.service;

import project_pet_backEnd.userLogin.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
}
