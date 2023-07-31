package project_pet_backEnd.userManager.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.user.rowMapper.UserRowMapper;
import project_pet_backEnd.user.vo.User;
import project_pet_backEnd.userManager.dao.UserManagerDao;
import project_pet_backEnd.userManager.dto.UserQueryParameter;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        //排序
        sql =sql +" ORDER BY  "+userQueryParameter.getOrder()+" "+userQueryParameter.getSort();
        //分頁
        sql =sql +" LIMIT :limit OFFSET :offset ";
        map.put("limit",userQueryParameter.getLimit());
        map.put("offset",userQueryParameter.getOffset());
        List<User> userList=namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());
        return userList;
    }

    @Override
    public Integer countUser(UserQueryParameter userQueryParameter) {
        String sql="select count(*) from `USER` where 1=1 ";
        Map <String , Object> map =new HashMap<>();
        if(userQueryParameter.getSearch()!=null){
            sql =sql + " AND USER_NAME LIKE :search or USER_NickNAME LIKE :search";
            map.put("search","%" + userQueryParameter.getSearch()+ "%");
        }
        Integer total =namedParameterJdbcTemplate.queryForObject(sql, map, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("count(*)");
            }
        });
        return total;
    }
}
