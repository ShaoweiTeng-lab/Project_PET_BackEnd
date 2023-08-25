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
import project_pet_backEnd.productMall.productsmanage.dto.AdjustProductListResponse;
import project_pet_backEnd.productMall.productsmanage.dto.ProductInfo;
import project_pet_backEnd.productMall.productsmanage.dto.ProductListQueryParameter;
import project_pet_backEnd.productMall.productsmanage.dto.ProductListResponse;
import project_pet_backEnd.productMall.productsmanage.service.ProductsManageService;
import project_pet_backEnd.productMall.productsmanage.vo.ProductPic;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.Valid;
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

    //ok後台 查看全部商品列表
    @ApiOperation("商品管理員查看商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/getallProduct")
    public ResponseEntity<List<ProductListResponse>> getAllProductsForMan() {
        List<ProductListResponse> productList = productsManageService.getAllProductsForMan();
        return ResponseEntity.ok(productList);
    }

//    [這裡不知道要怎麼測試搜尋關鍵字]後台 商品列表查詢
    @ApiOperation("商品管理員查詢商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/queryProduct/{pdStatus}")
    public ResponseEntity<Page<List<ProductListResponse>>> getAllProductsWithSearch(@RequestBody ProductListQueryParameter productListQueryParameter){
        Page<List<ProductListResponse>> productListPage = productsManageService.getAllProductsWithSearch(productListQueryParameter);
        return ResponseEntity.ok(productListPage);
    }


    //ok!!後台 修改商品列表狀態
    @ApiOperation("商品管理員修改商品狀態")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/updateProduct")
    public ResponseEntity<ResultResponse<String>> updateProductStatus(@RequestBody List<AdjustProductListResponse> adjustProductListResponse) {
        ResultResponse<String> result = productsManageService.updateProductStatus(adjustProductListResponse);
        return ResponseEntity.ok(result);
    }

    //[這裡無法測試要請紹偉幫忙用另一種寫法QQ]後台 查看編輯商品(資訊(名稱、價錢、規格、狀態、說明)+圖片)
    @ApiOperation("商品管理員查看編輯商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/getProduct")
   public ResponseEntity<List<Map<String, Object>>> getProduct (ProductInfo productInfo, List<ProductPic> pics) {
        List<Map<String, Object>> productList = productsManageService.getProduct(productInfo, pics);
        return ResponseEntity.ok(productList);
    }

    //[這裡無法測試要請紹偉幫忙用另一種寫法QQ]後台 修改編輯商品(資訊+圖片)
    @ApiOperation("商品管理員修改編輯商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping("/updateProduct")
     public ResponseEntity<ResultResponse> updateProduct(@RequestBody @Valid ProductInfo productInfo, @RequestParam("pics") List<MultipartFile> picFiles) {
        ResultResponse rs =new ResultResponse();
        List<ProductPic> pics = new ArrayList<>();

        // 將上傳的圖片轉換成 byte[] 格式並添加到商品圖片列表中
        for (MultipartFile picFile : picFiles) {
            byte[] picData = convertMultipartFileToByteArray(picFile);
            if (picData != null) {
                ProductPic pic = new ProductPic();
                pic.setPdPic(picData);
                pics.add(pic);
            }
        }
        productsManageService.updateProduct(productInfo, pics);
        rs.setMessage("更新成功");
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }


    //[這裡無法測試要請紹偉幫忙用另一種寫法QQ]後台 新增商品(資訊+圖片)
    @ApiOperation("商品管理員新增商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_M", value = "Manager Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/createProduct")
    public ResponseEntity<ResultResponse<String>> insertProduct(@RequestBody @Valid ProductInfo productInfo, @RequestParam("pics") List<MultipartFile> picFiles){
        ResultResponse rs =new ResultResponse();
        List<ProductPic> pics = new ArrayList<>();

        // 將上傳的圖片轉換成 byte[] 格式並添加到商品圖片列表中
        for (MultipartFile picFile : picFiles) {
            byte[] picData = convertMultipartFileToByteArray(picFile);
            if (picData != null) {
                ProductPic pic = new ProductPic();
                pic.setPdPic(picData);
                pics.add(pic);
            }
        }
        productsManageService.insertProduct(productInfo, pics);
        rs.setMessage("新增成功");
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

}
