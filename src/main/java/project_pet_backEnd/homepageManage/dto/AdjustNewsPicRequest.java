package project_pet_backEnd.homepageManage.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class AdjustNewsPicRequest {
    @NotBlank
    private Integer newsNo;
    private byte[] pic;
}
