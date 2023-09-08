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

import java.util.*;

@Service
public class ProductCollectServiceImp implements ProductCollectService {

    @Autowired
    ProductCollectDao productCollectDao;

    @Autowired
    ProductCollectRepository productCollectRepository;


//    @Override  // 新增商品收藏
//    public ResultResponse insertProductCollect(EditProductCollect editProductCollect) {
//        try {
//            productCollectDao.insertProductCollect(editProductCollect);
//            ResultResponse rs = new ResultResponse<>();
//            rs.setMessage("新增成功");
//            return rs;
//        } catch (DataAccessException e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "新增失敗，請稍後重試", e);
//        }
//    }



    @Override  //新增商品收藏
    public ResultResponse createProductCollect(EditProductCollect editProductCollect) {
        try {
            ProductCollectPk primaryKey = new ProductCollectPk();
            primaryKey.setUserId(editProductCollect.getUserId());
            primaryKey.setPdNo(editProductCollect.getPdNo());

            ProductCollect productCollect = new ProductCollect();
            productCollect.setId(primaryKey);

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

    @Override  // 瀏覽商品收藏
    public List<Map<String, Object>> getAllCollect(Integer userId) {
        List<ProductCollectList> pc = productCollectDao.getAllCollect(userId);

        List<Map<String, Object>> pcList = new ArrayList<>();
        for(ProductCollectList pcl : pc) {
            Map<String, Object> res = new HashMap<>();
            res.put("pdNo", pcl.getPdNo());
            res.put("userId", pcl.getUserId());
            res.put("PdPrice", pcl.getPdPrice());
            res.put("base64Image", pcl.getBase64Image());

            pcList.add(res);
        }
        return pcList;
    }
}
