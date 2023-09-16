package project_pet_backEnd.homepageManage.dto;

import javassist.bytecode.ByteArray;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
@Data
public class AdjustNewsPicRequest {
    @NotBlank
    private Integer newsNo;
    private Integer newsPicNo;
    private byte[] pic;
}
