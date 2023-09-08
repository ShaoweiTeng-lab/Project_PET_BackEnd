package project_pet_backEnd.productMall.productcollection.dto;

import lombok.Data;

import java.sql.Date;

@Data        //新增收藏
public class EditProductCollect {

    private Integer pdNo;

    private Integer userId;

    private Date pdcCreated;
}
