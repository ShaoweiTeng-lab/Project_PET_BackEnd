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
import project_pet_backEnd.productMall.order.dto.ChangeOrderStatusDTO;
import project_pet_backEnd.productMall.order.dto.CreateOrderDTO;
import project_pet_backEnd.productMall.order.dto.response.OrderResDTO;
import project_pet_backEnd.productMall.order.service.OrdersService;
import project_pet_backEnd.productMall.order.vo.Orders;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

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
    @PostMapping(value = "/order",consumes = {"application/json"})
    public ResponseEntity<ResultResponse<String>> postOrders(@RequestBody @Valid CreateOrderDTO createOrderDTO){
        ResultResponse rs = new ResultResponse();
        ordersService.createOrders(createOrderDTO);
        rs.setMessage("新增成功");
        return ResponseEntity.status(HttpStatus.OK).body(rs);

    }

    @ApiOperation(value = "傳入userId", notes = "查詢該位會員未出貨訂單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token",
                    required = true, dataType = "string", paramType = "header")})
    @GetMapping("/getUserOrders/{userId}")
    public ResponseEntity<ResultResponse<List<Orders>>> getByUserIdAndOrdStatusNot(@RequestAttribute(name = "userId") Integer userId){
        ResultResponse rs = new ResultResponse();
        rs.setMessage(ordersService.getByUserIdAndOrdStatusNot(userId));
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

    @ApiOperation(value = "ordNo", notes = "會員查詢該筆訂單詳細資料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token",
                    required = true, dataType = "string", paramType = "header")})
    @GetMapping("/order/{ordNo}")
    public ResponseEntity<ResultResponse<List<OrderResDTO>>> getOrderDetailByOrdNo(
            @PathVariable @Min(value = 1, message = "ordNo must be greater than or equal to 1")Integer ordNo){
        ResultResponse rs = new ResultResponse();
        rs.setMessage(ordersService.getOrderDetailByOrdNo(ordNo));
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

    @ApiOperation(value = "傳入ordNo",notes = "前台會員取消該筆訂單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token",
                    required = true, dataType = "string", paramType = "header")})
    @PatchMapping("/updateUserOrders")
    public ResponseEntity<ResultResponse<String>> updateOrderStatus(@RequestBody @Valid ChangeOrderStatusDTO changeOrderStatusDTO){
        ordersService.updateOrderStatus(changeOrderStatusDTO.getOrdNo(), changeOrderStatusDTO.getOrdStatus());
        ResultResponse rs = new ResultResponse();
        rs.setMessage("刪除成功!");
        return ResponseEntity.status(200).body(rs);
    }
}
