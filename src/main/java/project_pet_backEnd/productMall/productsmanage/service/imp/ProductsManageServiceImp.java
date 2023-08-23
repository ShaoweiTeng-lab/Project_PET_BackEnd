package project_pet_backEnd.productMall.productsmanage.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project_pet_backEnd.productMall.productsmanage.dao.ProductPicDao;
import project_pet_backEnd.productMall.productsmanage.dao.ProductRepository;
import project_pet_backEnd.productMall.productsmanage.dao.ProductsManageDao;
import project_pet_backEnd.productMall.productsmanage.dto.AdjustProductListResponse;
import project_pet_backEnd.productMall.productsmanage.dto.ProductInfo;
import project_pet_backEnd.productMall.productsmanage.dto.ProductListQueryParameter;
import project_pet_backEnd.productMall.productsmanage.dto.ProductListResponse;
import project_pet_backEnd.productMall.productsmanage.service.ProductsManageService;
import project_pet_backEnd.productMall.productsmanage.vo.Product;
import project_pet_backEnd.productMall.productsmanage.vo.ProductPic;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.ArrayList;
import java.util.List;

@Service     // 1.設條件 2.呼叫方法  throw exception rollback
public class ProductsManageServiceImp implements ProductsManageService {

    @Autowired
    private ProductsManageDao productsManageDao;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPicDao productPicDao;


    @Override  //後台 查看全部商品列表
    public Page<List<ProductListResponse>> getAllProductsForMan(ProductListQueryParameter productListQueryParameter) {
//        List<ProductListResponse> allProductsList = productsManageDao.getAllProductsWithSearch(ProductListQueryParameter);
//        List<ProductListResponse> rsList = new ArrayList<>();
//        if (allGroomersList == null || allGroomersList.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "找不到寵物美容師");
//        }
//        for (GetAllGroomers groomers : allGroomersList) {
//            GetAllGroomerListSortRes getAllGroomerListSortRes = new GetAllGroomerListSortRes();
//            getAllGroomerListSortRes.setManId(groomers.getManId());
//            getAllGroomerListSortRes.setPgId(groomers.getPgId());
//            getAllGroomerListSortRes.setPgName(groomers.getPgName());
//            int gender = groomers.getPgGender();
//            switch (gender) {
//                case 0:
//                    getAllGroomerListSortRes.setPgGender("女性");
//                    break;
//                case 1:
//                    getAllGroomerListSortRes.setPgGender("男性");
//                    break;
//            }
//            getAllGroomerListSortRes.setPgPic(AllDogCatUtils.base64Encode(groomers.getPgPic()));
//            getAllGroomerListSortRes.setPgEmail(groomers.getPgEmail());
//            getAllGroomerListSortRes.setPgPh(groomers.getPgPh());
//            getAllGroomerListSortRes.setPgAddress(groomers.getPgAddress());
//            getAllGroomerListSortRes.setPgBirthday(AllDogCatUtils.timestampToSqlDateFormat(groomers.getPgBirthday()));
//            getAllGroomerListSortRes.setNumAppointments(groomers.getNumAppointments());
//            rsList.add(getAllGroomerListSortRes);
//        }
//        Page page = new Page<>();
//        page.setLimit(PGQueryParameter.getLimit());
//        page.setOffset(PGQueryParameter.getOffset());
//        //得到總筆數，方便實作頁數
//        Integer total = petGroomerDao.countPetGroomer(PGQueryParameter);
//        page.setTotal(total);
//        page.setRs(rsList);
//        return page;
    }

    @Override  //後台 商品列表查詢 (order by PdNo / PdName / PdStatus)
    public Page<List<ProductListResponse>> getAllProductsWithSearch(ProductListQueryParameter productListQueryParameter) {
        return null;
    }

    @Override  //後台 修改商品列表狀態
    public ResultResponse<String> updateProductStatus(List<AdjustProductListResponse> adjustProductListResponse) {
        return null;
    }

    @Override  //後台 查看編輯商品(資訊(名稱、價錢、規格、狀態、說明)+圖片)
    public List<Product> getProduct(ProductInfo productInfo, List<ProductPic> pics) {
        return null;
    }

    @Override
    @Transactional  //後台 修改編輯商品(資訊+圖片)
    public ResultResponse<String> updateProduct(ProductInfo productInfo, List<ProductPic> pics) {
        return null;
    }

    @Transactional
    @Override  //後台 新增商品(資訊+圖片)
    public ResultResponse<String> insertProduct(ProductInfo productInfo, List<ProductPic> pics) {
        // 1.創建商品資訊 2.上傳圖片 3.條件(驗證)

        // 創建商品資訊
        Product product = new Product();
        product.setPdName(productInfo.getPdName());
        product.setPdPrice(productInfo.getPdPrice());
        product.setPdFormat(productInfo.getPdFormat());
        product.setPdStatus(productInfo.getPdStatus());
        product.setPdDescription(productInfo.getPdDescription());

        productRepository.save(product);

        //上傳商品圖片(需轉換格式)
        List<ProductPic> piclist = new ArrayList<>();
        for (ProductPic pic : pics) {
            pic.getPdPic();
        }

        try {


        } catch (Exception e) {
            throw new RuntimeException();
        }
        ResultResponse rs = new ResultResponse();
        rs.setMessage("新增成功!");
        return null;  //先寫著而已
    }

}


