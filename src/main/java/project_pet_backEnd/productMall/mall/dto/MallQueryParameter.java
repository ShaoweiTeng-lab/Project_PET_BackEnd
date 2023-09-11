package project_pet_backEnd.productMall.mall.dto;

import lombok.Data;

import project_pet_backEnd.userManager.dto.Sort;

@Data
public class MallQueryParameter {
    private String search;
    private Integer pdNo;
    private String pdName;
    private MallOrderBy order;
    private Sort sort;
    private Integer limit;
    private Integer offset;
    private Integer minPrice;
    private Integer maxPrice;
}
