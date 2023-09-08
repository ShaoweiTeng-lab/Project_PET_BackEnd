package project_pet_backEnd.socialMedia.activityChat.dao;


import project_pet_backEnd.socialMedia.activityChat.dto.UserActivity;

public interface UserDao {


    UserActivity createUser(int userId);

    boolean checkUserExists(int userId);

    UserActivity getUser(int userId);

    void addUserToOnlineList(String userId);

    void removeUserFromOnlineList(String userId);

    boolean checkUserOnlineStatus(int userId);

}