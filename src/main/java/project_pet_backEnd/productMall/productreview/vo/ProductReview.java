package project_pet_backEnd.productMall.productreview.vo;

import lombok.Data;

import javax.persistence.Column;
import java.sql.Date;

@Data
public class ProductReview {
    private Integer pdReviewNo;
    private Integer pdNo;
    private Integer userId;
    private String pdReviewComment;
    private Integer pdReviewScore;
    @Column(name = " REVIEW_CREATED" , columnDefinition = "DATE  DEFAULT CURRENT_DATE")
    private Date ReviewCreated;

}
