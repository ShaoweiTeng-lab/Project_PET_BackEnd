package project_pet_backEnd.homepageManage.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
@Data
public class AdjustRotePicRequest {
        private String picRotateUrl;
        private Byte pic;
        private Integer picRotStatus;
        @NotBlank
        private Date picRotStart;
        @NotBlank
        private Date picRotEnd;
        private  String rotePicPicLocateUrl;


}