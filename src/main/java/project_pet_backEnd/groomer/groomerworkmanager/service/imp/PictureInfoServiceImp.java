package project_pet_backEnd.groomer.groomerworkmanager.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.groomer.groomerworkmanager.dao.PictureInfoDao;
import project_pet_backEnd.groomer.groomerworkmanager.service.PictureInfoService;
import project_pet_backEnd.groomer.groomerworkmanager.vo.PictureInfo;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.response.PictureInfoRes;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class PictureInfoServiceImp implements PictureInfoService {

    @Autowired
    PictureInfoDao dao;

    /**
     * 新增作品圖片
     * @param rest
     * @return
     */
    @Override
    public ResultResponse insert(PictureInfo rest) {
        try {
            dao.insert(rest);
            ResultResponse rs = new ResultResponse();
            rs.setMessage("操作成功");
            return rs;
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "操作失敗，請稍後重試", e);
        }
    }

    /**
     * 更新作品圖片
     * @param rest
     * @return
     */
    @Override
    public ResultResponse update(PictureInfo rest) {
        try {
            dao.update(rest);
            ResultResponse rs = new ResultResponse();
            rs.setMessage("操作成功");
            return rs;
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "操作失敗，請稍後重試", e);
        }
    }

    /**
     * 删除作品圖片
     * @param rest
     * @return
     */
    @Override
    public ResultResponse delete(PictureInfo rest) {
        try {
            dao.delete(rest);
            ResultResponse rs = new ResultResponse();
            rs.setMessage("操作成功");
            return rs;
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "操作失敗，請稍後重試", e);
        }
    }

    /**
     * 查询作品圖片
     *
     * @param rest
     * @return
     */
    @Override
    public PictureInfoRes findById(PictureInfo rest) {
        try {
            PictureInfo portfolio = dao.findById(rest);
            if (portfolio == null) {
                return null;
            }
            PictureInfoRes res = new PictureInfoRes();
            res.setPiNo(portfolio.getPiNo());
            res.setPorId(portfolio.getPorId());
            res.setPiPicture(AllDogCatUtils.base64Encode(portfolio.getPiPicture()));
            res.setPiDate(AllDogCatUtils.timestampToSqlDateFormat((Date) portfolio.getPiDate()));
            return res;
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "操作失敗，請稍後重試", e);
        }
    }

    /**
     * 作品圖片列表
     * @param PGQueryParameter
     * @return
     */
    @Override
    public Page<List<PictureInfoRes>> list(PGQueryParameter PGQueryParameter) {
        List<PictureInfo> list = dao.list(PGQueryParameter);
        List<PictureInfoRes> rsList = new ArrayList<>();
//        if (list == null || list.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "查询不到作品");
//        }
        for (PictureInfo portfolio : list) {
            PictureInfoRes res = new PictureInfoRes();
            res.setPiNo(portfolio.getPiNo());
            res.setPorId(portfolio.getPorId());
            res.setPiPicture(AllDogCatUtils.base64Encode(portfolio.getPiPicture()));
            res.setPiDate(AllDogCatUtils.timestampToSqlDateFormat((Date) portfolio.getPiDate()));
            rsList.add(res);
        }
        Page page = new Page<>();
        page.setLimit(PGQueryParameter.getLimit());
        page.setOffset(PGQueryParameter.getOffset());
        //得到總筆數，方便實作頁數
        Integer total = dao.count(PGQueryParameter);
        page.setTotal(total);
        page.setRs(rsList);
        return page;
    }
}
