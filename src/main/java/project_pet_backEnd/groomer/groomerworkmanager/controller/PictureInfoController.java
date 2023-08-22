package project_pet_backEnd.groomer.groomerworkmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project_pet_backEnd.groomer.groomerworkmanager.service.PictureInfoService;
import project_pet_backEnd.groomer.groomerworkmanager.vo.PictureInfo;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.orderby.PGOrderBy;
import project_pet_backEnd.groomer.petgroomer.dto.response.PictureInfoRes;
import project_pet_backEnd.groomer.petgroomer.dto.response.PortfolioCollectRes;
import project_pet_backEnd.groomer.petgroomercollection.service.PortfolioCollectService;
import project_pet_backEnd.groomer.petgroomercollection.vo.PortfolioCollect;
import project_pet_backEnd.userManager.dto.Sort;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@RestController
@Validated
public class PictureInfoController {

    @Autowired
    PictureInfoService service;

    /**
     * 新增作品圖片
     * @param porId
     * @param piPicture
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @PostMapping("/picture/insert")
    public ResponseEntity<?> insert(
            @RequestParam @NotNull Integer porId,
            @RequestParam @NotNull MultipartFile piPicture
    ) {
        PictureInfo rest = new PictureInfo();
        rest.setPorId(porId);
        rest.setPiPicture(AllDogCatUtils.convertMultipartFileToByteArray(piPicture));
        ResultResponse resultResponse = service.insert(rest);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    /**
     * 修改作品图片
     * @param piNo
     * @param porId
     * @param piPicture
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @PostMapping("/picture/update")
    public ResponseEntity<?> update(
            @RequestParam @NotNull Integer piNo,
            @RequestParam @NotNull Integer porId,
            @RequestParam @NotNull MultipartFile piPicture
    ) {
        PictureInfo rest = new PictureInfo();
        rest.setPiNo(piNo);
        rest.setPorId(porId);
        rest.setPiPicture(AllDogCatUtils.convertMultipartFileToByteArray(piPicture));
        rest.setPiDate(new Date());
        ResultResponse resultResponse = service.update(rest);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    /**
     * 删除作品圖片
     * @param piNo
     * @return
     */
    //    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @PostMapping("/picture/delete")
    public ResponseEntity<?> delete(
            @RequestParam @NotNull Integer piNo
    ) {
        PictureInfo rest = new PictureInfo();
        rest.setPiNo(piNo);
        ResultResponse resultResponse = service.delete(rest);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    /**
     * 查询作品圖片
     * @param piNo
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @PostMapping("/picture/findById")
    public ResponseEntity<?> findById(
            @RequestParam @NotNull Integer piNo
    ) {
        PictureInfo rest = new PictureInfo();
        rest.setPiNo(piNo);
        PictureInfoRes portfolio = service.findById(rest);
        return ResponseEntity.status(200).body(portfolio);
    }

    /**
     * 作品圖片列表
     * @param porId
     * @param search
     * @param orderBy
     * @param sort
     * @param limit
     * @param offset
     * @return
     */
    @GetMapping("/picture/list")
    public ResponseEntity<Page<List<PictureInfoRes>>> list(
            @RequestParam(value = "porId", required = false) Integer porId,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "orderBy", required = false, defaultValue = "NUM_APPOINTMENTS") PGOrderBy orderBy,
            @RequestParam(value = "sort", required = false, defaultValue = "desc") Sort sort,
            @RequestParam(value = "limit", defaultValue = "10") @Max(50) @Min(0) Integer limit,
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset) {
        PGQueryParameter pgQueryParameter = new PGQueryParameter();
        pgQueryParameter.setPorId(porId);
        pgQueryParameter.setSearch(search);
        pgQueryParameter.setOrder(orderBy);
        pgQueryParameter.setSort(sort);
        pgQueryParameter.setLimit(limit);
        pgQueryParameter.setOffset(offset);
        Page<List<PictureInfoRes>> list = service.list(pgQueryParameter);
        return ResponseEntity.status(200).body(list);
    }
}