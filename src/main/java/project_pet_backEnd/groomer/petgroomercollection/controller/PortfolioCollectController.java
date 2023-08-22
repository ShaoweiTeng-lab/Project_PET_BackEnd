package project_pet_backEnd.groomer.petgroomercollection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.orderby.PGOrderBy;
import project_pet_backEnd.groomer.petgroomer.dto.response.PortfolioCollectRes;
import project_pet_backEnd.groomer.petgroomercollection.service.PortfolioCollectService;
import project_pet_backEnd.groomer.petgroomercollection.vo.PortfolioCollect;
import project_pet_backEnd.userManager.dto.Sort;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Validated
public class PortfolioCollectController {

    @Autowired
    PortfolioCollectService service;

    /**
     * 新增作品收藏
     *
     * @param porId
     * @param userId
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @PostMapping("/collect/insert")
    public ResponseEntity<?> insert(
            @RequestParam @NotNull Integer porId,
            @RequestParam @NotNull Integer userId
    ) {
        PortfolioCollect rest = new PortfolioCollect();
        rest.setPorId(porId);
        rest.setUserId(userId);
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
        ResultResponse resultResponse = service.update(rest);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    /**
     * 删除作品收藏
     *
     * @param pcNo
     * @return
     */
    //    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @PostMapping("/collect/delete")
    public ResponseEntity<?> delete(
            @RequestParam @NotNull Integer pcNo
    ) {
        PortfolioCollect rest = new PortfolioCollect();
        rest.setPcNo(pcNo);
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
    @PostMapping("/collect/findById")
    public ResponseEntity<?> findById(
            @RequestParam @NotNull Integer pcNo
    ) {
        PortfolioCollect rest = new PortfolioCollect();
        rest.setPcNo(pcNo);
        PortfolioCollectRes portfolio = service.findById(rest);
        return ResponseEntity.status(200).body(portfolio);
    }

    /**
     * 作品收藏列表
     *
     * @param userId
     * @param search
     * @param orderBy
     * @param sort
     * @param limit
     * @param offset
     * @return
     */
    @GetMapping("/collect/list")
    public ResponseEntity<Page<List<PortfolioCollectRes>>> list(
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "orderBy", required = false, defaultValue = "NUM_APPOINTMENTS") PGOrderBy orderBy,
            @RequestParam(value = "sort", required = false, defaultValue = "desc") Sort sort,
            @RequestParam(value = "limit", defaultValue = "10") @Max(50) @Min(0) Integer limit,
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset) {
        PGQueryParameter pgQueryParameter = new PGQueryParameter();
        pgQueryParameter.setUserId(userId);
        pgQueryParameter.setSearch(search);
        pgQueryParameter.setOrder(orderBy);
        pgQueryParameter.setSort(sort);
        pgQueryParameter.setLimit(limit);
        pgQueryParameter.setOffset(offset);
        Page<List<PortfolioCollectRes>> list = service.list(pgQueryParameter);
        return ResponseEntity.status(200).body(list);
    }
}
