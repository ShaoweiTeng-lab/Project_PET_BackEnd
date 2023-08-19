package project_pet_backEnd.groomer.petgroomerschedule.dto.response;

import lombok.Data;

@Data
public class ListForScheduleRes {
    private Integer pgId;
    private String pgName;
    private String pgPic;//Base64

}
