package project_pet_backEnd.productMall.productsmanage.dto;

import lombok.Data;
import project_pet_backEnd.userManager.dto.Sort;


@Data
public class ProductListQueryParameter {
    private  String search;
    private ProductListOrderBy order;
//  pdNo 商品編號
//  pdName 商品名稱
//  pdPrice 商品價錢
//  pdStatus 商品上下架狀態 0:上架中  1:已下架
    private Integer minPrice;
    private Integer maxPrice;
    private Sort sort;
    private Integer limit;
    private Integer offset;
}
