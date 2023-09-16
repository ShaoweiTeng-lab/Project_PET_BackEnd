package project_pet_backEnd.groomer.petgroomercollection.service.imp;

import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.groomer.groomerworkmanager.dao.PictureInfoDao;
import project_pet_backEnd.groomer.groomerworkmanager.vo.PictureInfo;
import project_pet_backEnd.groomer.petgroomer.dao.PetGroomerDao;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.response.ChatRes;
import project_pet_backEnd.groomer.petgroomer.dto.response.PortfolioRes;
import project_pet_backEnd.groomer.petgroomer.vo.PetGroomer;
import project_pet_backEnd.groomer.petgroomercollection.dao.ChatDao;
import project_pet_backEnd.groomer.petgroomercollection.dao.PortfolioCollectDao;
import project_pet_backEnd.groomer.petgroomercollection.dao.PortfolioDao;
import project_pet_backEnd.groomer.petgroomercollection.service.ChatService;
import project_pet_backEnd.groomer.petgroomercollection.service.PortfolioService;
import project_pet_backEnd.groomer.petgroomercollection.vo.Chat;
import project_pet_backEnd.groomer.petgroomercollection.vo.Portfolio;
import project_pet_backEnd.groomer.petgroomercollection.vo.PortfolioCollect;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.UserJwtUtil;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PortfolioServiceImp implements PortfolioService {

    @Autowired
    PortfolioDao dao;

    @Autowired
    PetGroomerDao petGroomerDao;

    @Autowired
    PictureInfoDao pictureInfoDao;

    @Autowired
    PortfolioCollectDao portfolioCollectDao;

    @Autowired
    private UserJwtUtil userJwtUtil;

    /**
     * 获取当前用户
     * @param request
     * @return
     */
    @Override
    public Integer currentUser(HttpServletRequest request) {
//        String token = request.getHeader("Authorization_U");
//        if(StringUtils.isEmpty(token)){
//            return 1;
//        }
//        String userName = userJwtUtil.getUserName(token);
//        if(StringUtils.isEmpty(userName)){
//            return 1;
//        }
//        return Integer.parseInt(userName);
        return 1;
    }

    /**
     * 新增
     *
     * @param rest
     * @return
     */
    @Override
    public ResultResponse insert(Portfolio rest, HttpServletRequest request) {
        rest.setPgId(currentUser(request));
        try {
            dao.insert(rest);
            ResultResponse rs = new ResultResponse();
            rs.setMessage("操作成功");
            rs.setCode(200);
            Portfolio portfolio = dao.findLastestPortfolio();
            rs.setData(portfolio.getPorId());
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
    public ResultResponse update(Portfolio rest, HttpServletRequest request) {
        rest.setPgId(currentUser(request));
        try {
            dao.update(rest);
            ResultResponse rs = new ResultResponse();
            rs.setMessage("操作成功");
            rs.setData(rest.getPorId());
            rs.setCode(200);
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
    public ResultResponse delete(Portfolio rest, HttpServletRequest request) {
        rest.setPgId(currentUser(request));
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
    public PortfolioRes detail(Portfolio rest, HttpServletRequest request) {
        try {
            Portfolio portfolio = dao.findById(rest);
            if (portfolio == null) {
                return null;
            }
            List<PictureInfo> pictureInfos = pictureInfoDao.getAllPicture();
            Map<Integer, byte[]> pictureInfoMap = new HashMap<>();
            for (PictureInfo pictureInfo : pictureInfos) {
                pictureInfoMap.put(pictureInfo.getPorId(), pictureInfo.getPiPicture());
            }
            PortfolioRes res = new PortfolioRes();
            res.setPorId(portfolio.getPorId());
            res.setPorText(portfolio.getPorText());
            res.setPgId(portfolio.getPgId());
            res.setPorTitle(portfolio.getPorTitle());
            byte[] bytes = pictureInfoMap.get(res.getPorId());
            if (bytes != null) {
                res.setPorPic(AllDogCatUtils.base64Encode(bytes));
            }
            res.setPorUpload(AllDogCatUtils.timestampToDateFormat(portfolio.getPorUpload()));
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
    public Page<List<PortfolioRes>> list(PGQueryParameter PGQueryParameter, HttpServletRequest request) {
        Integer pgId = currentUser(request);
        List<PetGroomer> petGroomers = petGroomerDao.getAllGroomer();
        Map<Integer, String> petGroomerMap = new HashMap<>();
        for (PetGroomer petGroomer : petGroomers) {
            petGroomerMap.put(petGroomer.getPgId(), petGroomer.getPgName());
        }
        List<PictureInfo> pictureInfos = pictureInfoDao.getAllPicture();
        Map<Integer, byte[]> pictureInfoMap = new HashMap<>();
        for (PictureInfo pictureInfo : pictureInfos) {
            pictureInfoMap.put(pictureInfo.getPorId(), pictureInfo.getPiPicture());
        }
        PGQueryParameter.setPgId(pgId);
        List<PortfolioCollect> collectList = portfolioCollectDao.list(PGQueryParameter);
        List<Integer> collects = collectList.stream().map(p -> p.getPorId()).collect(Collectors.toList());
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
            res.setPgName(petGroomerMap.get(rest.getPgId()));
            res.setPorTitle(rest.getPorTitle());
            if (collects.contains(res.getPorId())) {
                res.setCollect("1");
            }
            byte[] bytes = pictureInfoMap.get(res.getPorId());
            if (bytes != null) {
                res.setPorPic(AllDogCatUtils.base64Encode(bytes));
            }
            res.setPorUpload(AllDogCatUtils.timestampToDateFormat(rest.getPorUpload()));
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

