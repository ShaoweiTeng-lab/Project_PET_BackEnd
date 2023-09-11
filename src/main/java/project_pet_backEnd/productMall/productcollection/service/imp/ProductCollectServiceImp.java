package project_pet_backEnd.productMall.productcollection.service.imp;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.productMall.productcollection.dao.ProductCollectDao;

import project_pet_backEnd.productMall.productcollection.dao.ProductCollectRepository;
import project_pet_backEnd.productMall.productcollection.dto.EditProductCollect;
import project_pet_backEnd.productMall.productcollection.dto.ProductCollectList;
import project_pet_backEnd.productMall.productcollection.service.ProductCollectService;
import project_pet_backEnd.productMall.productcollection.vo.ProductCollect;
import project_pet_backEnd.productMall.productcollection.vo.ProductCollectPk;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.sql.Date;
import java.util.*;

@Service
public class ProductCollectServiceImp implements ProductCollectService {

    @Autowired
    ProductCollectDao productCollectDao;

    @Autowired
    ProductCollectRepository productCollectRepository;

    @Override  //新增商品收藏
    public ResultResponse createProductCollect(EditProductCollect editProductCollect) {
        try {
            ProductCollectPk primaryKey = new ProductCollectPk();
            primaryKey.setUserId(editProductCollect.getUserId());
            primaryKey.setPdNo(editProductCollect.getPdNo());

            // 創建 java.util.Date 對象並轉換為 java.sql.Date
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            ProductCollect productCollect = new ProductCollect();
            productCollect.setId(primaryKey);
            productCollect.setPdcCreated(sqlDate);

            productCollectRepository.save(productCollect);
            ResultResponse rs = new ResultResponse<>();
            rs.setMessage("新增成功");
            return rs;
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "新增失敗，請稍後重試", e);
        }
    }

    @Override  // 刪除商品收藏
    public ResultResponse deleteProductCollect(Integer userId, Integer pdNo) {
        try {
            ProductCollectPk primaryKey = new ProductCollectPk();
            primaryKey.setUserId(userId);
            primaryKey.setPdNo(pdNo);

            Optional<ProductCollect> existingCollect = productCollectRepository.findById(primaryKey);

            if (existingCollect.isPresent()) {
                productCollectRepository.delete(existingCollect.get());
                ResultResponse rs = new ResultResponse<>();
                rs.setMessage("刪除成功");
                return rs;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "未找到要刪除的商品收藏紀錄");
            }
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "刪除失敗，請稍後重試", e);
        }
    }

    //    @Override  // 瀏覽商品收藏
    public List<ProductCollectList> getAllCollect(Integer userId) {
        return productCollectDao.getAllCollect(userId);
    }
}
