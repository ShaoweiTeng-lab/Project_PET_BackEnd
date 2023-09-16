package project_pet_backEnd.groomer.groomerworkmanager.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import project_pet_backEnd.groomer.groomerworkmanager.dao.PictureInfoDao;
import project_pet_backEnd.groomer.groomerworkmanager.vo.PictureInfo;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.vo.PetGroomer;
import project_pet_backEnd.groomer.petgroomercollection.dao.PortfolioDao;
import project_pet_backEnd.groomer.petgroomercollection.vo.Portfolio;
import project_pet_backEnd.groomer.petgroomercollection.vo.PortfolioCollect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PictureInfoDaoImp implements PictureInfoDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void insert(PictureInfo rest) {
        String sql = "INSERT INTO  PICTURE_INFO(" +
                "POR_ID," +
                "PI_PICTURE," +
                "PI_DATE)" +
                "VALUES(" +
                ":porId," +
                ":piPicture," +
                ":piDate)";
        Map<String, Object> map = new HashMap<>();
        map.put("porId", rest.getPorId());
        map.put("piPicture", rest.getPiPicture());
        map.put("piDate", rest.getPiDate());
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void update(PictureInfo rest) {
        String sql = "UPDATE PICTURE_INFO SET " +
                "PI_PICTURE = :piPicture, " +
                "PI_DATE = :piDate " +
                "WHERE POR_ID = :porId";
        Map<String, Object> map = new HashMap<>();
        map.put("porId", rest.getPorId());
        map.put("piPicture", rest.getPiPicture());
        map.put("piDate", rest.getPiDate());
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void delete(PictureInfo rest) {
        String sql = "DELETE FROM PICTURE_INFO WHERE PI_NO = :piNo";
        Map<String, Object> map = new HashMap<>();
        map.put("piNo", rest.getPiNo());
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public PictureInfo findById(PictureInfo rest) {
        String sql = "select PI_NO, POR_ID, PI_PICTURE, PI_DATE from PICTURE_INFO";
        Map<String, Object> map = new HashMap<>();
        if (rest.getPiNo() != null) {
            sql += " WHERE PI_NO = :search ";
            map.put("search", rest.getPiNo());
        }
        List<PictureInfo> list = namedParameterJdbcTemplate.query(sql, map, new RowMapper<PictureInfo>() {
            @Override
            public PictureInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                PictureInfo rest = new PictureInfo();
                rest.setPiNo(rs.getInt("PI_NO"));
                rest.setPorId(rs.getInt("POR_ID"));
                rest.setPiPicture(rs.getBytes("PI_PICTURE"));
                rest.setPiDate(rs.getDate("PI_DATE"));
                return rest;
            }
        });
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<PictureInfo> list(PGQueryParameter PGQueryParameter) {
        String sql = "select PI_NO, POR_ID, PI_PICTURE, PI_DATE from PICTURE_INFO";
        Map<String, Object> map = new HashMap<>();
        if (PGQueryParameter.getSearch() != null) {
            sql += " WHERE POR_ID LIKE :search ";
            map.put("search", PGQueryParameter.getPorId());
        }
        // Limit and Offset
        sql += "LIMIT :limit OFFSET :offset ";
        map.put("limit", PGQueryParameter.getLimit());
        map.put("offset", PGQueryParameter.getOffset());

        List<PictureInfo> list = namedParameterJdbcTemplate.query(sql, map, new RowMapper<PictureInfo>() {
            @Override
            public PictureInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                PictureInfo rest = new PictureInfo();
                rest.setPiNo(rs.getInt("PI_NO"));
                rest.setPorId(rs.getInt("POR_ID"));
                rest.setPiPicture(rs.getBytes("PI_PICTURE"));
                rest.setPiDate(rs.getDate("PI_DATE"));
                return rest;
            }
        });
        return list;
    }

    @Override
    public List<PictureInfo> getAllPicture() {
        String sql = "select PI_NO, POR_ID, PI_PICTURE, PI_DATE from PICTURE_INFO";
        Map map = new HashMap<>();
        List<PictureInfo> petGroomerAllList = namedParameterJdbcTemplate.query(sql, map, new RowMapper<PictureInfo>() {
            @Override
            public PictureInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                PictureInfo rest = new PictureInfo();
                rest.setPiNo(rs.getInt("PI_NO"));
                rest.setPorId(rs.getInt("POR_ID"));
                rest.setPiPicture(rs.getBytes("PI_PICTURE"));
                rest.setPiDate(rs.getDate("PI_DATE"));
                return rest;
            }
        });
        return petGroomerAllList;
    }

    @Override
    public Integer count(PGQueryParameter PGQueryParameter) {
        String sql = "SELECT COUNT(*) AS total_count " +
                "FROM PICTURE_INFO ";
        Map<String, Object> map = new HashMap<>();
        if (PGQueryParameter.getSearch() != null) {
            sql += " WHERE POR_ID LIKE :search ";
            map.put("search", PGQueryParameter.getPorId());
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

