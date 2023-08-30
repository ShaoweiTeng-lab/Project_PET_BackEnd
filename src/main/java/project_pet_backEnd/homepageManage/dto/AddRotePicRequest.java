package project_pet_backEnd.homepageManage.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class AddRotePicRequest {
    private String picLocateUrl;
    private byte[] pic;
    private Integer picRotStatus;
    @NotNull
    private String picRotStart;
    @NotNull
    private String picRotEnd;

}
