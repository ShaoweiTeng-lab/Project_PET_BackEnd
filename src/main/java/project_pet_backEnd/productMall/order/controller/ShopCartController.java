package project_pet_backEnd.productMall.order.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.productMall.order.dto.CartItemDTO;
import project_pet_backEnd.productMall.order.service.ShopCartService;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.constraints.Min;
import java.util.List;

@Api(tags = "前台購物車相關功能")
@Validated
@RestController
@RequestMapping("/user")
public class ShopCartController {

    @Autowired
    ShopCartService shopCartService;

    @ApiOperation(value = "", notes = "更新(數量)商品至購物車")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token",
                    required = true, dataType = "string", paramType = "header")})
    @PostMapping("/addProduct")
    public ResponseEntity<ResultResponse<String>> changCartAmount(@RequestAttribute(name = "userId") @Min(0) Integer shoppingCart_userId,
                                                            @RequestParam Integer pdNo,
                                                            @RequestParam Integer quantity){

        ResultResponse rs = new ResultResponse<>();
        System.out.println(shoppingCart_userId);
        shopCartService.changCartAmount(shoppingCart_userId, pdNo, quantity);
        rs.setMessage("成功新增至購物車!");
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }


    @ApiOperation(value = "userId", notes = "取得該位使用者的購物車清單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token",
                    required = true, dataType = "string", paramType = "header")})
    @GetMapping("/getShopCart")
    public ResponseEntity<ResultResponse<List<CartItemDTO>>> getCart(@RequestAttribute(name = "userId") @Min(0) Integer shoppingCart_userId){
        ResultResponse rs = new ResultResponse();
        rs.setMessage(shopCartService.getCart(shoppingCart_userId));
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

    @ApiOperation(value = "userId", notes = "刪除該位購物車清單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token",
                    required = true, dataType = "string", paramType = "header")})
    @DeleteMapping("/deleteShopCart")
    public ResponseEntity<ResultResponse<String>> deleteShopCart(@RequestAttribute(name = "userId") Integer shoppingCart_userId){
        System.out.println(shoppingCart_userId);
        ResultResponse rs = new ResultResponse();
        shopCartService.deleteCart(shoppingCart_userId);
        rs.setMessage("購物車清除成功!");
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }
}
