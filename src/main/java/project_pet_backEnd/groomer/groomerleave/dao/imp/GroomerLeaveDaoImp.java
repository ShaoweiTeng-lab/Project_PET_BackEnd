package project_pet_backEnd.groomer.groomerleave.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.groomer.groomerleave.dao.GroomerLeaveDao;
import project_pet_backEnd.groomer.groomerleave.dto.GroomerLeaveQueryParameter;
import project_pet_backEnd.groomer.groomerleave.dto.response.PGLeaveSearchRes;
import project_pet_backEnd.groomer.groomerleave.vo.GroomerLeave;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class GroomerLeaveDaoImp implements GroomerLeaveDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void insertNewGroomerLeave(GroomerLeave groomerLeave) {
        String sql = "INSERT INTO GROOMER_LEAVE (PG_ID, LEAVE_CREATED, LEAVE_DATE, LEAVE_TIME, LEAVE_STATE) " +
                "VALUES (:pgId, :leaveCreated, :leaveDate, :leaveTime, :leaveState)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pgId", groomerLeave.getPgId());
        params.addValue("leaveCreated", groomerLeave.getLeaveCreated());
        params.addValue("leaveDate", groomerLeave.getLeaveDate());
        params.addValue("leaveTime", groomerLeave.getLeaveTime());
        params.addValue("leaveState", groomerLeave.getLeaveState());

        namedParameterJdbcTemplate.update(sql, params);

    }

    @Override
    public void updateGroomerLeaveByLeaveNo(GroomerLeave groomerLeave) {
        String sql = "UPDATE GROOMER_LEAVE " +
                "SET PG_ID = :pgId, " +
                "LEAVE_CREATED = :leaveCreated, " +
                "LEAVE_DATE = :leaveDate, " +
                "LEAVE_TIME = :leaveTime, " +
                "LEAVE_STATE = :leaveState " +
                "WHERE LEAVE_NO = :leaveNo";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pgId", groomerLeave.getPgId());
        params.addValue("leaveCreated", groomerLeave.getLeaveCreated());
        params.addValue("leaveDate", groomerLeave.getLeaveDate());
        params.addValue("leaveTime", groomerLeave.getLeaveTime());
        params.addValue("leaveState", groomerLeave.getLeaveState());
        params.addValue("leaveNo", groomerLeave.getLeaveNo());

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public List<PGLeaveSearchRes> getAllGroomerLeave() {
        String sql = "SELECT gl.LEAVE_NO, gl.PG_ID, gl.LEAVE_CREATED, gl.LEAVE_DATE, gl.LEAVE_TIME, gl.LEAVE_STATE, pg.PG_NAME " +
                "FROM GROOMER_LEAVE gl " +
                "JOIN PET_GROOMER pg ON gl.PG_ID = pg.PG_ID";
        MapSqlParameterSource params = new MapSqlParameterSource();
        List<PGLeaveSearchRes> allGroomerLeaveList = namedParameterJdbcTemplate.query(sql, params, new RowMapper<PGLeaveSearchRes>() {
            @Override
            public PGLeaveSearchRes mapRow(ResultSet rs, int rowNum) throws SQLException {
                PGLeaveSearchRes pgLeaveSearchRes = new PGLeaveSearchRes();
                pgLeaveSearchRes.setLeaveNo(rs.getInt("LEAVE_NO"));
                pgLeaveSearchRes.setPgId(rs.getInt("PG_ID"));
                pgLeaveSearchRes.setLeaveCreated(rs.getDate("LEAVE_CREATED"));
                pgLeaveSearchRes.setLeaveDate(rs.getDate("LEAVE_DATE"));
                pgLeaveSearchRes.setLeaveTime(rs.getString("LEAVE_TIME"));
                pgLeaveSearchRes.setLeaveState(rs.getInt("LEAVE_STATE"));
                pgLeaveSearchRes.setPgName(rs.getString("PG_NAME"));
                return pgLeaveSearchRes;
            }
        });
        return allGroomerLeaveList;
    }

    @Override
    public List<PGLeaveSearchRes> getAllGroomerLeaveWithSearch(GroomerLeaveQueryParameter groomerLeaveQueryParameter) {
        String sql = "SELECT gl.LEAVE_NO, gl.PG_ID, gl.LEAVE_CREATED, gl.LEAVE_DATE, gl.LEAVE_TIME, gl.LEAVE_STATE, pg.PG_NAME " +
                "FROM GROOMER_LEAVE gl " +
                "JOIN PET_GROOMER pg ON gl.PG_ID = pg.PG_ID " +
                "WHERE 1=1 ";

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (groomerLeaveQueryParameter.getSearch() != null) {
            sql += "AND (pg.PG_NAME LIKE :search OR gl.PG_ID LIKE :search OR gl.LEAVE_DATE LIKE :search OR gl.LEAVE_CREATED LIKE :search) ";
            params.addValue("search", "%" + groomerLeaveQueryParameter.getSearch() + "%");
        }

        // Sort
        if (groomerLeaveQueryParameter.getOrder() != null) {
            String orderBy;
            switch (groomerLeaveQueryParameter.getOrder()) {
                case PG_ID:
                    orderBy = "gl.PG_ID";
                    break;
                case LEAVE_DATE:
                    orderBy = "gl.LEAVE_DATE";
                    break;
                case LEAVE_CREATED:
                    orderBy = "gl.LEAVE_CREATED";
                    break;
                case LEAVE_STATE:
                    orderBy = "gl.LEAVE_STATE";
                    break;
                case PG_NAME:
                    orderBy = "pg.PG_NAME";
                    break;
                default:
                    orderBy = "gl.LEAVE_NO"; // Default sorting by LEAVE_NO
            }
            sql += "ORDER BY " + orderBy + " ";
        }

        // Sort
        if (groomerLeaveQueryParameter.getSort() != null) {
            sql += groomerLeaveQueryParameter.getSort() + " ";
        }

        // Limit and Offset
        sql += "LIMIT :limit OFFSET :offset ";
        params.addValue("limit", groomerLeaveQueryParameter.getLimit());
        params.addValue("offset", groomerLeaveQueryParameter.getOffset());

        List<PGLeaveSearchRes> pgLeaveSearchResList = namedParameterJdbcTemplate.query(sql, params, new RowMapper<PGLeaveSearchRes>() {
            @Override
            public PGLeaveSearchRes mapRow(ResultSet rs, int rowNum) throws SQLException {
                PGLeaveSearchRes pgLeaveSearchRes = new PGLeaveSearchRes();
                pgLeaveSearchRes.setLeaveNo(rs.getInt("LEAVE_NO"));
                pgLeaveSearchRes.setPgId(rs.getInt("PG_ID"));
                pgLeaveSearchRes.setLeaveCreated(rs.getDate("LEAVE_CREATED"));
                pgLeaveSearchRes.setLeaveDate(rs.getDate("LEAVE_DATE"));
                pgLeaveSearchRes.setLeaveTime(rs.getString("LEAVE_TIME"));
                pgLeaveSearchRes.setLeaveState(rs.getInt("LEAVE_STATE"));
                pgLeaveSearchRes.setPgName(rs.getString("PG_NAME"));
                return pgLeaveSearchRes;
            }
        });

        return pgLeaveSearchResList;
    }

    @Override
    public List<GroomerLeave> getGroomerLeaveByPgId(Integer pgId) {
        String sql = "SELECT LEAVE_NO, PG_ID, LEAVE_CREATED, LEAVE_DATE, LEAVE_TIME, LEAVE_STATE " +
                "FROM GROOMER_LEAVE " +
                "WHERE PG_ID = :pgId " +
                "ORDER BY LEAVE_DATE "; // Default sorting by LEAVE_DATE from earliest to latest

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pgId", pgId);

        List<GroomerLeave> groomerLeaveList = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(GroomerLeave.class));
        return groomerLeaveList;
    }
}

