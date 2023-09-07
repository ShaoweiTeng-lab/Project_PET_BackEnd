package project_pet_backEnd.productMall.productcollection.dto;

import lombok.Data;

import java.sql.Date;

@Data       //(查看)商品收藏列表
public class ProductCollectList {

    private Integer pdNo;

    private Integer userId;

    private Integer pdPrice;

    private Date pdcCreated;

    private String base64Image;

}
