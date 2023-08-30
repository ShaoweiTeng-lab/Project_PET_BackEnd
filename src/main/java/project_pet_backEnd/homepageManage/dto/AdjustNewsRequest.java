package project_pet_backEnd.homepageManage.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
@Data
public class AdjustNewsRequest {
    @NotBlank
    private String newsTitle;
    @NotBlank
    private String newsCont;
    private Integer newsStatus;

  //  private Date updateTime;

}
