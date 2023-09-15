package project_pet_backEnd.productMall.productcollection.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import project_pet_backEnd.productMall.productcollection.dto.EditProductCollect;
import project_pet_backEnd.productMall.productcollection.dto.ProductCollectList;
import project_pet_backEnd.productMall.productcollection.service.ProductCollectService;

import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.constraints.NotNull;
import java.util.List;


@Api(tags = "前台會員商品收藏功能")
@RestController
@Validated
@RequestMapping("/user")
public class ProductCollectController {

    @Autowired
    ProductCollectService productCollectService;


    // 新增商品收藏
    @ApiOperation("新增商品收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token",
                    required = true, dataType = "string", paramType = "header")})
    @PostMapping("/addproductcollect")
    public ResponseEntity<ResultResponse<String>> insertProductCollect(
            @RequestParam @NotNull Integer pdNo,
            @RequestAttribute(name = "userId") Integer userId
    ){
        EditProductCollect editProductCollect = new EditProductCollect();
        editProductCollect.setPdNo(pdNo);
        editProductCollect.setUserId(userId);

        ResultResponse<String> rs = productCollectService.createProductCollect(editProductCollect);
        return ResponseEntity.status(200).body(rs);
    }

    // 刪除商品收藏
    @ApiOperation("移除商品收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token",
                    required = true, dataType = "string", paramType = "header")})
    @DeleteMapping("/deleteproductcollect")
    public ResponseEntity<ResultResponse<String>> deleteProductCollect(
            @RequestAttribute(name = "userId") Integer userId,
            @RequestParam(value = "pdNo") Integer pdNo) {
        ResultResponse<String> rs = productCollectService.deleteProductCollect(userId, pdNo);
        return ResponseEntity.status(200).body(rs);
    }

    // 瀏覽商品收藏
    @ApiOperation("瀏覽商品收藏列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token",
                    required = true, dataType = "string", paramType = "header")})
    @GetMapping("/productcollectlist")
    public ResponseEntity<ResultResponse<List<ProductCollectList>>> getAllCollect(
            @RequestAttribute(name = "userId") Integer userId){
        List<ProductCollectList> pclist = productCollectService.getAllCollect(userId);
        ResultResponse<List<ProductCollectList>> rs = new ResultResponse<>();
        rs.setMessage(pclist);
        return ResponseEntity.status(200).body(rs);
    }

}


