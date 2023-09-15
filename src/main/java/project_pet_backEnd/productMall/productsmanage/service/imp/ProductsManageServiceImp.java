package project_pet_backEnd.productMall.productsmanage.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.productMall.productsmanage.dao.ProductPicDao;
import project_pet_backEnd.productMall.productsmanage.dao.ProductPicRepository;
import project_pet_backEnd.productMall.productsmanage.dao.ProductRepository;
import project_pet_backEnd.productMall.productsmanage.dao.ProductsManageDao;
import project_pet_backEnd.productMall.productsmanage.dto.*;
import project_pet_backEnd.productMall.productsmanage.service.ProductsManageService;
import project_pet_backEnd.productMall.productsmanage.vo.Product;
import project_pet_backEnd.productMall.productsmanage.vo.ProductPic;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.*;

@Service
public class ProductsManageServiceImp implements ProductsManageService {

    @Autowired
    private ProductsManageDao productsManageDao;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPicRepository productPicRepository;

    @Autowired
    private ProductPicDao productPicDao;


    @Override  //ok後台 查看全部商品列表
    public List<ProductListResponse> getAllProductsForMan() {
        List<Product> pdlist = new ArrayList<>();
        pdlist = productRepository.findAll();
        List<ProductListResponse> rsList = new ArrayList<>();
        for (int i = 0; i < pdlist.size(); i++) {
            Product pd = pdlist.get(i);
            ProductListResponse productListResponse = new ProductListResponse();
            productListResponse.setPdNo(pd.getPdNo());
            productListResponse.setPdName(pd.getPdName());
            productListResponse.setPdPrice(pd.getPdPrice());
            productListResponse.setPdStatus(pd.getPdStatus());

            rsList.add(productListResponse);
        }
        return rsList;
    }

    @Override  //ok後台 商品列表查詢 (order by PdNo / PdName / PdStatus)
    public Page<List<ProductListResponse>> getAllProductsWithSearch(ProductListQueryParameter productListQueryParameter) {
        List<Product> pdlist = new ArrayList<>();
        pdlist = productRepository.findAll();
        List<ProductListResponse> rsList = new ArrayList<>();
        for (int i = 0; i < pdlist.size(); i++) {
            Product pd = pdlist.get(i);
            ProductListResponse productListResponse = new ProductListResponse();
            productListResponse.setPdNo(pd.getPdNo());
            productListResponse.setPdName(pd.getPdName());
            productListResponse.setPdPrice(pd.getPdPrice());
            productListResponse.setPdStatus(pd.getPdStatus());

            rsList.add(productListResponse);
        }
        Page<List<ProductListResponse>> page = new Page<>();
        page.setLimit(productListQueryParameter.getLimit());
        page.setOffset(productListQueryParameter.getOffset());
        Integer total = productsManageDao.countAllProductWithSearch(productListQueryParameter);
        page.setTotal(total);
        page.setRs(rsList);

        return page;
    }

    @Override  //後台 修改單一商品列表狀態
    public ResultResponse<String> updateoneProductStatus(AdjustProductListResponse adjustProductListResponse) {
        ResultResponse<String> rs = new ResultResponse<>();
        Product product = productRepository.findByPdNo(adjustProductListResponse.getPdNo());
        product.setPdStatus(adjustProductListResponse.getPdStatus());
        productRepository.save(product);
        rs.setMessage("更新成功");
        return rs;
    }

    @Transactional
    @Override  //ok後台 修改商品列表狀態
//    更新成功
    public ResultResponse<String> updateProductStatus(List<AdjustProductListResponse> adjustProductListResponse) {
        ResultResponse<String> rs = new ResultResponse<>();
        productsManageDao.batchupdateproductstatusByPdNo(adjustProductListResponse);
        rs.setMessage("更新成功");
        return rs;
    }

    @Override //ok後台 查看編輯商品(資訊(名稱、價錢、狀態、說明)+圖片)
    public ResultResponse<ProductRes> getProduct(Integer pdNo){
        Product product = productRepository.findByPdNo(pdNo);

        if (product == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "無此商品。");

        List<ProductPic> picList = productPicRepository.findByPdNo(pdNo);

        if (picList == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "此商品尚未上傳任何圖片。");

        ProductRes productRes = new ProductRes();
        productRes.setPdNo(product.getPdNo());
        productRes.setPdName(product.getPdName());
        productRes.setPdPrice(product.getPdPrice());
        productRes.setPdStatus(product.getPdStatus());
        productRes.setPdDescription(product.getPdDescription());

        //取出pdPicNo
        List<Integer> picNo = new ArrayList<>();
        for(ProductPic productPic : picList){
            picNo.add(productPic.getPdPicNo());
        }
        productRes.setPdPicNo(picNo);

        //取出PdOrderList
        List<Integer> picOrder = new ArrayList<>();
        for(ProductPic productPic : picList){
            picOrder.add(productPic.getPdOrderList());
        }
        productRes.setPdOrderList(picOrder);

        //圖片轉Base64 - > 放入List
        List<String> pics = new ArrayList<>();
        for (ProductPic productPic : picList) {
            pics.add(AllDogCatUtils.base64Encode(productPic.getPdPic()));
        }
        productRes.setBase64Image(pics);

        ResultResponse<ProductRes> rs = new ResultResponse<ProductRes>();
        rs.setMessage(productRes);
        return rs;
    }


