package project_pet_backEnd.socialMedia.postCollection.dto.req;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class CategoryReq {
    @Size(min = 2, message = "{validation.name.size.too_short}")
    @Size(max = 30, message = "{validation.name.size.too_long}")
    String tagName;
    @Size(max = 100, message = "{validation.name.size.too_long}")
    String tagDisc;
}
