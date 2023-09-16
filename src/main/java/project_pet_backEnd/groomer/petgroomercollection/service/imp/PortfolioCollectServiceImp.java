package project_pet_backEnd.groomer.petgroomercollection.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.groomer.groomerworkmanager.dao.PictureInfoDao;
import project_pet_backEnd.groomer.groomerworkmanager.vo.PictureInfo;
import project_pet_backEnd.groomer.petgroomer.dao.PetGroomerDao;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.response.PortfolioCollectRes;
import project_pet_backEnd.groomer.petgroomer.dto.response.PortfolioRes;
import project_pet_backEnd.groomer.petgroomer.vo.PetGroomer;
import project_pet_backEnd.groomer.petgroomercollection.dao.PortfolioCollectDao;
import project_pet_backEnd.groomer.petgroomercollection.dao.PortfolioDao;
import project_pet_backEnd.groomer.petgroomercollection.service.PortfolioCollectService;
import project_pet_backEnd.groomer.petgroomercollection.service.PortfolioService;
import project_pet_backEnd.groomer.petgroomercollection.vo.Portfolio;
import project_pet_backEnd.groomer.petgroomercollection.vo.PortfolioCollect;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PortfolioCollectServiceImp implements PortfolioCollectService {

    @Autowired
    PortfolioCollectDao dao;

    @Autowired
    PortfolioDao portfolioDao;

    @Autowired
    PictureInfoDao pictureInfoDao;

    @Autowired
    PetGroomerDao petGroomerDao;

    /**
     * 新增
     *
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
     *
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
     *
     * @param rest
     * @return
     */
    @Override
    public ResultResponse delete(PortfolioCollect rest) {
        try {
            dao.delete(rest);
            ResultResponse rs = new ResultResponse();
            rs.setMessage("操作成功");
            rs.setCode(200);
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
    public PortfolioCollectRes detail(PortfolioCollect rest) {
        try {
            PortfolioCollect portfolio = dao.findById(rest);
            if (portfolio == null) {
                return null;
            }
            PortfolioCollectRes res = new PortfolioCollectRes();
            res.setPcNo(portfolio.getPcNo());
            res.setPorId(portfolio.getPorId());
            res.setUserId(portfolio.getUserId());
            res.setPcCreated(AllDogCatUtils.timestampToDateFormat(portfolio.getPcCreated()));
            return res;
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "操作失敗，請稍後重試", e);
        }
    }

    /**
     * 列表
     *
     * @param PGQueryParameter
     * @return
     */
    @Override
    public Page<List<PortfolioCollectRes>> list(PGQueryParameter PGQueryParameter) {
        List<PetGroomer> petGroomers = petGroomerDao.getAllGroomer();
        Map<Integer, String> petGroomerMap = new HashMap<>();
        for (PetGroomer petGroomer : petGroomers) {
            petGroomerMap.put(petGroomer.getPgId(), petGroomer.getPgName());
        }
        List<PortfolioCollect> list = dao.list(PGQueryParameter);
        List<Portfolio> portfolioList = portfolioDao.listAllPortfolio();
        Map<Integer, Portfolio> portfolioMap = new HashMap<>();
        for (Portfolio portfolio : portfolioList) {
            portfolioMap.put(portfolio.getPorId(), portfolio);
        }
        List<PictureInfo> pictureInfos = pictureInfoDao.getAllPicture();
        Map<Integer, byte[]> pictureInfoMap = new HashMap<>();
        for (PictureInfo pictureInfo : pictureInfos) {
            pictureInfoMap.put(pictureInfo.getPorId(), pictureInfo.getPiPicture());
        }
        List<PortfolioCollectRes> rsList = new ArrayList<>();
        for (PortfolioCollect rest : list) {
            PortfolioCollectRes res = new PortfolioCollectRes();
            res.setPcNo(rest.getPcNo());
            res.setPorId(rest.getPorId());
            res.setUserId(rest.getUserId());
            res.setPcCreated(AllDogCatUtils.timestampToDateFormat(rest.getPcCreated()));
            Portfolio portfolio = portfolioMap.get(rest.getPorId());
            if (portfolio != null) {
                res.setPorId(portfolio.getPorId());
                res.setPorText(portfolio.getPorText());
                res.setPgId(portfolio.getPgId());
                res.setPgName(petGroomerMap.get(portfolio.getPgId()));
                res.setPorTitle(portfolio.getPorTitle());
                byte[] bytes = pictureInfoMap.get(res.getPorId());
                if (bytes != null) {
                    res.setPorPic(AllDogCatUtils.base64Encode(bytes));
                }
                res.setPorUpload(AllDogCatUtils.timestampToDateFormat(portfolio.getPorUpload()));
            }
            rsList.add(res);
        }
        Page page = new Page<>();
        page.setLimit(PGQueryParameter.getLimit());
        page.setOffset(PGQueryParameter.getOffset());
        //得到總筆數，方便實作頁數
        Integer total = dao.count(PGQueryParameter);
        if (total % PGQueryParameter.getLimit() == 0) {
            page.setPage(total / PGQueryParameter.getLimit());
        } else {
            page.setPage(total / PGQueryParameter.getLimit() + 1);
        }
        page.setTotal(total);
        page.setRs(rsList);
        return page;
    }
}

