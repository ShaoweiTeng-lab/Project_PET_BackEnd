package project_pet_backEnd.socialMedia.post.dto.res;

import lombok.Data;

@Data
public class CheckRes {
    boolean result;
    String body;
    String errorMes;

}
