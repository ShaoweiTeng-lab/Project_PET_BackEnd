package project_pet_backEnd.groomer.petgroomercollection.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomercollection.dao.PortfolioCollectDao;
import project_pet_backEnd.groomer.petgroomercollection.vo.PortfolioCollect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PortfolioCollectDaoImp implements PortfolioCollectDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void insert(PortfolioCollect rest) {
        String sql = "INSERT INTO PORTFOLIO_COLLECT(" +
                "USER_ID," +
                "POR_ID," +
                "PC_CREATED)" +
                "VALUES(" +
                ":userId," +
                ":porId," +
                ":pcCreated)";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", rest.getUserId());
        map.put("porId", rest.getPorId());
        map.put("pcCreated", rest.getPcCreated());
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void update(PortfolioCollect rest) {
        String sql = "UPDATE PORTFOLIO_COLLECT SET " +
                "USER_ID = :userId, " +
                "POR_ID = :porId, " +
                "PC_CREATED = :pcCreated " +
                "WHERE PC_NO = :pcNo";
        Map<String, Object> map = new HashMap<>();
        map.put("pcNo", rest.getPcNo());
        map.put("userId", rest.getUserId());
        map.put("porId", rest.getPorId());
        map.put("pcCreated", rest.getPcCreated());
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void delete(PortfolioCollect rest) {
        String sql = "DELETE FROM PORTFOLIO_COLLECT WHERE PC_NO = :pcNo";
        Map<String, Object> map = new HashMap<>();
        map.put("pcNo", rest.getPcNo());
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public PortfolioCollect findById(PortfolioCollect rest) {
        String sql = "select PC_NO, USER_ID, POR_ID, PC_CREATED from PORTFOLIO_COLLECT ";
        Map<String, Object> map = new HashMap<>();
        if (rest.getPorId() != null) {
            sql += " WHERE POR_ID = :search";
            map.put("search", rest.getPorId());
        }
        List<PortfolioCollect> list = namedParameterJdbcTemplate.query(sql, map, new RowMapper<PortfolioCollect>() {
            @Override
            public PortfolioCollect mapRow(ResultSet rs, int rowNum) throws SQLException {
                PortfolioCollect rest = new PortfolioCollect();
                rest.setPcNo(rs.getInt("PC_NO"));
                rest.setUserId(rs.getInt("USER_ID"));
                rest.setPorId(rs.getInt("POR_ID"));
                rest.setPcCreated(rs.getDate("PC_CREATED"));
                return rest;
            }
        });
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<PortfolioCollect> list(PGQueryParameter PGQueryParameter) {
        String sql = "select PC_NO, USER_ID, POR_ID, PC_CREATED from PORTFOLIO_COLLECT ";
        Map<String, Object> map = new HashMap<>();
        sql += "WHERE 1 = 1 AND USER_ID is not null ";
        if (PGQueryParameter.getUserId() != null) {
            sql += " AND USER_ID = :userId ";
            map.put("userId", PGQueryParameter.getUserId());
        }
        // Limit and Offset
        sql += "LIMIT :limit OFFSET :offset ";
        map.put("limit", PGQueryParameter.getLimit());
        map.put("offset", PGQueryParameter.getOffset());

        List<PortfolioCollect> list = namedParameterJdbcTemplate.query(sql, map, new RowMapper<PortfolioCollect>() {
            @Override
            public PortfolioCollect mapRow(ResultSet rs, int rowNum) throws SQLException {
                PortfolioCollect rest = new PortfolioCollect();
                rest.setPcNo(rs.getInt("PC_NO"));
                rest.setUserId(rs.getInt("USER_ID"));
                rest.setPorId(rs.getInt("POR_ID"));
                rest.setPcCreated(rs.getDate("PC_CREATED"));
                return rest;
            }
        });
        return list;
    }

    @Override
    public Integer count(PGQueryParameter PGQueryParameter) {
        String sql = "SELECT COUNT(*) AS total_count " +
                "FROM PORTFOLIO_COLLECT ";
        Map<String, Object> map = new HashMap<>();
        sql += "WHERE 1 = 1 ";
        if (PGQueryParameter.getUserId() != null) {
            sql += " AND USER_ID = :userId ";
            map.put("userId", PGQueryParameter.getUserId());
        }
        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("total_count");
            }
        });
        return total;
    }
}
