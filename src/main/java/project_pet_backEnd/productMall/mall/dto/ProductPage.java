package project_pet_backEnd.productMall.mall.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductPage {
    private Integer pdNo;
    private String pdName;
    private Integer pdPrice;
    private List<String> base64Image;
    private String pdDescription;


    public void addBase64Image(String base64Image) {
        if (this.base64Image == null) {
            this.base64Image = new ArrayList<>();
        }
        this.base64Image.add(base64Image);
    }


//    ---以下是評論---
//    private String userPic; //放預設圖就可
//    private String userNickName;
//    private String pdReviewComment;
//    private Integer pdReviewScore;
//    private Date ReviewCreated;
//    private Integer pdTotalreview;
//    private Integer pdScore;

}