    @Override
    @Transactional  //後台 修改編輯商品
    public ResultResponse updateProduct(ProductUpdate productUpdate, List<ProductPic> pics) {
        try {
//            // 檢查是否修改成以存在的商品名稱
//            if (productRepository.existsByPdName(productInfo.getPdName())) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "修改的商品名稱已存在");
//            }
            System.out.println(productUpdate);
            Product product = new Product();
            product.setPdNo(productUpdate.getPdNo());
            if (!productUpdate.getPdName().isBlank())
                product.setPdName(productUpdate.getPdName());
            if (productUpdate.getPdPrice() != null)
                product.setPdPrice(productUpdate.getPdPrice());

            product.setPdStatus(productUpdate.getPdStatus());
            product.setPdDescription(productUpdate.getPdDescription());


            productRepository.save(product); // 先保存商品，獲取商品編號

            for (ProductPic pic : pics) {
                pic.setPdNo(product.getPdNo()); // 關聯商品編號
                pic.setPdPicNo(productUpdate.getPdPicNo());
                }


            productPicDao.batchupdateproductPicByPdNo(pics); // 執行批次修改圖片
            ResultResponse<String> rs = new ResultResponse<>();
            rs.setMessage("修改成功");
            return rs;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "更新失敗，請稍後重試", e);
        }
    }

    @Transactional
    @Override  //ok後台 新增商品(資訊+圖片)
    //    新增成功 | *!=null
    public ResultResponse insertProduct(ProductInfo productInfo, List<ProductPic> pics) {
        try {
            // 檢查是否重覆新增商品(商品名)
            if (productRepository.existsByPdName(productInfo.getPdName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "商品名稱已存在");
            }

            // 1.創建商品資訊
            Product product = new Product();
            if (!productInfo.getPdName().isBlank())
                product.setPdName(productInfo.getPdName());
            if (productInfo.getPdPrice() != null)
                product.setPdPrice(productInfo.getPdPrice());

            product.setPdStatus(productInfo.getPdStatus());
            product.setPdDescription(productInfo.getPdDescription());

            // 2.上傳圖片並關聯商品 3.(驗證)

            productRepository.save(product); // 先保存商品，獲取商品編號
            for (ProductPic pic : pics) {
                pic.setPdNo(product.getPdNo()); // 關聯商品編號
            }
            productPicDao.batchinsertProductPic(pics); // 執行批次插入圖片
            ResultResponse<String> rs = new ResultResponse<>();
            rs.setMessage("新增成功");
            return rs;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "新增失敗，請檢查是否有空值", e);
        }
    }

    @Override  //後台 編輯時新增商品圖片
    public ResultResponse insertProductPic(Integer pdNo, ProductPic productPic) {

        List<ProductPic> existingPics = productPicRepository.findByPdNo(pdNo);
        int picOrder = existingPics.size() + 1;
        productPic.setPdOrderList(picOrder);

        productPicRepository.save(productPic);

        ResultResponse<String> rs = new ResultResponse<>();
        rs.setMessage("新增成功");
        return rs;
    }

    @Transactional
    @Override  //後台 編輯時刪除商品圖片
    public ResultResponse deleteProductPic(Integer pdPicNo) {
        List<ProductPic> picList = productPicRepository.deleteByPdPicNo(pdPicNo);

        ResultResponse<String> rs = new ResultResponse<>();
        rs.setMessage("刪除成功");
        return rs;
    }



    @Transactional
    @Override  //後台 編輯時更換商品圖片
    public ResultResponse changeProductPic(Integer pdNo, Integer pdPicNo, Integer pdOrderList, ProductPic productPic) {
        List<ProductPic> picList = productPicRepository.findByPdPicNo(pdPicNo);
//        productPic.setPdNo(pdNo);
        productPicRepository.save(productPic);
        System.out.println(productPic);
        ResultResponse<String> rs = new ResultResponse<>();
        rs.setMessage("更換成功");
        return rs;
    }


}





