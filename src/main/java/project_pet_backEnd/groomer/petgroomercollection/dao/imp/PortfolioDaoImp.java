package project_pet_backEnd.groomer.petgroomercollection.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomercollection.dao.PortfolioDao;
import project_pet_backEnd.groomer.petgroomercollection.vo.Portfolio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PortfolioDaoImp implements PortfolioDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void insert(Portfolio rest) {
        String sql = "INSERT INTO  PORTFOLIO(" +
                "PG_ID," +
                "POR_TITLE," +
                "POR_TEXT," +
                "POR_UPLOAD)" +
                "VALUES(" +
                ":pgId," +
                ":porTitle," +
                ":porText," +
                ":porUpload)";
        Map<String, Object> map = new HashMap<>();
        map.put("porId", rest.getPorId());
        map.put("pgId", rest.getPgId());
        map.put("porTitle", rest.getPorTitle());
        map.put("porText", rest.getPorText());
        map.put("porUpload", rest.getPorUpload());
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void update(Portfolio rest) {
        String sql = "UPDATE PORTFOLIO SET " +
                "PG_ID = :pgId, " +
                "POR_TITLE = :porTitle, " +
                "POR_TEXT = :porText, " +
                "POR_UPLOAD = :porUpload " +
                "WHERE POR_ID = :porId";
        Map<String, Object> map = new HashMap<>();
        map.put("porId", rest.getPorId());
        map.put("pgId", rest.getPgId());
        map.put("porTitle", rest.getPorTitle());
        map.put("porText", rest.getPorText());
        map.put("porUpload", rest.getPorUpload());
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void delete(Portfolio rest) {
        String sql = "DELETE FROM PORTFOLIO WHERE POR_ID = :porId";
        Map<String, Object> map = new HashMap<>();
        map.put("porId", rest.getPorId());
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public Portfolio findById(Portfolio rest) {
        String sql = "select POR_ID, PG_ID, POR_TITLE, POR_TEXT, POR_UPLOAD from PORTFOLIO";
        Map<String, Object> map = new HashMap<>();
        if (rest.getPorId() != null) {
            sql += " WHERE POR_ID = :search";
            map.put("search", rest.getPorId());
        }
        List<Portfolio> list = namedParameterJdbcTemplate.query(sql, map, new RowMapper<Portfolio>() {
            @Override
            public Portfolio mapRow(ResultSet rs, int rowNum) throws SQLException {
                Portfolio rest = new Portfolio();
                rest.setPorId(rs.getInt("POR_ID"));
                rest.setPgId(rs.getInt("PG_ID"));
                rest.setPorTitle(rs.getString("POR_TITLE"));
                rest.setPorText(rs.getString("POR_TEXT"));
                rest.setPorUpload(rs.getDate("POR_UPLOAD"));
                return rest;
            }
        });
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Portfolio> list(PGQueryParameter PGQueryParameter) {
        String sql = "SELECT POR_ID, PG_ID, POR_TITLE, POR_TEXT, POR_UPLOAD FROM PORTFOLIO ";
        Map<String, Object> map = new HashMap<>();
        sql += "WHERE 1 = 1 ";
        if (PGQueryParameter.getSearch() != null) {
            sql += " AND POR_TITLE LIKE :search ";
            map.put("search", "%" + PGQueryParameter.getSearch() + "%");
        }
        if (PGQueryParameter.getPgId() != null) {
            sql += " AND PG_ID = :pgId ";
            map.put("pgId", PGQueryParameter.getPgId());
        }
        // Limit and Offset
        sql += "LIMIT :limit OFFSET :offset ";
        map.put("limit", PGQueryParameter.getLimit());
        map.put("offset", PGQueryParameter.getOffset());

        List<Portfolio> list = namedParameterJdbcTemplate.query(sql, map, new RowMapper<Portfolio>() {
            @Override
            public Portfolio mapRow(ResultSet rs, int rowNum) throws SQLException {
                Portfolio rest = new Portfolio();
                rest.setPorId(rs.getInt("POR_ID"));
                rest.setPgId(rs.getInt("PG_ID"));
                rest.setPorTitle(rs.getString("POR_TITLE"));
                rest.setPorText(rs.getString("POR_TEXT"));
                rest.setPorUpload(rs.getDate("POR_UPLOAD"));
                return rest;
            }
        });
        return list;
    }

    @Override
    public Integer count(PGQueryParameter PGQueryParameter) {
        String sql = "SELECT COUNT(*) AS total_count " +
                "FROM PORTFOLIO ";
        Map<String, Object> map = new HashMap<>();
        sql += "WHERE 1 = 1 ";
        if (PGQueryParameter.getSearch() != null) {
            sql += " AND POR_TITLE LIKE :search ";
            map.put("search", "%" + PGQueryParameter.getSearch() + "%");
        }
        if (PGQueryParameter.getPgId() != null) {
            sql += " AND PG_ID = :pgId ";
            map.put("pgId", PGQueryParameter.getPgId());
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
