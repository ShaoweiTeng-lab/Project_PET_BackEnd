package project_pet_backEnd.groomer.petgroomer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.orderby.PGOrderBy;
import project_pet_backEnd.groomer.petgroomer.dto.request.GetAllGroomerListReq;
import project_pet_backEnd.groomer.petgroomer.dto.request.PGInsertReq;
import project_pet_backEnd.groomer.petgroomer.dto.response.GetAllGroomerListSortRes;
import project_pet_backEnd.groomer.petgroomer.dto.response.GetAllGroomerListSortResForUser;
import project_pet_backEnd.groomer.petgroomer.dto.response.ManagerGetByFunctionIdRes;
import project_pet_backEnd.groomer.petgroomer.service.PetGroomerService;
import project_pet_backEnd.utils.commonDto.ResultResponse;
import project_pet_backEnd.userManager.dto.Sort;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.Page;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.List;

@RestController
@Validated

public class GroomerController {

    @Autowired
    PetGroomerService petGroomerService;
    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @GetMapping("/manager/insertNewGroomer")
    public ResultResponse<List<ManagerGetByFunctionIdRes>> insertNewGroomer(){
        return petGroomerService.getManagerByFunctionId(3);
    }

    /*
     * 用From表單送請求進來 QueryString
     */
    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @PostMapping("/manager/commitInsertNewGroomer")
    public ResultResponse<String> commitInsertNewGroomer(
            @RequestParam @NotNull Integer manId,
            @RequestParam @NotBlank String pgName,
            @RequestParam Integer pgGender,
            @RequestParam(required = false) MultipartFile pgPic,
            @RequestParam(required = false) String pgEmail,
            @RequestParam(required = false) String pgPh,
            @RequestParam(required = false) String pgAddress,
            @RequestParam(required = false) String pgBirthday
    ){
        PGInsertReq pgInsertReq = new PGInsertReq();
        pgInsertReq.setManId(manId);
        pgInsertReq.setPgName(pgName);
        pgInsertReq.setPgPic(AllDogCatUtils.convertMultipartFileToByteArray(pgPic));
        pgInsertReq.setPgGender(pgGender);
        pgInsertReq.setPgEmail(pgEmail);
        pgInsertReq.setPgPh(pgPh);
        pgInsertReq.setPgAddress(pgAddress);

        if(pgBirthday==null ||pgBirthday.isBlank()){
            try {
                pgInsertReq.setPgBirthday(AllDogCatUtils.dateFormatToSqlDate("1900-01-01"));
            } catch (ParseException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "日期格式有誤!", e);
            }
        }else {
            try {
                pgInsertReq.setPgBirthday(AllDogCatUtils.dateFormatToSqlDate(pgBirthday));
            } catch (ParseException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "日期格式有誤!", e);
            }
        }

        return petGroomerService.insertGroomer(pgInsertReq);
    }
    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @GetMapping("/manager/getAllGroomerListSort")
    public ResultResponse<Page<List<GetAllGroomerListSortRes>>> getAllGroomersForMan(
            @RequestParam(value = "search",required = false) String search,
            @RequestParam(value = "orderBy",required = false, defaultValue = "NUM_APPOINTMENTS") PGOrderBy orderBy,
            @RequestParam(value = "sort",required = false,defaultValue = "desc") Sort sort,
            @RequestParam(value = "limit",defaultValue = "10")@Max(50) @Min(0) Integer limit,
            @RequestParam(value = "offset",defaultValue = "0")@Min(0)Integer offset){

        PGQueryParameter pgQueryParameter =new PGQueryParameter();
        pgQueryParameter.setSearch(search);
        pgQueryParameter.setOrder(orderBy);
        pgQueryParameter.setSort(sort);
        pgQueryParameter.setLimit(limit);
        pgQueryParameter.setOffset(offset);
        Page<List<GetAllGroomerListSortRes>> petGroomerServiceAllGroomersForMan = petGroomerService.getAllGroomersForMan(pgQueryParameter);

        ResultResponse<Page<List<GetAllGroomerListSortRes>>> rs =new ResultResponse<>();
        rs.setMessage(petGroomerServiceAllGroomersForMan);
        return rs;
    }

    @GetMapping("/customer/getAllGroomerListSort")
    public ResultResponse<Page<List<GetAllGroomerListSortResForUser>>> getAllGroomersForUser(
            @RequestParam(value = "search",required = false) String search,
            @RequestParam(value = "orderBy",required = false, defaultValue = "NUM_APPOINTMENTS") PGOrderBy orderBy,
            @RequestParam(value = "sort",required = false,defaultValue = "desc") Sort sort,
            @RequestParam(value = "limit",defaultValue = "10")@Max(50) @Min(0) Integer limit,
            @RequestParam(value = "offset",defaultValue = "0")@Min(0)Integer offset){
        PGQueryParameter pgQueryParameter =new PGQueryParameter();

        pgQueryParameter.setSearch(search);
        pgQueryParameter.setOrder(orderBy);
        pgQueryParameter.setSort(sort);
        pgQueryParameter.setLimit(limit);
        pgQueryParameter.setOffset(offset);
        Page<List<GetAllGroomerListSortResForUser>> petGroomerServiceAllGroomersForUser = petGroomerService.getAllGroomersForUser(pgQueryParameter);

        ResultResponse<Page<List<GetAllGroomerListSortResForUser>>> rs =new ResultResponse<>();
        rs.setMessage(petGroomerServiceAllGroomersForUser);
        return rs;
    }

    /*
     * 用From表單送請求進來 QueryString
     */
    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @PostMapping("/manager/updateGroomerByPgId")
    public ResultResponse<String> updateGroomerByPgIdForMan(
            @RequestParam @NotNull Integer pgId,
            @RequestParam @NotNull Integer manId,
            @RequestParam(required = false) String pgName,
            @RequestParam(required = false) Integer pgGender,
            @RequestParam(required = false) MultipartFile pgPic,
            @RequestParam(required = false) String pgEmail,
            @RequestParam(required = false) String pgPh,
            @RequestParam(required = false) String pgAddress,
            @RequestParam(required = false) String pgBirthday
            ){
        GetAllGroomerListReq getAllGroomerListReq = new GetAllGroomerListReq();
        getAllGroomerListReq.setPgId(pgId);
        getAllGroomerListReq.setManId(manId);
        getAllGroomerListReq.setPgName(pgName);
        getAllGroomerListReq.setPgGender(pgGender);
        getAllGroomerListReq.setPgPic(AllDogCatUtils.convertMultipartFileToByteArray(pgPic));
        getAllGroomerListReq.setPgEmail(pgEmail);
        getAllGroomerListReq.setPgPh(pgPh);
        getAllGroomerListReq.setPgAddress(pgAddress);

        if(pgBirthday==null ||pgBirthday.isBlank()){
            try {
                getAllGroomerListReq.setPgBirthday(AllDogCatUtils.dateFormatToSqlDate("1900-01-01"));
            } catch (ParseException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "日期格式有誤!", e);
            }
        }else{
            try {
                getAllGroomerListReq.setPgBirthday(AllDogCatUtils.dateFormatToSqlDate(pgBirthday));//yyyy-mm-dd ->sql.date
            } catch (ParseException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "日期格式有誤!", e);
            }
        }
        return petGroomerService.updateGroomerByIdForMan(getAllGroomerListReq);
    }

    //-----美容師個人管理-----

    // 用From表單送請求進來 QueryString
    @PreAuthorize("hasAnyAuthority('美容師個人管理')")
    @PostMapping("/manager/updateGroomerForPg")
    public ResultResponse<String> updateGroomerByPgIdForPg(
            @RequestParam @NotNull Integer pgId,
            @RequestParam @NotNull Integer manId,
            @RequestParam(required = false) String pgName,
            @RequestParam(required = false) Integer pgGender,
            @RequestParam(required = false) MultipartFile pgPic,
            @RequestParam(required = false) String pgEmail,
            @RequestParam(required = false) String pgPh,
            @RequestParam(required = false) String pgAddress,
            @RequestParam(required = false) String pgBirthday
    ){
        GetAllGroomerListReq getAllGroomerListReq = new GetAllGroomerListReq();
        getAllGroomerListReq.setPgId(pgId);
        getAllGroomerListReq.setManId(manId);
        getAllGroomerListReq.setPgName(pgName);
        getAllGroomerListReq.setPgGender(pgGender);
        getAllGroomerListReq.setPgPic(AllDogCatUtils.convertMultipartFileToByteArray(pgPic));
        getAllGroomerListReq.setPgEmail(pgEmail);
        getAllGroomerListReq.setPgPh(pgPh);
        getAllGroomerListReq.setPgAddress(pgAddress);

        if(pgBirthday==null ||pgBirthday.isBlank()){
            try {
                getAllGroomerListReq.setPgBirthday(AllDogCatUtils.dateFormatToSqlDate("1900-01-01"));
            } catch (ParseException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "日期格式有誤!", e);
            }
        }else{
            try {
                getAllGroomerListReq.setPgBirthday(AllDogCatUtils.dateFormatToSqlDate(pgBirthday));//yyyy-mm-dd ->sql.date
            } catch (ParseException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "日期格式有誤!", e);
            }
        }
        return petGroomerService.updateGroomerByIdForMan(getAllGroomerListReq);
    }




}
