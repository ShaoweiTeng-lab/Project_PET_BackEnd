package project_pet_backEnd.socialMedia.post.message.dto.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UpMesReq {
    @Pattern(regexp = "",message = "is required")
    int userId;
    @NotNull
    String mesContent;
}
