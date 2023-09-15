package project_pet_backEnd.productMall.productsmanage.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project_pet_backEnd.productMall.productsmanage.dto.*;
import project_pet_backEnd.productMall.productsmanage.service.ProductsManageService;
import project_pet_backEnd.productMall.productsmanage.vo.ProductPic;
import project_pet_backEnd.userManager.dto.Sort;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static project_pet_backEnd.utils.AllDogCatUtils.convertMultipartFileToByteArray;

@Api(tags = "商品管理")
@RestController
@Validated
@PreAuthorize("hasAnyAuthority('商品管理')")
@RequestMapping("/manager")
public class ProductsManageController {
    @Autowired
    ProductsManageService productsManageService;

    //後台 查看全部商品列表
    @ApiOperation("商品管理員查看商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/getallProduct")
    public ResponseEntity<ResultResponse<List<ProductListResponse>>> getAllProductsForMan() {
        List<ProductListResponse> productList = productsManageService.getAllProductsForMan();
        ResultResponse rs = new ResultResponse<>();
        rs.setMessage(productList);
        return ResponseEntity.status(200).body(rs);
    }


    // [搜尋關鍵字?]後台 商品列表查詢
    @ApiOperation("商品管理員查詢商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token",
                    required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/queryProduct")
    public ResultResponse<Page<List<ProductListResponse>>> getAllProductsWithSearch(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "orderBy", required = false, defaultValue = "PD_NO") ProductListOrderBy orderBy,
            @RequestParam(value = "sort", required = false, defaultValue = "desc") Sort sort,
            @RequestParam(value = "limit", defaultValue = "10") @Max(50) @Min(0) Integer limit,
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset) {

        ProductListQueryParameter productListQueryParameter = new ProductListQueryParameter();
        productListQueryParameter.setSearch(search);
        productListQueryParameter.setOrder(orderBy);
        productListQueryParameter.setSort(sort);
        productListQueryParameter.setLimit(limit);
        productListQueryParameter.setOffset(offset);
        Page<List<ProductListResponse>> productListPage = productsManageService.getAllProductsWithSearch(productListQueryParameter);

        ResultResponse<Page<List<ProductListResponse>>> rs = new ResultResponse<>();
        rs.setMessage(productListPage);
        return rs;
    }

    //後台 修改單一商品列表狀態
    @ApiOperation("商品管理員修改商品狀態")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/updateOneProductStatus")
    public ResponseEntity<ResultResponse<String>> updateProductStatus(@RequestBody AdjustProductListResponse adjustProductListResponse) {
        ResultResponse<String> rs = productsManageService.updateoneProductStatus(adjustProductListResponse);
        rs.setMessage("修改成功");
        return ResponseEntity.status(200).body(rs);
    }

    //後台 修改商品列表狀態
    @ApiOperation("商品管理員修改單一商品狀態")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/updateProductStatus")
    public ResponseEntity<ResultResponse<String>> updateProductStatus(@RequestBody List<AdjustProductListResponse> adjustProductListResponse) {
        ResultResponse<String> rs = productsManageService.updateProductStatus(adjustProductListResponse);
        rs.setMessage("修改成功");
        return ResponseEntity.status(200).body(rs);
    }

    //後台 查看編輯商品(資訊(名稱、價錢、規格、狀態、說明)+圖片)
    @ApiOperation("商品管理員查看編輯商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/getProduct")  //    創一個DTO傳回
    public ResultResponse<ProductRes> getProduct(
            @RequestParam(value = "pdNo") Integer pdNo) throws IOException {
        return productsManageService.getProduct(pdNo);
    }

    //後台 修改編輯商品(資訊+圖片)

