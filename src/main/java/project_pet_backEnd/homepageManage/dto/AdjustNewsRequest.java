package project_pet_backEnd.homepageManage.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Date;
@Data
public class AdjustNewsRequest {
    @NotBlank
    private String newsTitle;
    @NotBlank
    private String newsCont;
    private Integer newsStatus;
    @NotBlank
    private Date updateTime;

}
