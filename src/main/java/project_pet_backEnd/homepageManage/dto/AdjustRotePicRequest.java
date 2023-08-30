package project_pet_backEnd.homepageManage.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Data
public class AdjustRotePicRequest {
        private Integer picNo;
        private String picRotateUrl;
        private Byte pic;
        private Integer picRotStatus;
        @NotBlank
        private String picRotStart;
        @NotNull
        private String picRotEnd;
        private  String rotePicPicLocateUrl;


}