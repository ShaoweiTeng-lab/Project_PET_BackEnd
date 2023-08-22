package project_pet_backEnd.productMall.order.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project_pet_backEnd.productMall.order.dto.CreateOrderDTO;
import project_pet_backEnd.productMall.order.service.OrdersService;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.Valid;

@Api(tags="前台會員訂單相關功能")
@Validated
@RestController
@RequestMapping("/user")
public class UserOrderController {

    @Autowired
    OrdersService ordersService;
    @ApiOperation(value = "傳入CreateOrderDTO", notes = "新增單筆訂單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token",
                    required = true, dataType = "string", paramType = "header")})
    @PostMapping(value = "/orders",consumes = {"application/json"})
    public ResponseEntity<ResultResponse<String>> postOrders(@RequestBody @Valid CreateOrderDTO createOrderDTO){
        ResultResponse rs = new ResultResponse();
        try {
            ordersService.createOrders(createOrderDTO);
            rs.setMessage("新增成功");
            return ResponseEntity.status(HttpStatus.OK).body(rs);
        }catch (Exception e){
            rs.setMessage("新增失敗");
            return ResponseEntity.status(HttpStatus.OK).body(rs);
        }

    }
}
