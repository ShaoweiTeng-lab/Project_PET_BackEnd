package project_pet_backEnd.productMall.productreview.vo;

import lombok.Data;

@Data
public class ProductReview {
    private Integer pdReviewNo;
    private Integer pdNo;
    private Integer userId;
    private String pdReviewComment;
    private Integer pdReviewScore;
    private byte[] pdReviewPic;
}
