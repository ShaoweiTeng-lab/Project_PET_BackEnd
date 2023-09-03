package project_pet_backEnd.homepageManage.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Data
public class AdjustRotePicRequest {
        private Integer picNo;
        private String picRotateUrl;
        private MultipartFile pic;
        private Integer picRotStatus;
        @NotNull
        private Date picRotStart;
        @NotNull
        private Date picRotEnd;
        private  String rotePicPicLocateUrl;


}