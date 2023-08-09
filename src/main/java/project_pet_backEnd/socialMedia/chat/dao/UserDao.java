package project_pet_backEnd.socialMedia.chat.dao;

import project_pet_backEnd.socialMedia.chat.dto.User;

import java.util.Set;

public interface UserDao {


    User createUser(int userId, String username, Boolean isOnline);

    boolean checkUserExists(int userId);

    User getUser(int userId, String username);

    Set<String> getUserRoomIds(int userId);


    void addUserToOnlineList(String userId);

    void removeUserFromOnlineList(String userId);


}
