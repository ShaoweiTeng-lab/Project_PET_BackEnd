package project_pet_backEnd.socialMedia.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Room {
    private String id;
    private String name;

    public Room(String id, String activeName) {
        this.id = id;
        // room name equals activity name
        this.name = activeName;
    }


}
