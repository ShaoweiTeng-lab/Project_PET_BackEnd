package project_pet_backEnd.productMall.mall.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import project_pet_backEnd.productMall.mall.dto.GetAllMall;
import project_pet_backEnd.productMall.mall.dto.MallQueryParameter;
import project_pet_backEnd.productMall.mall.dto.ProductPage;
import project_pet_backEnd.productMall.mall.service.MallService;
import project_pet_backEnd.userManager.dto.Sort;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;


@RestController
@Validated
public class MallController {

    @Autowired
    MallService mallService;

    /*
     * 瀏覽商城
     * @param search
     * @param sort
     * @param limit
     * @param offset
     * @return
     */
    @ApiOperation("瀏覽商城")
    @GetMapping("/customer/mall")
    public ResponseEntity<ResultResponse<Page<List<GetAllMall>>>> getMallProducts(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sort", required = false, defaultValue = "desc") Sort sort,
            @RequestParam(value = "limit", defaultValue = "10") @Max(50) @Min(0) Integer limit,
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset) {
        MallQueryParameter mallQueryParameter = new MallQueryParameter();
        mallQueryParameter.setSearch(search);
        mallQueryParameter.setSort(sort);
        mallQueryParameter.setLimit(limit);
        mallQueryParameter.setOffset(offset);

        Page<List<GetAllMall>> list = mallService.getMallProducts(mallQueryParameter);
        ResultResponse rs = new ResultResponse<>();
        rs.setMessage(list);
        return ResponseEntity.status(200).body(rs);
    }


    @ApiOperation("瀏覽單一商品")
    @GetMapping("/customer/mallproduct")
    public ResultResponse<ProductPage> getProductPage(
            @RequestParam(value = "pdNo") Integer pdNo){
        return mallService.getProductPage(pdNo);
    }
}
