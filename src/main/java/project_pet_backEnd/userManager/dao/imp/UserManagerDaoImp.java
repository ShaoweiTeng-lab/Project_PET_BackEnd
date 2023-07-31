package project_pet_backEnd.userManager.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.user.rowMapper.UserRowMapper;
import project_pet_backEnd.user.vo.User;
import project_pet_backEnd.userManager.dao.UserManagerDao;
import project_pet_backEnd.userManager.dto.UserQueryParameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class UserManagerDaoImp implements UserManagerDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public List<User> getUsers(UserQueryParameter userQueryParameter) {
        String sql="select * from `USER` where 1=1 ";
        Map <String , Object> map =new HashMap<>();
        if(userQueryParameter.getSearch()!=null){
            sql =sql + " AND USER_NAME LIKE :search or USER_NickNAME LIKE :search";
            map.put("search","%" + userQueryParameter.getSearch()+ "%");
        }
        sql =sql +" ORDER BY  "+userQueryParameter.getOrder()+" "+userQueryParameter.getSort();
        List<User> userList=namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());
        return userList;
    }
}