    @ApiOperation("商品管理員修改編輯商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/updateProduct")                       //創一個DTO(picno、picfile)
    public ResponseEntity<ResultResponse> updateProduct(@ModelAttribute ProductUpdate productUpdate) {
        ResultResponse rs = new ResultResponse();
        List<ProductPic> pics = new ArrayList<>();
        List<MultipartFile> multipartFileList = productUpdate.getPicFiles();
        // 將上傳的圖片轉換成 byte[] 格式並添加到商品圖片列表中
        if(multipartFileList!=null){
            for (MultipartFile picFile : multipartFileList) {
                byte[] picData = convertMultipartFileToByteArray(picFile);
                if (picData != null) {
                    ProductPic pic = new ProductPic();
                    pic.setPdPic(picData);
                    pics.add(pic);
                }
            }
        }
        productsManageService.updateProduct(productUpdate, pics);
        rs.setMessage("修改成功");
        return ResponseEntity.status(200).body(rs);
    }


    //後台 新增商品(資訊+圖片)
    @ApiOperation("商品管理員新增商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/createProduct")
    public ResponseEntity<ResultResponse<String>> insertProduct(@ModelAttribute ProductInfo productInfo) {
        ResultResponse rs = new ResultResponse();
        List<ProductPic> pics = new ArrayList<>();
        List<MultipartFile> multipartFileList = productInfo.getPicFiles();
        // 將上傳的圖片轉換成 byte[] 格式並添加到商品圖片列表中
        int orderIndex = 1;
        for (MultipartFile picFile : multipartFileList) {
            byte[] picData = convertMultipartFileToByteArray(picFile);
            if (picData != null) {
                ProductPic pic = new ProductPic();
                pic.setPdPic(picData);
                pics.add(pic);
                pic.setPdOrderList(orderIndex);
                orderIndex++;
            }
        }
        productsManageService.insertProduct(productInfo, pics);
        rs.setMessage("新增成功");
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

    //後台 新增商品圖片
    @ApiOperation("商品管理員新增商品圖片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/createPic")
    public ResponseEntity<ResultResponse> insertProductPic(
            @RequestParam Integer pdNo,
            @RequestParam("pdPic") MultipartFile pdPic) throws IOException {

        ResultResponse rs = new ResultResponse();

        // 將上傳的圖片轉換成 byte[] 格式
        byte[] pic = AllDogCatUtils.convertMultipartFileToByteArray(pdPic);

        ProductPic productPic = new ProductPic();
        productPic.setPdNo(pdNo);
        productPic.setPdPic(pic);

        productsManageService.insertProductPic(pdNo, productPic);

        rs.setMessage("新增成功");
        return ResponseEntity.status(200).body(rs);

    }

    //後台刪除商品圖片
    @ApiOperation("商品管理員刪除商品圖片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/deletePic")
    public ResponseEntity<ResultResponse> deleteProductPic(
            @RequestParam Integer pdPicNo) {
        ResultResponse rs = new ResultResponse();

        productsManageService.deleteProductPic(pdPicNo);
        rs.setMessage("刪除成功");
        return ResponseEntity.status(200).body(rs);
    }

    //後台 更換商品圖片
    @ApiOperation("商品管理員更換商品圖片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/changePic")
    public ResponseEntity<ResultResponse> changeProductPic(
            @RequestParam Integer pdNo,
            @RequestParam Integer pdPicNo,
            @RequestParam Integer pdOrderList,
            @RequestParam("pdPic") MultipartFile pdPic) throws IOException {

        ResultResponse rs = new ResultResponse();

        // 將上傳的圖片轉換成 byte[] 格式
        byte[] pic = AllDogCatUtils.convertMultipartFileToByteArray(pdPic);

        ProductPic productPic = new ProductPic();
        productPic.setPdNo(pdNo);
        productPic.setPdPicNo(pdPicNo);
        productPic.setPdOrderList(pdOrderList);
        productPic.setPdPic(pic);
        productsManageService.changeProductPic(pdNo, pdPicNo, pdOrderList, productPic);

        rs.setMessage("更換成功");
        return ResponseEntity.status(200).body(rs);

    }
}
