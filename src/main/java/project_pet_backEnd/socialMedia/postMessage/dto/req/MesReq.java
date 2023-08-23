package project_pet_backEnd.socialMedia.postMessage.dto.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MesReq {
    @NotNull
    String mesContent;
}
