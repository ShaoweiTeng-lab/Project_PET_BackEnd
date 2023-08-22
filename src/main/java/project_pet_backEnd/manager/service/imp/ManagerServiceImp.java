package project_pet_backEnd.manager.service.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_pet_backEnd.manager.dao.ManagerRepository;
import project_pet_backEnd.manager.dao.PermissionRepository;
import project_pet_backEnd.manager.dto.*;
import project_pet_backEnd.manager.security.ManagerDetailsImp;
import project_pet_backEnd.manager.service.ManagerService;
import project_pet_backEnd.manager.vo.Manager;
import project_pet_backEnd.utils.commonDto.ResponsePage;
import project_pet_backEnd.utils.commonDto.ResultResponse;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.ManagerJwtUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ManagerServiceImp  implements ManagerService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private  ManagerJwtUtil managerJwtUtil;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private  ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder bcryptEncoder;


    public ResultResponse createManager(CreateManagerRequest createManagerRequest){
        Manager manager=managerRepository.findByManagerAccount(createManagerRequest.getManagerAccount());
        if(manager!=null)
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"已有重複管理員");
        manager =new Manager();
        manager.setManagerAccount(createManagerRequest.getManagerAccount());
        String encodePwd=bcryptEncoder.encode(createManagerRequest.getManagerPassword());
        manager.setManagerPassword(encodePwd);
        managerRepository.save(manager);
        ResultResponse loginResponse=new ResultResponse();
        loginResponse.setMessage("新增成功");
        return loginResponse;
    }

    public ResultResponse managerLogin(ManagerLoginRequest managerLoginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(managerLoginRequest.getManagerAccount(),managerLoginRequest.getManagerPassword());
        Authentication authentication= authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authentication))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"認證錯誤");
        ManagerDetailsImp managerDetail = (ManagerDetailsImp) authentication.getPrincipal();
        if(managerDetail.getManager().getManagerState()==0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"您已被停權");
        String managerId =String.valueOf( managerDetail.getManager().getManagerId());
        ObjectMapper objectMapper =new ObjectMapper();
        String managerDetailJson=null;
        try {
            managerDetailJson=objectMapper.writeValueAsString(managerDetail);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        redisTemplate.opsForValue().set("Manager_Login_"+managerDetail.getManager().getManagerId(),managerDetailJson);
        String jwt= managerJwtUtil.createJwt(managerId);
        ResultResponse responseResult=new ResultResponse();
        responseResult.setMessage(jwt);
        return  responseResult;
    }
    @Transactional
    public  ResultResponse adjustPermission(AdjustPermissionRequest adjustPermissionRequest){
        Manager manager=managerRepository.findByManagerAccount(adjustPermissionRequest.getAccount());
        if(manager==null)
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"無此管理員");
        Integer managerId=manager.getManagerId();
        List<String> authorities=  managerRepository.findManagerFunctionsById(managerId);
        if(authorities.contains( ManagerAuthorities.管理員管理.name()))
               throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"最高管理員不可更改自身權限");
        managerRepository.deleteAllAuthoritiesById(managerId);
        List<String> stringList = new ArrayList<>();
        for (ManagerAuthorities enumValue : adjustPermissionRequest.getAuthorities()) {
            stringList.add(enumValue.name());
        }
        permissionRepository.batchUpdatePermission(managerId,stringList);
        redisTemplate.delete("Manager_Login_"+managerId);//需重新登入
        ResultResponse rs =new ResultResponse();
        rs.setMessage("更新完成");
        return  rs;
    }

    public  ResultResponse getManagerAuthoritiesById(Integer managerId){
        ResultResponse rs =new ResultResponse();
        Manager manager =managerRepository.findById(managerId).orElse(null);
        List<ManagerAuthorities> managerAuthoritiesList =new ArrayList<>();
        List<String> managerFunctions=managerRepository.findManagerFunctionsById(managerId);
        managerFunctions.forEach(function ->{
            ManagerAuthorities managerAuthorities=ManagerAuthorities.valueOf(function);
            managerAuthoritiesList.add(managerAuthorities);
        } );
        QueryManagerAuthorities queryManagerAuthorities=new QueryManagerAuthorities();
        queryManagerAuthorities.setManagerAccount(manager.getManagerAccount());
        queryManagerAuthorities.setManagerAuthoritiesList(managerAuthoritiesList);
        rs.setMessage(queryManagerAuthorities);
        return  rs;
    }
    public  ResultResponse getManagerAuthoritiesByAccount(String account){
        ResultResponse rs =new ResultResponse();
        List<ManagerAuthorities> managerAuthoritiesList =new ArrayList<>();
        List<String> managerFunctions=managerRepository.findManagerFunctionsByAccount(account);
        managerFunctions.forEach(function ->{
            ManagerAuthorities managerAuthorities=ManagerAuthorities.valueOf(function);
            managerAuthoritiesList.add(managerAuthorities);
        } );

        QueryManagerAuthorities queryManagerAuthorities=new QueryManagerAuthorities();
        queryManagerAuthorities.setManagerAccount(account);
        queryManagerAuthorities.setManagerAuthoritiesList(managerAuthoritiesList);
        rs.setMessage(queryManagerAuthorities);
        return  rs;
    }
    @Transactional
    public ResultResponse adjustManager(AdjustManagerRequest adjustManagerRequest) {
        Manager manager =managerRepository.findByManagerAccount(adjustManagerRequest.getOrgManagerAccount());
        if(manager==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"無此管理員");
        if(adjustManagerRequest.getManagerPassword()!=null)
            manager.setManagerPassword(bcryptEncoder.encode(adjustManagerRequest.getManagerPassword()));
        managerRepository.save(manager);
        ResultResponse rs =new ResultResponse();
        rs.setMessage("修改完成");
        return  rs;
    }

    @Override
    public ResponsePage<List<ManagerQueryResponse>> getManagers(QueryManagerParameter queryManagerParameter) {
        Pageable pageable = PageRequest.of(queryManagerParameter.getPage()-1, queryManagerParameter.getSize(), Sort.by("managerId").ascending());
        org.springframework.data.domain.Page<Manager> managerPage =managerRepository.findByManagerAccount(queryManagerParameter.getSearch(), pageable);
        List<Manager> managerList = managerPage.getContent();
        List<ManagerQueryResponse> managerQueryResponseList=new ArrayList<>();
        for(int i =0 ;i<managerList.size();i++){
            Manager manager =managerList.get(i);
            ManagerQueryResponse managerQueryResponse =new ManagerQueryResponse();
            managerQueryResponse.setManagerAccount(manager.getManagerAccount());
            managerQueryResponse.setManagerCreated(AllDogCatUtils.timestampToDateFormat(manager.getManagerCreated()));
            managerQueryResponse.setManagerState(manager.getManagerState()==1?"開啟":"停權");
            managerQueryResponseList.add(managerQueryResponse);
        }
        ResponsePage<List<ManagerQueryResponse>> rs =new ResponsePage<>();
        rs.setPage(managerPage.getPageable().getPageNumber()+1);//pageable預設從第0頁開始
        rs.setSize(pageable.getPageSize());
        rs.setTotal((int)managerPage.getTotalElements());
        rs.setBody(managerQueryResponseList);
        return rs;
    }

    @Override
    public ManagerProfileResponse getProfile(Integer managerId) {
        Manager manager=managerRepository.findById(managerId).orElse(null);
        if(manager==null)
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"無此管理員");
        ManagerProfileResponse managerProfileResponse =new ManagerProfileResponse();
        managerProfileResponse.setManagerAccount(manager.getManagerAccount());
        managerProfileResponse.setManagerCreated(AllDogCatUtils.timestampToDateFormat(manager.getManagerCreated()));
        String state="";
        switch (manager.getManagerState()){
            case 0:
                state="停權";
                break;
            case 1:
                state="開啟";
                break;
        }
        managerProfileResponse.setManagerState(state);
        return managerProfileResponse;
    }

    @Override
    public void adjustProfile(Integer managerId, ManagerAdjustProfileRequest managerAdjustProfileRequest) {

        Manager adjustManager =managerRepository.findById(managerId).orElse(null);
        if(adjustManager==null)
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"無此使用者");
        String pwd =bcryptEncoder.encode(managerAdjustProfileRequest.getManagerPassword());
        adjustManager.setManagerPassword(pwd);
        managerRepository.save(adjustManager);
    }
}
