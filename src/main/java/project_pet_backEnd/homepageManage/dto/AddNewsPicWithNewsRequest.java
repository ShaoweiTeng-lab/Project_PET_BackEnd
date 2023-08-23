package project_pet_backEnd.homepageManage.dto;

import javax.validation.constraints.NotBlank;

public class AddNewsPicWithNewsRequest {
    @NotBlank
    private Integer newsNo;
    private byte[] pic;
}
