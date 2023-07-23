package project_pet_backEnd.userLogin.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.userLogin.dto.UserSignUpRequest;
import project_pet_backEnd.userLogin.model.IdentityProvider;
import project_pet_backEnd.userLogin.model.User;
import project_pet_backEnd.userLogin.rowMapper.UserRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class UserDao {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public void localSignUp(UserSignUpRequest userSignUpRequest){
        String sql ="INSERT INTO  USER(" +
                "USER_NAME," +
                "USER_NICKNAME," +
                "USER_GENDER," +
                "USER_EMAIL," +
                "USER_PASSWORD," +
                "USER_ADDRESS," +
                "USER_BIRTHDAY," +
                "USER_PROVIDER) " +
                "VALUES(" +
                ":userName," +
                ":userNickName," +
                ":userGender," +
                ":userEmail," +
                ":userPassword," +
                ":userAddress," +
                ":userBirthday," +
                ":identityProvider)";

        Map<String , Object> map =new HashMap<>();
        map.put("userName",userSignUpRequest.getUserName());
        map.put("userNickName",userSignUpRequest.getUserNickName());
        map.put("userGender",userSignUpRequest.getUserGender());
        map.put("userEmail",userSignUpRequest.getUserEmail());
        map.put("userPassword",userSignUpRequest.getUserPassword());
        map.put("userAddress",userSignUpRequest.getUserAddress());
        map.put("userBirthday",userSignUpRequest.getUserBirthday());
        map.put("identityProvider",userSignUpRequest.getIdentityProvider().toString());
        namedParameterJdbcTemplate.update(sql,map);

    }

    public User getUserByEmail(String email){
        String sql ="select * from USER where USER_EMAIL =:email";
        Map<String ,Object> map =new HashMap<>();
        map.put("email",email);
        List<User> userList =namedParameterJdbcTemplate.query(sql, map,new UserRowMapper());
        if(userList.size()>0)
            return  userList.get(0);
        return  null;
    }
}
