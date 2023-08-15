package project_pet_backEnd.productMall.productsmanage.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project_pet_backEnd.productMall.productsmanage.dao.ProductsManageDao;
import project_pet_backEnd.productMall.productsmanage.dao.imp.ProductsManageDaoImp;
import project_pet_backEnd.productMall.productsmanage.dto.CreateProductRequest;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.Valid;

@RestController
@Validated
@PreAuthorize("hasAnyAuthority('商品管理')")
@RequestMapping("/manager")
public class ProductsManageController {
    @Autowired
    ProductsManageDaoImp productsManageDaoImp;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/createProduct")
    public ResponseEntity<ResultResponse<String>> insertProduct(@RequestBody @Valid CreateProductRequest createProductRequest){
        productsManageDaoImp.insertProduct(createProductRequest);
        ResultResponse rs =new ResultResponse();
        rs.setMessage("新增成功");
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

}
