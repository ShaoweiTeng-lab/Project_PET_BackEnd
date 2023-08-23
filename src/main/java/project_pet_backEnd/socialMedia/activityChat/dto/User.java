package project_pet_backEnd.socialMedia.activityChat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int userId;
    private String username;
    private boolean isOnline;
}
