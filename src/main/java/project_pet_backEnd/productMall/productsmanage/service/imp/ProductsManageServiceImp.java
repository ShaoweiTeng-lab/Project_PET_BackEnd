package project_pet_backEnd.productMall.productsmanage.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.productMall.productsmanage.dao.ProductPicDao;
import project_pet_backEnd.productMall.productsmanage.dao.ProductRepository;
import project_pet_backEnd.productMall.productsmanage.dao.ProductsManageDao;
import project_pet_backEnd.productMall.productsmanage.dto.*;
import project_pet_backEnd.productMall.productsmanage.service.ProductsManageService;
import project_pet_backEnd.productMall.productsmanage.vo.Product;
import project_pet_backEnd.productMall.productsmanage.vo.ProductPic;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductsManageServiceImp implements ProductsManageService {

    @Autowired
    private ProductsManageDao productsManageDao;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPicDao productPicDao;


    @Override  //ok後台 查看全部商品列表
    public List<ProductListResponse> getAllProductsForMan() {
        List<Product> pdlist = new ArrayList<>();
        pdlist = productRepository.findAll();
        List<ProductListResponse> rsList =new ArrayList<>();
        for(int i = 0; i < pdlist.size(); i++){
            Product pd = pdlist.get(i);
            ProductListResponse productListResponse=new ProductListResponse();
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
        List<ProductListResponse> rsList =new ArrayList<>();
        for(int i = 0; i < pdlist.size(); i++){
            Product pd = pdlist.get(i);
            ProductListResponse productListResponse=new ProductListResponse();
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

    @Override  //ok後台 修改商品列表狀態
//    更新成功
    public ResultResponse<String> updateProductStatus(List<AdjustProductListResponse> adjustProductListResponse) {
        ResultResponse<String> rs = new ResultResponse<>();
            productsManageDao.batchupdateproductstatusByPdNo(adjustProductListResponse);
            rs.setMessage("更新成功");
            return rs;
    }

    @Override  //ok後台 查看編輯商品(資訊(名稱、價錢、狀態、說明)+圖片)
    public  List<Map<String, Object>> getProduct(ProductInfo productInfo, List<ProductPic> pics) {
        List<Product> allProduct = productRepository.findAll();
        List<Map<String, Object>> pdinfoList = new ArrayList<>();
        for (Product product : allProduct) {
            Map<String, Object> productData = new HashMap<>();
            productData.put("pdName", product.getPdName());
            productData.put("pdPrice", product.getPdPrice());
            productData.put("pdStatus", product.getPdStatus());
            productData.put("pdDescription", product.getPdDescription());

            List<ProductPic> relatedPics = productPicDao.getAllProductPic(product.getPdNo());
            List<String> base64EncodedPics = new ArrayList<>();

            for (ProductPic pic : relatedPics) {
                String base64EncodedPic = AllDogCatUtils.base64Encode(pic.getPdPic());
                base64EncodedPics.add(base64EncodedPic);
            }

            productData.put("pdPics", base64EncodedPics);
            pdinfoList.add(productData);
        }
        return pdinfoList;
    }

    @Override
    @Transactional  //ok後台 修改編輯商品(資訊+圖片)
    // 更新成功 | *!=null
    public ResultResponse updateProduct(ProductPicData productPicData, List<ProductPic> pics) {
        Product product = new Product();
        if (!productPicData.getPdName().isBlank())
            product.setPdName(productPicData.getPdName());
        if (productPicData.getPdPrice() != null)
            product.setPdPrice(productPicData.getPdPrice());

        product.setPdStatus(productPicData.getPdStatus());
        product.setPdDescription(productPicData.getPdDescription());

        try {
            productRepository.save(product); // 先保存商品，獲取商品編號
            for (ProductPic pic : pics) {
                pic.setPdNo(product.getPdNo()); // 關聯商品編號
            }
            productPicDao.batchupdateproductPicByPdNo(pics); // 執行批次修改圖片
            ResultResponse<String> rs = new ResultResponse<>();
            rs.setMessage("新增成功");
            return rs;

        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "更新失敗，請稍後重試", e);
        }
    }

    @Transactional
    @Override  //ok後台 新增商品(資訊+圖片)
    //    新增成功 | *!=null
    public ResultResponse insertProduct(ProductInfo productInfo, List<ProductPic> pics) {
        // 1.創建商品資訊
        Product product = new Product();
        if (!productInfo.getPdName().isBlank())
            product.setPdName(productInfo.getPdName());
        if (productInfo.getPdPrice() != null)
            product.setPdPrice(productInfo.getPdPrice());

        product.setPdStatus(productInfo.getPdStatus());
        product.setPdDescription(productInfo.getPdDescription());

        // 2.上傳圖片並關聯商品 3.(驗證)
        try {
            productRepository.save(product); // 先保存商品，獲取商品編號
            for (ProductPic pic : pics) {
                pic.setPdNo(product.getPdNo()); // 關聯商品編號
            }
            productPicDao.batchinsertProductPic(pics); // 執行批次插入圖片
            ResultResponse<String> rs = new ResultResponse<>();
            rs.setMessage("新增成功");
            return rs;

        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "新增失敗，請稍後重試", e);
        }
    }
}




