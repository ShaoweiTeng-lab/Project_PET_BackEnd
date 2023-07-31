package project_pet_backEnd.manager.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.manager.dao.ManagerDao;
import project_pet_backEnd.manager.dao.ManagerRepository;
import project_pet_backEnd.manager.dto.AdjustManagerRequest;
import project_pet_backEnd.manager.dto.AdjustPermissionRequest;
import project_pet_backEnd.manager.dto.CreateManagerRequest;
import project_pet_backEnd.manager.dto.ManagerAuthorities;
import project_pet_backEnd.manager.vo.Function;
import project_pet_backEnd.manager.vo.Manager;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ManagerDaoImp implements ManagerDao {
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public Manager getManagerByAccount(String managerAccount) {
        //Manager manager =managerRepository.findByManagerAccount(managerAccount);
        String sql ="select * from manager where MANAGER_ACCOUNT =:managerAccount";
        Map map =new HashMap<>();
        map.put("managerAccount",managerAccount);
        List<Manager> managerList=namedParameterJdbcTemplate.query(sql, map, new RowMapper<Manager>() {
            @Override
            public Manager mapRow(ResultSet rs, int rowNum) throws SQLException {
                Manager manager =new Manager();
                manager.setManagerId(rs.getInt("MANAGER_ID"));
                manager.setManagerAccount(rs.getString("MANAGER_ACCOUNT"));
                manager.setManagerPassword(rs.getString("MANAGER_PASSWORD"));
                manager.setManagerCreated(rs.getDate("MANAGER_CREATED"));
                manager.setManagerState(rs.getInt("MANAGER_STATE"));
                return manager;
            }
        });
        if(managerList.size()>0)
            return managerList.get(0);
        return null;
    }

    @Override
    public List<Function> getManagerRolesByManagerId(Integer managerId) {
        String sql="select  permission.function_id,`function`.function_name\n" +
                "from `manager` m\n" +
                "join permission on m.manager_id = permission.manager_id\n" +
                "join `function` on function.function_id = permission.function_id\n" +
                "where m.MANAGER_ID = :managerId";
        Map<String , Object> map =new HashMap<>();
        map.put("managerId", managerId);
        List<Function> rsList=namedParameterJdbcTemplate.query(sql, map, new RowMapper<Function>() {
            @Override
            public Function mapRow(ResultSet rs, int rowNum) throws SQLException {
                Function function =new Function(rs.getInt("function_id"),rs.getString("function_name"));
                return function;
            }
        });
        return rsList;
    }

    @Override
    public void createManager(Manager createManagerData) {
         Manager manager =managerRepository.save(createManagerData);
//        String sql ="Insert into Manager(Manager_Account,Manager_Password) values(:account ,:password)";
//        Map<String,Object> map =new HashMap<>();
//        map.put("account",createManagerData.getManagerAccount());
//        map.put("password",createManagerData.getManagerPassword());
//        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public List<ManagerAuthorities> getManagerAuthoritiesById(Integer managerId) {
        String  sql ="select f.FUNCTION_NAME from \n" +
                "manager m\n" +
                "join permission  p on m.MANAGER_ID=p.MANAGER_ID\n" +
                "join `function` f on p.FUNCTION_ID =f.FUNCTION_ID\n" +
                "where m.MANAGER_ID = :managerId";
        Map<String,Object> map =new HashMap<>();
        map.put("managerId",managerId);
        List<ManagerAuthorities>  managerAuthoritiesList=namedParameterJdbcTemplate.query(sql, map, new RowMapper<ManagerAuthorities>() {
            @Override
            public ManagerAuthorities mapRow(ResultSet rs, int rowNum) throws SQLException {
                ManagerAuthorities managerAuthority =ManagerAuthorities.valueOf(rs.getString("FUNCTION_NAME"));
                return managerAuthority;
            }
        });
        return managerAuthoritiesList;
    }

    @Override
    public List<ManagerAuthorities> getManagerAuthoritiesByAccount(String account) {
        String sql ="select f.FUNCTION_NAME from \n" +
                "manager m\n" +
                "join permission  p on m.MANAGER_ID=p.MANAGER_ID\n" +
                "join `function` f on p.FUNCTION_ID =f.FUNCTION_ID\n" +
                "where m.MANAGER_ACCOUNT = ':account'";
        Map<String ,Object> map=new HashMap<>();
        map.put("account",account);
        List<ManagerAuthorities>  managerAuthoritiesList=namedParameterJdbcTemplate.query(sql, map, new RowMapper<ManagerAuthorities>() {
            @Override
            public ManagerAuthorities mapRow(ResultSet rs, int rowNum) throws SQLException {
                ManagerAuthorities managerAuthority =ManagerAuthorities.valueOf(rs.getString("FUNCTION_NAME"));
                return managerAuthority;
            }
        });
        return managerAuthoritiesList;
    }

    @Override
    public void deleteAllAuthoritiesById(Integer managerId) {
        String sql="delete from permission where MANAGER_ID = :managerId";
        Map<String ,Object> map=new HashMap<>();
        map.put("managerId",managerId);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void adjustPermission(Integer managerId,AdjustPermissionRequest adjustPermissionRequest) {
        String sql="INSERT INTO permission (MANAGER_ID, FUNCTION_ID)\n" +
                "SELECT :managerId, f.FUNCTION_ID\n" +
                "FROM `function` f\n" +
                "WHERE FUNCTION_NAME = :functionName " ;
        Map <String ,Object>[] map =new HashMap[adjustPermissionRequest.getAuthorities().size()];
        for(int i =0;i<map.length;i++){
            map[i]=new HashMap<>();
            map[i].put("managerId",managerId);
            map[i].put("functionName",adjustPermissionRequest.getAuthorities().get(i).toString());
        }
        namedParameterJdbcTemplate.batchUpdate(sql,map);
    }

    @Override
    public Integer getManagerIdByAccount(String account) {
        String sql ="select MANAGER_ID from manager where MANAGER_ACCOUNT =:account";
        Map<String,Object> map =new HashMap<>();
        map.put("account",account);
        List<Integer> managerIdList=namedParameterJdbcTemplate.query(sql, map, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("MANAGER_ID");
            }
        });
        if(managerIdList.size()>0)
            return managerIdList.get(0);
        return null;
    }

    @Override
    public String getPasswordById(Integer managerId) {
       String sql = "select MANAGER_PASSWORD from  manager where MANAGER_ID = :managerId";
       Map<String,Object> map =new HashMap<>();
       map.put("managerId",managerId);
       List<String> passwordList=namedParameterJdbcTemplate.query(sql, map, new RowMapper<String>() {
           @Override
           public String mapRow(ResultSet rs, int rowNum) throws SQLException {
               return rs.getString("MANAGER_PASSWORD");
           }
       });
       if(passwordList.size()>0)
           return passwordList.get(0);
       return null;
    }

    @Override
    public void updateManager(AdjustManagerRequest adjustManagerRequest) {
        String sql="UPDATE manager\n" +
                "SET MANAGER_ACCOUNT = :account,\n" +
                "    MANAGER_PASSWORD = :password,\n" +
                "    MANAGER_STATE= :state\n" +
                "WHERE MANAGER_ID = :managerId ; ";
        Map<String,Object>map =new HashMap<>();
        map.put("account",adjustManagerRequest.getManagerAccount());
        map.put("password",adjustManagerRequest.getManagerPassword());
        map.put("state",adjustManagerRequest.getManagerState());
        map.put("managerId",adjustManagerRequest.getManagerId());
        namedParameterJdbcTemplate.update(sql,map);
    }
}
