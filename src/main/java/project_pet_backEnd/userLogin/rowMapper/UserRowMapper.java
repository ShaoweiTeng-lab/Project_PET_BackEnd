package project_pet_backEnd.userLogin.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import project_pet_backEnd.userLogin.model.IdentityProvider;
import project_pet_backEnd.userLogin.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper  implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user=new User();
        user.setUserId(rs.getInt("USER_ID"));
        user.setUserName(rs.getString("USER_NAME"));
        user.setUserNickName(rs.getString("USER_NICKNAME"));
        user.setUserGender(rs.getInt("USER_GENDER"));
        user.setUserEmail(rs.getString("USER_EMAIL"));
        user.setUserPassword(rs.getString("USER_PASSWORD"));
        user.setUserAddress(rs.getString("USER_ADDRESS"));
        user.setUserBirthday(rs.getDate("USER_BIRTHDAY"));
        user.setUserPoint(rs.getInt("USER_POINT"));
        user.setUserPic(rs.getBytes("USER_PIC"));
        user.setIdentityProvider(IdentityProvider.valueOf(rs.getString("USER_PROVIDER")) );
        user.setUserCreated(rs.getTimestamp("USER_CREATED"));
        return user;
    }
}
