package project_pet_backEnd.socialMedia.post.post.dto.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PostReq {
    int userId;
    @NotNull
    String content;
}
