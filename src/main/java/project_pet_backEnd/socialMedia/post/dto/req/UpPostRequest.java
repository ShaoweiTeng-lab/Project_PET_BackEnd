package project_pet_backEnd.socialMedia.post.dto.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpPostRequest {
    int userId;
    @NotNull
    String postContent;
}
