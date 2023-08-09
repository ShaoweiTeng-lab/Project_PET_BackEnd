package project_pet_backEnd.socialMedia.chat.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserDataRequest {
    private String userId;
    private String userName;
    private String password;

}
