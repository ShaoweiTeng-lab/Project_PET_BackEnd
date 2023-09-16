package project_pet_backEnd.homepageManage.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Data
public class AdjustNewsRequest {
    private Integer newsNo;
    @NotBlank
    private String newsTitle;
    @NotBlank
    private String newsCont;
    private Integer newsStatus;
    String newsPic;

  //  private Date updateTime;

}
