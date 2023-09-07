package project_pet_backEnd.socialMedia.activityChat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserActivity {
    private int userId;
    private String username;
    private String userPic;
    private boolean isOnline;
}
