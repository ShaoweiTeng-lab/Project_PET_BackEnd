package project_pet_backEnd.groomer.petgroomercollection.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomercollection.dao.ChatDao;
import project_pet_backEnd.groomer.petgroomercollection.dao.UserRowMapper;
import project_pet_backEnd.groomer.petgroomercollection.vo.Chat;
import project_pet_backEnd.user.vo.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ChatDaoImp implements ChatDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void insert(Chat rest) {
        String sql = "INSERT INTO GROOMER_CHAT(" +
                "USER_ID," +
                "PG_ID," +
                "CHAT_TEXT," +
                "CHAT_STATUS," +
                "CHAT_CREATED)" +
                "VALUES(" +
                ":userId," +
                ":pgId," +
                ":chatText," +
                ":chatStatus," +
                ":chatCreated)";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", rest.getUserId());
        map.put("pgId", rest.getPgId());
        map.put("chatText", rest.getChatText());
        map.put("chatStatus", rest.getChatStatus());
        map.put("chatCreated", rest.getChatCreated());
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void update(Chat rest) {
        String sql = "UPDATE GROOMER_CHAT SET " +
                "USER_ID = :userId, " +
                "PG_ID = :pgId, " +
                "CHAT_TEXT = :chatText, " +
                "CHAT_STATUS = :chatStatus, " +
                "CHAT_CREATED = :chatCreated " +
                "WHERE CHAT_NO = :chatNo";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", rest.getUserId());
        map.put("pgId", rest.getPgId());
        map.put("chatText", rest.getChatText());
        map.put("chatStatus", rest.getChatStatus());
        map.put("chatCreated", rest.getChatCreated());
        map.put("chatNo", rest.getChatNo());
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void delete(Chat rest) {
        String sql = "DELETE FROM GROOMER_CHAT WHERE CHAT_NO = :chatNo";
        Map<String, Object> map = new HashMap<>();
        map.put("chatNo", rest.getChatNo());
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public List<Chat> list(PGQueryParameter PGQueryParameter) {
        String sql = "SELECT CHAT_NO, USER_ID, PG_ID, CHAT_TEXT, CHAT_STATUS, CHAT_CREATED FROM GROOMER_CHAT ";
        Map<String, Object> map = new HashMap<>();
        sql += " WHERE 1 = 1 ";
        if (PGQueryParameter.getUserId() != null) {
            sql += " AND USER_ID = :userId ";
            map.put("userId", PGQueryParameter.getUserId());
        }
        if (PGQueryParameter.getPgId() != null) {
            sql += " AND PG_ID = :pgId ";
            map.put("pgId", PGQueryParameter.getPgId());
        }
        if (PGQueryParameter.getSearch() != null) {
            sql += " AND CHAT_TEXT LIKE :search ";
            map.put("search", "%" + PGQueryParameter.getSearch() + "%");
        }
        sql += "ORDER BY CHAT_CREATED ASC ";

        List<Chat> list = namedParameterJdbcTemplate.query(sql, map, new RowMapper<Chat>() {
            @Override
            public Chat mapRow(ResultSet rs, int rowNum) throws SQLException {
                Chat rest = new Chat();
                rest.setChatNo(rs.getInt("CHAT_NO"));
                rest.setUserId(rs.getInt("USER_ID"));
                rest.setPgId(rs.getInt("PG_ID"));
                rest.setChatText(rs.getString("CHAT_TEXT"));
                rest.setChatStatus(rs.getString("CHAT_STATUS"));
                rest.setChatCreated(rs.getDate("CHAT_CREATED"));
                return rest;
            }
        });
        return list;
    }

    @Override
    public Integer count(PGQueryParameter PGQueryParameter) {
        String sql = "SELECT COUNT(*) AS total_count " +
                "FROM GROOMER_CHAT ";
        Map<String, Object> map = new HashMap<>();
        sql += " WHERE 1 = 1 ";
        if (PGQueryParameter.getUserId() != null) {
            sql += " AND USER_ID = :userId ";
            map.put("userId", PGQueryParameter.getUserId());
        }
        if (PGQueryParameter.getPgId() != null) {
            sql += " AND PG_ID = :pgId ";
            map.put("pgId", PGQueryParameter.getPgId());
        }
        if (PGQueryParameter.getSearch() != null) {
            sql += " AND CHAT_TEXT LIKE :search ";
            map.put("search", "%" + PGQueryParameter.getSearch() + "%");
        }
        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("total_count");
            }
        });
        return total;
    }

    public User getUserById(Integer id){
        String sql ="select * from USER where USER_ID =:id";
        Map<String ,Object> map =new HashMap<>();
        map.put("id",id);
        User user=namedParameterJdbcTemplate.queryForObject(sql, map,new UserRowMapper());
        return  user;
    }
}

