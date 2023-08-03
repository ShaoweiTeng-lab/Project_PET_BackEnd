package project_pet_backEnd.manager.service.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.manager.dao.ManagerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_pet_backEnd.manager.dao.ManagerRepository;
import project_pet_backEnd.manager.dto.*;
import project_pet_backEnd.manager.security.ManagerDetailsImp;
import project_pet_backEnd.manager.service.ManagerService;
import project_pet_backEnd.manager.vo.Manager;
import project_pet_backEnd.user.dto.ResultResponse;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.ManagerJwtUtil;
import project_pet_backEnd.utils.commonDto.Page;

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
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private ManagerDao managerDao;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private  ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder bcryptEncoder;


    public ResultResponse createManager(CreateManagerRequest createManagerRequest){
        Manager manager=managerDao.getManagerByAccount(createManagerRequest.getManagerAccount());
        if(manager!=null)
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);

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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        ManagerDetailsImp managerDetail = (ManagerDetailsImp) authentication.getPrincipal();
        if(managerDetail.getManager().getManagerState()==0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);//被停權
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
        Integer managerId=managerDao.getManagerIdByAccount(adjustPermissionRequest.getAccount());
        if(managerId==null)
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"無此管理員");
        List<ManagerAuthorities> managerAuthorities=managerDao.getManagerAuthoritiesByAccount(adjustPermissionRequest.getAccount());
        if(managerAuthorities.contains(ManagerAuthorities.管理員管理))
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"最高管理員不可更改自身權限");
        managerDao.deleteAllAuthoritiesById(managerId);
        managerDao.adjustPermission(managerId,adjustPermissionRequest);
        redisTemplate.delete("Manager_Login_"+managerId);//需重新登入
        ResultResponse rs =new ResultResponse();
        rs.setMessage("更新完成");
        return  rs;
    }

    public  ResultResponse getManagerAuthoritiesById(Integer managerId){
        ResultResponse rs =new ResultResponse();
        List<ManagerAuthorities> managerAuthorities=managerDao.getManagerAuthoritiesById(managerId);
        rs.setMessage(managerAuthorities);
        return  rs;
    }
    public  ResultResponse getManagerAuthoritiesByAccount(String account){
        ResultResponse rs =new ResultResponse();
        List<ManagerAuthorities> managerAuthorities=managerDao.getManagerAuthoritiesByAccount(account);
        rs.setMessage(managerAuthorities);
        return  rs;
    }
    @Transactional
    public ResultResponse adjustManager(AdjustManagerRequest adjustManagerRequest) {
        Integer managerId =managerDao.getManagerIdByAccount(adjustManagerRequest.getOrgManagerAccount());
        if (managerId==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"無此管理員");
        adjustManagerRequest.setManagerId(managerId);
        if(adjustManagerRequest.getManagerPassword()==null){
            String password= managerDao.getPasswordById(managerId);
            adjustManagerRequest.setManagerPassword(password);
        }
        else
            adjustManagerRequest.setManagerPassword(bcryptEncoder.encode(adjustManagerRequest.getManagerPassword()));
        managerDao.updateManager(adjustManagerRequest);
        ResultResponse rs =new ResultResponse();
        return  rs;
    }

    @Override
    public Page<List<ManagerQueryResponse>> getManagers(QueryManagerParameter queryManagerParameter) {
        List<Manager> managerList =managerRepository.findAll();
        List<ManagerQueryResponse> managerQueryResponseList=new ArrayList<>();
        for(int i =0 ;i<managerList.size();i++){
            Manager manager =managerList.get(i);
            ManagerQueryResponse managerQueryResponse =new ManagerQueryResponse();
            managerQueryResponse.setManagerAccount(manager.getManagerAccount());
            managerQueryResponse.setManagerCreated(AllDogCatUtils.timestampToDateFormat(manager.getManagerCreated()));
            managerQueryResponse.setManagerState(manager.getManagerState()==1?"開啟":"停權");
            managerQueryResponseList.add(managerQueryResponse);
        }
        Page<List<ManagerQueryResponse>> rs =new Page<>();
        rs.setLimit(queryManagerParameter.getLimit());
        rs.setOffset(queryManagerParameter.getOffset());
        rs.setTotal(managerList.size());
        rs.setRs(managerQueryResponseList);
        return rs;
    }
}
