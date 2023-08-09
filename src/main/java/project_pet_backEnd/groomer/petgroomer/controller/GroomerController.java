package project_pet_backEnd.groomer.petgroomer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.orderby.PGOrderBy;
import project_pet_backEnd.groomer.petgroomer.dto.request.PGInsertReq;
import project_pet_backEnd.groomer.petgroomer.dto.response.GetAllGroomerListRes;
import project_pet_backEnd.groomer.petgroomer.dto.response.GetAllGroomerListSortRes;
import project_pet_backEnd.groomer.petgroomer.dto.response.GetAllGroomerListSortResForUser;
import project_pet_backEnd.groomer.petgroomer.service.PetGroomerService;
import project_pet_backEnd.user.dto.ResultResponse;
import project_pet_backEnd.userManager.dto.Sort;
import project_pet_backEnd.utils.commonDto.Page;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated

public class GroomerController {

    @Autowired
    PetGroomerService petGroomerService;
    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @GetMapping("/manager/insertNewGroomer")
    public ResponseEntity<?> insertNewGroomer(){
        ResultResponse managerByFunctionIdList = petGroomerService.getManagerByFunctionId(3);
        return  ResponseEntity.status(HttpStatus.OK).body(managerByFunctionIdList );
    }
    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @PostMapping("/manager/commitInsertNewGroomer")
    public ResponseEntity<?> commitInsertNewGroomer(@RequestBody @Valid PGInsertReq pgInsertReq){
        ResultResponse resultResponse = petGroomerService.insertGroomer(pgInsertReq);//Y
        return  ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }
    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @GetMapping("/manager/getAllGroomerListSort")
    public ResponseEntity<Page<List<GetAllGroomerListSortRes>>> getAllGroomersForMan(
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
        return ResponseEntity.status(200).body(petGroomerServiceAllGroomersForMan);
    }

    @GetMapping("/customer/getAllGroomerListSort")
    public ResponseEntity<Page<List<GetAllGroomerListSortResForUser>>> getAllGroomersForUser(
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
        return ResponseEntity.status(200).body(petGroomerServiceAllGroomersForUser);
    }
    @PreAuthorize("hasAnyAuthority('美容師管理')")
    @PostMapping("/manager/updateGroomerByPgId")
    public ResponseEntity<?> updateGroomerByPgIdForMan(@RequestBody  @Valid GetAllGroomerListRes getAllGroomerListRes){
        ResultResponse petGroomerServiceAllGroomersForUser = petGroomerService.updateGroomerByIdForMan(getAllGroomerListRes);
        return ResponseEntity.status(200).body(petGroomerServiceAllGroomersForUser);
    }

}
