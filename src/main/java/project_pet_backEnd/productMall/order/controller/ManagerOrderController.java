package project_pet_backEnd.productMall.order.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.productMall.order.dto.ChangeOrderStatusDTO;
import project_pet_backEnd.productMall.order.dto.response.AllOrdersResDTO;
import project_pet_backEnd.productMall.order.dto.response.OrderResDTO;
import project_pet_backEnd.productMall.order.service.OrdersService;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Api(tags = "後台商城管理員相關功能")
@Validated
@RestController
@RequestMapping("/manager")
public class ManagerOrderController {

    // eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiZXhwIjoxNjkzMzA3NzExfQ.kat8gUm1VuFR_mVZIEpALJkbTbeafSpGr6QHgeZhCq8
    // 測試帳號 帳密1~9

    @Autowired
    OrdersService ordersService;

    @ApiOperation(value = "null", notes = "後臺管理員查詢所有訂單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token",
                    required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('商品管理')")
    @GetMapping("/getAllOrders")
    public ResponseEntity<ResultResponse<List<AllOrdersResDTO>>> getAllOrders(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "rows", required = false, defaultValue = "10") Integer rows){
        Pageable pageable = PageRequest.of(page, rows);
//      Pageable pageable = PageRequest.of(page, rows, Sort.by("id"));
        ResultResponse rs = new ResultResponse();
        rs.setMessage(ordersService.getAllOrders(pageable));
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

    @ApiOperation(value = "ordNo", notes = "後台管理員查詢該筆訂單詳情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token",
                    required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('商品管理')")
    @GetMapping("/getOrders/{ordNo}")
    public ResponseEntity<ResultResponse<List<OrderResDTO>>> manGetOrderDetailByOrdNo(
            @PathVariable @Min(value = 1, message = "ordNo must be greater than or equal to 1")Integer ordNo){
        ResultResponse rs = new ResultResponse();
        rs.setMessage(ordersService.getOrderDetailByOrdNo(ordNo));
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

    @ApiOperation(value = "ordNo", notes = "後台管理員刪除已取消的訂單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token",
                    required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('商品管理')")
    @DeleteMapping("/deleteOrders/{ordNo}")
    public ResponseEntity<ResultResponse<String>> deleteByOrdNo(@PathVariable @Min(0) Integer ordNo){
        ResultResponse rs = new ResultResponse();
        ordersService.deleteByOrdNo(ordNo);
        rs.setMessage("刪除成功");
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }


    @ApiOperation(value = "ordNo", notes = "後台管理員修改訂單資訊")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token",
                    required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAnyAuthority('商品管理')")
    @PatchMapping("/patchOrders")
    public ResponseEntity<ResultResponse<String>> manUpdateOrderStatus(@RequestBody @Valid ChangeOrderStatusDTO changeOrderStatusDTO){
        ResultResponse rs = new ResultResponse<>();
        ordersService.updateOrderContent(changeOrderStatusDTO);
        rs.setMessage("修改成功");
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }
}
