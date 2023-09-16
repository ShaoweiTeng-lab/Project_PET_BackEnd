package project_pet_backEnd.groomer.petgroomercollection.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.orderby.PGOrderBy;
import project_pet_backEnd.groomer.petgroomer.dto.response.PortfolioReq;
import project_pet_backEnd.groomer.petgroomer.dto.response.PortfolioRes;
import project_pet_backEnd.groomer.petgroomercollection.service.PortfolioService;
import project_pet_backEnd.groomer.petgroomercollection.vo.Portfolio;
import project_pet_backEnd.userManager.dto.Sort;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
public class PortfolioController {

    public static Map<Integer, Integer> portfolioMap = new HashMap<>();

    @Autowired
    PortfolioService service;

    /**
     * 新增作品
     *
     * @param rest
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('美容師管理')")

    @PostMapping("/portfolio/insert")
    public ResponseEntity<?> insert(
            @RequestBody Portfolio rest, HttpServletRequest request
    ) {
        rest.setPorUpload(new Date());
        ResultResponse resultResponse = service.insert(rest, request);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    /**
     * 更新作品
     *
     * @param portfolio
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('美容師管理')")

    @PostMapping("/portfolio/update")
    public ResponseEntity<?> update(
            @RequestBody Portfolio portfolio, HttpServletRequest request
    ) {
        Portfolio rest = new Portfolio();
        rest.setPorId(portfolioMap.get(portfolio.getPgId()));
        rest.setPgId(portfolio.getPgId());
        rest.setPorTitle(portfolio.getPorTitle());
        rest.setPorText(portfolio.getPorText());
        ResultResponse resultResponse = service.update(rest, request);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    /**
     * 删除作品
     *
     * @param portfolio
     * @return
     */
    //    @PreAuthorize("hasAnyAuthority('美容師管理')")

    @PostMapping("/portfolio/delete")
    public ResponseEntity<?> delete(
            @RequestBody Portfolio portfolio, HttpServletRequest request
    ) {
        Portfolio rest = new Portfolio();
        rest.setPorId(portfolio.getPorId());
        ResultResponse resultResponse = service.delete(rest, request);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    /**
     * 查询作品详情
     *
     * @param req
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('美容師管理')")

    @PostMapping("/portfolio/detail")
    public ResponseEntity<?> detail(
            @RequestBody Portfolio req, HttpServletRequest request
    ) {
        Portfolio rest = new Portfolio();
        rest.setPorId(portfolioMap.get(req.getPgId()));
        PortfolioRes portfolio = service.detail(rest, request);
        return ResponseEntity.status(200).body(portfolio);
    }


    @PostMapping("/portfolio/save")
    public ResponseEntity<?> save(
            @RequestBody Portfolio req, HttpServletRequest request
    ) {
        Integer pgId = service.currentUser(request);
        portfolioMap.put(pgId, req.getPorId());
        return ResponseEntity.status(200).body(portfolioMap);
    }


    /**
     * 作品列表
     *
     * @param req
     * @return
     */
    @PostMapping("/portfolio/list")
    public ResponseEntity<Page<List<PortfolioRes>>> list(
            @RequestBody PortfolioReq req, HttpServletRequest request) {
        PGQueryParameter pgQueryParameter = new PGQueryParameter();
        pgQueryParameter.setSearch(req.getSearch());
        Integer limit = req.getPageSize();
        Integer offset = (req.getCurrentPage() - 1) * req.getPageSize();
        pgQueryParameter.setLimit(limit);
        pgQueryParameter.setOffset(offset);
        Page<List<PortfolioRes>> list = service.list(pgQueryParameter, request);
        return ResponseEntity.status(200).body(list);
    }
}
