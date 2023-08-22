package project_pet_backEnd.groomer.petgroomercollection.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.response.PortfolioCollectRes;
import project_pet_backEnd.groomer.petgroomercollection.dao.PortfolioCollectDao;
import project_pet_backEnd.groomer.petgroomercollection.service.PortfolioCollectService;
import project_pet_backEnd.groomer.petgroomercollection.vo.PortfolioCollect;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class PortfolioCollectServiceImp implements PortfolioCollectService {

    @Autowired
    PortfolioCollectDao dao;

    /**
     * 新增
     * @param rest
     * @return
     */
    @Override
    public ResultResponse insert(PortfolioCollect rest) {
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
     * 更新
     * @param rest
     * @return
     */
    @Override
    public ResultResponse update(PortfolioCollect rest) {
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
     * 删除
     * @param rest
     * @return
     */
    @Override
    public ResultResponse delete(PortfolioCollect rest) {
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
     * 查询
     *
     * @param rest
     * @return
     */
    @Override
    public PortfolioCollectRes findById(PortfolioCollect rest) {
        try {
            PortfolioCollect portfolio = dao.findById(rest);
            if (portfolio == null) {
                return null;
            }
            PortfolioCollectRes res = new PortfolioCollectRes();
            res.setPcNo(portfolio.getPcNo());
            res.setPorId(portfolio.getPorId());
            res.setUserId(portfolio.getUserId());
            res.setPcCreated(AllDogCatUtils.timestampToSqlDateFormat(portfolio.getPcCreated()));
            return res;
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "操作失敗，請稍後重試", e);
        }
    }

    /**
     * 列表
     * @param PGQueryParameter
     * @return
     */
    @Override
    public Page<List<PortfolioCollectRes>> list(PGQueryParameter PGQueryParameter) {
        List<PortfolioCollect> list = dao.list(PGQueryParameter);
        List<PortfolioCollectRes> rsList = new ArrayList<>();
//        if (list == null || list.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "查询不到作品");
//        }
        for (PortfolioCollect rest : list) {
            PortfolioCollectRes res = new PortfolioCollectRes();
            res.setPcNo(rest.getPcNo());
            res.setPorId(rest.getPorId());
            res.setUserId(rest.getUserId());
            res.setPcCreated(AllDogCatUtils.timestampToSqlDateFormat(rest.getPcCreated()));
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
