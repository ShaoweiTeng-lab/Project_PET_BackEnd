package project_pet_backEnd.homepageManage.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class AdjustRotePicRequest {
    Integer picNo;
    String picLocateUrl;
    String pic;
    Integer picRotStatus;
    String picRotStart;
    String picRotEnd;
}