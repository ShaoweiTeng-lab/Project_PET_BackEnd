package project_pet_backEnd.groomer.game.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_pet_backEnd.groomer.game.service.GameService;
import project_pet_backEnd.user.dao.UserRepository;
import project_pet_backEnd.user.vo.User;

@Service
public class GameServiceImp implements GameService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void updateUserPoint(Integer userId,Integer point) {
        User user = userRepository.findById(userId).orElse(null);

        user.setUserPoint(user.getUserPoint()+point);
        userRepository.save(user);
    }
}
