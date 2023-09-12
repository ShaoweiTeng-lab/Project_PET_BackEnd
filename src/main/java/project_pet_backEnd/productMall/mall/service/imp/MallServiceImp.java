package project_pet_backEnd.productMall.mall.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.productMall.mall.dao.MallDao;
import project_pet_backEnd.productMall.productsmanage.dao.ProductPicRepository;
import project_pet_backEnd.productMall.mall.dto.GetAllMall;
import project_pet_backEnd.productMall.mall.dto.MallQueryParameter;
import project_pet_backEnd.productMall.mall.dto.ProductPage;
import project_pet_backEnd.productMall.mall.service.MallService;
import project_pet_backEnd.productMall.productsmanage.dao.ProductRepository;
import project_pet_backEnd.productMall.productsmanage.vo.Product;
import project_pet_backEnd.productMall.productsmanage.vo.ProductPic;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.ArrayList;

import java.util.List;


@Service
public class MallServiceImp implements MallService {
    @Autowired
    MallDao mallDao;

    @Autowired
    ProductPicRepository productPicRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public Page<List<GetAllMall>> getMallProducts(MallQueryParameter mallQueryParameter) {
        List<GetAllMall> allMall = mallDao.getMallProducts(mallQueryParameter);

        Page page = new Page<>();
        page.setLimit(mallQueryParameter.getLimit());
        page.setOffset(mallQueryParameter.getOffset());

        Integer total = mallDao.countMallProducts(mallQueryParameter);
        page.setTotal(total);
        page.setRs(allMall);
        return page;
    }

    @Override
    public ResultResponse<ProductPage> getProductPage(Integer pdNo) {
        Product product = productRepository.findByPdNo(pdNo);

        if (product == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "無此商品。");

        List<ProductPic> picList = productPicRepository.findByPdNo(pdNo);

        if (picList == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "此商品尚未上傳任何圖片。");

        ProductPage productPage = new ProductPage();
        productPage.setPdNo(product.getPdNo());
        productPage.setPdName(product.getPdName());
        productPage.setPdPrice(product.getPdPrice());
        productPage.setPdDescription(product.getPdDescription());
        //圖片轉Base64 - > 放入List
        List<String> pics = new ArrayList<>();
        for (ProductPic productPic : picList) {

            pics.add(AllDogCatUtils.base64Encode(productPic.getPdPic()));
        }
        productPage.setBase64Image(pics);
        ResultResponse<ProductPage> rs = new ResultResponse<ProductPage>();
        rs.setMessage(productPage);
        return rs;
    }
}
