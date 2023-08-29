package project_pet_backEnd.productMall.mall.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_pet_backEnd.productMall.mall.dao.MallDao;
import project_pet_backEnd.productMall.mall.dto.GetAllMall;
import project_pet_backEnd.productMall.mall.dto.MallQueryParameter;
import project_pet_backEnd.productMall.mall.service.MallService;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MallServiceImp implements MallService {
    @Autowired
    MallDao mallDao;

    @Override
    public Page<List<Map<String, Object>>> list(MallQueryParameter mallQueryParameter) {
        mallQueryParameter.setSortField("PD_PRICE"); // 設置排序屬性
        mallQueryParameter.setSortOrder("DESC"); // 設置排序顺序

        List<GetAllMall> allMall = mallDao.getMallProducts(mallQueryParameter);

        List<Map<String, Object>> mallQueryParameterList = new ArrayList<>();
        for (GetAllMall getAllMall : allMall) {
            Map<String, Object> mallData = new HashMap<>();
            mallData.put("pdName", getAllMall.getPdName());
            mallData.put("pdPrice", getAllMall.getPdPrice());
            // 添加商品圖片 base64 字串
            mallData.put("base64Image", getAllMall.getBase64Image());

            mallQueryParameterList.add(mallData);
        }

        Page<List<Map<String, Object>>> page = new Page<>();
        page.setLimit(mallQueryParameter.getLimit());
        page.setOffset(mallQueryParameter.getOffset());
        Integer total = mallDao.countMallProducts(mallQueryParameter);
        page.setTotal(total);
        page.setRs(mallQueryParameterList);
        return page;
    }
}
