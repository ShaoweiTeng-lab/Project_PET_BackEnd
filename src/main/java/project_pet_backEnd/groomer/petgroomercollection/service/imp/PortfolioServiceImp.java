package project_pet_backEnd.groomer.petgroomercollection.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.response.PortfolioRes;
import project_pet_backEnd.groomer.petgroomercollection.dao.PortfolioDao;
import project_pet_backEnd.groomer.petgroomercollection.service.PortfolioService;
import project_pet_backEnd.groomer.petgroomercollection.vo.Portfolio;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class PortfolioServiceImp implements PortfolioService {

    @Autowired
    PortfolioDao dao;

    /**
     * 新增
     * @param rest
     * @return
     */
    @Override
    public ResultResponse insert(Portfolio rest) {
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
    public ResultResponse update(Portfolio rest) {
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
    public ResultResponse delete(Portfolio rest) {
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
    public PortfolioRes findById(Portfolio rest) {
        try {
            Portfolio portfolio = dao.findById(rest);
            if (portfolio == null) {
                return null;
            }
            PortfolioRes res = new PortfolioRes();
            res.setPorId(portfolio.getPorId());
            res.setPorText(portfolio.getPorText());
            res.setPgId(portfolio.getPgId());
            res.setPorTitle(portfolio.getPorTitle());
            res.setPorUpload(AllDogCatUtils.timestampToSqlDateFormat(portfolio.getPorUpload()));
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
    public Page<List<PortfolioRes>> list(PGQueryParameter PGQueryParameter) {
        List<Portfolio> list = dao.list(PGQueryParameter);
        List<PortfolioRes> rsList = new ArrayList<>();
//        if (list == null || list.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "查询不到作品");
//        }
        for (Portfolio rest : list) {
            PortfolioRes res = new PortfolioRes();
            res.setPorId(rest.getPorId());
            res.setPorText(rest.getPorText());
            res.setPgId(rest.getPgId());
            res.setPorTitle(rest.getPorTitle());
            res.setPorUpload(AllDogCatUtils.timestampToSqlDateFormat(rest.getPorUpload()));
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
