package project_pet_backEnd.homepageManage.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class AddNewsRequest {
    @NotBlank
    private String newsTitle;
    @NotBlank
    private String newsCont;
   // private Integer newsStatus; 後端自動生成
  //  private Date updateTime; 後端自動生成
}
