package project_pet_backEnd.groomer.petgroomercollection.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.orderby.PGOrderBy;
import project_pet_backEnd.groomer.petgroomer.dto.response.PortfolioCollectReq;
import project_pet_backEnd.groomer.petgroomer.dto.response.PortfolioCollectRes;
import project_pet_backEnd.groomer.petgroomercollection.service.PortfolioCollectService;
import project_pet_backEnd.groomer.petgroomercollection.vo.PortfolioCollect;
import project_pet_backEnd.userManager.dto.Sort;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@RestController
@Validated
public class PortfolioCollectController {

    @Autowired
    PortfolioCollectService service;

    /**
     * 新增作品收藏
     *
     * @param collect
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/collect/insert")
    public ResponseEntity<?> insert(
            @RequestBody PortfolioCollect collect
    ) {
        PortfolioCollect rest = new PortfolioCollect();
        rest.setPorId(collect.getPorId());
        rest.setUserId(collect.getUserId());
        rest.setPcCreated(new Date());
        ResultResponse resultResponse = service.insert(rest);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    /**
     * 更新作品收藏
     *
     * @param pcNo
     * @param porId
     * @param userId
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/collect/update")
    public ResponseEntity<?> update(
            @RequestParam @NotNull Integer pcNo,
            @RequestParam @NotNull Integer porId,
            @RequestParam @NotNull Integer userId
    ) {
        PortfolioCollect rest = new PortfolioCollect();
        rest.setPcNo(pcNo);
        rest.setPorId(porId);
        rest.setUserId(userId);
        rest.setPcCreated(new Date());
        ResultResponse resultResponse = service.update(rest);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    /**
     * 删除作品收藏
     *
     * @param collect
     * @return
     */
    //    @PreAuthorize("hasAnyAuthority('美容師管理')")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
//    })
    @PostMapping("/collect/delete")
    public ResponseEntity<?> delete(
            @RequestBody PortfolioCollect collect
    ) {
        PortfolioCollect rest = new PortfolioCollect();
        rest.setPcNo(collect.getPcNo());
        ResultResponse resultResponse = service.delete(rest);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    /**
     * 查询作品收藏
     *
     * @param pcNo
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/collect/detail")
    public ResponseEntity<?> detail(
            @RequestParam @NotNull Integer pcNo
    ) {
        PortfolioCollect rest = new PortfolioCollect();
        rest.setPcNo(pcNo);
        PortfolioCollectRes portfolio = service.detail(rest);
        return ResponseEntity.status(200).body(portfolio);
    }

    /**
     * 作品收藏列表
     *
     * @param req
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/collect/list")
    public ResponseEntity<Page<List<PortfolioCollectRes>>> list(
            @RequestBody PortfolioCollectReq req) {
        PGQueryParameter pgQueryParameter = new PGQueryParameter();
        pgQueryParameter.setUserId(req.getUserId());
        Integer limit = req.getPageSize();
        Integer offset = (req.getCurrentPage() - 1) * req.getPageSize();
        pgQueryParameter.setLimit(limit);
        pgQueryParameter.setOffset(offset);
        Page<List<PortfolioCollectRes>> list = service.list(pgQueryParameter);
        return ResponseEntity.status(200).body(list);
    }
}
