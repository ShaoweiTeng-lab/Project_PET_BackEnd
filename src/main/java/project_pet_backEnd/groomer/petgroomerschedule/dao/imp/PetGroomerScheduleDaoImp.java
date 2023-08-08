package project_pet_backEnd.groomer.petgroomerschedule.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.groomer.appointment.vo.PetGroomerAppointment;
import project_pet_backEnd.groomer.petgroomerschedule.dao.PetGroomerScheduleDao;
import project_pet_backEnd.groomer.petgroomerschedule.dto.PGScheduleQueryParameter;
import project_pet_backEnd.groomer.petgroomerschedule.dto.PGScheduleSearchList;
import project_pet_backEnd.groomer.petgroomerschedule.vo.PetGroomerSchedule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class PetGroomerScheduleDaoImp implements PetGroomerScheduleDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void insertNewPgSchedule(PetGroomerSchedule petGroomerSchedule) {
        String sql = "INSERT INTO pet_groomer_schedule " +
                "(PG_ID,PGS_DATE,PGS_STATE)" +
                "VALUES (:pgId, :psgDate,:psgState)";
        Map<String,Object> map = new HashMap<>();
        map.put("pgId",petGroomerSchedule.getPgId());
        map.put("psgDate",petGroomerSchedule.getPgsDate());
        map.put("psgState",petGroomerSchedule.getPgsState());

        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void updatePgScheduleByPgsId(PetGroomerSchedule petGroomerSchedule) {
        String sql = "UPDATE pet_groomer_schedule " +
                "SET PG_ID=:pgId, " +
                "PGS_DATE=:psgDate, " +
                "PGS_STATE=:psgState " +
                "WHERE PGS_ID=:pgsId";
        Map<String, Object> map = new HashMap<>();

        map.put("pgId",petGroomerSchedule.getPgId());
        map.put("psgDate",petGroomerSchedule.getPgsDate());
        map.put("psgState",petGroomerSchedule.getPgsState());
        map.put("pgsId",petGroomerSchedule.getPgsId());
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public List<PGScheduleSearchList> getAllPgScheduleByPgId() {
        String sql = "SELECT pgs.PGS_ID, pgs.PG_ID, pgs.PGS_DATE, pgs.PGS_STATE, pg.PG_NAME " +
                "FROM PET_GROOMER_SCHEDULE pgs " +
                "JOIN PET_GROOMER pg ON pgs.PG_ID = pg.PG_ID ";
        Map<String, Object> map = new HashMap<>();

        List<PGScheduleSearchList> pgScheduleSearchListList = namedParameterJdbcTemplate.query(sql, map, new RowMapper<PGScheduleSearchList>() {
            @Override
            public PGScheduleSearchList mapRow(ResultSet rs, int rowNum) throws SQLException {
                PGScheduleSearchList pgScheduleSearchList = new PGScheduleSearchList();
                pgScheduleSearchList.setPgsId(rs.getInt("PGS_ID"));
                pgScheduleSearchList.setPgId(rs.getInt("PG_ID"));
                pgScheduleSearchList.setPgsDate(rs.getDate("PGS_DATE"));
                pgScheduleSearchList.setPgsState(rs.getString("PGS_STATE"));
                pgScheduleSearchList.setPgName(rs.getString("PG_NAME"));
                return pgScheduleSearchList;
            }
        });
        return pgScheduleSearchListList;
    }

    @Override
    public List<PGScheduleSearchList> getAllPgScheduleWithSearch(PGScheduleQueryParameter pgScheduleQueryParameter) {
        String sql = "SELECT pgs.PGS_ID, pgs.PG_ID, pgs.PGS_DATE, pgs.PGS_STATE, pg.PG_NAME " +
                "FROM PET_GROOMER_SCHEDULE pgs " +
                "JOIN PET_GROOMER pg ON pgs.PG_ID = pg.PG_ID " +
                "WHERE 1=1 ";

        Map<String, Object> map = new HashMap<>();

        if (pgScheduleQueryParameter.getSearch() != null) {
            sql += "AND (pg.PG_NAME LIKE :search OR pgs.PG_ID LIKE :search OR pgs.PGS_DATE LIKE :search) ";
            map.put("search", "%" + pgScheduleQueryParameter.getSearch() + "%");
        }

        // Sort
        if (pgScheduleQueryParameter.getOrder() != null) {
            String orderBy;
            switch (pgScheduleQueryParameter.getOrder()) {
                case PG_ID:
                    orderBy = "pgs.PG_ID";
                    break;
                case PGS_DATE:
                    orderBy = "pgs.PGS_DATE";
                    break;
                case PG_NAME:
                    orderBy = "pg.PG_NAME";
                    break;
                default:
                    orderBy = "pgs.PGS_ID"; // Default sorting by PGS_ID
            }
            sql += "ORDER BY " + orderBy + " ";
        }

        // Sort
        if (pgScheduleQueryParameter.getSort() != null) {
            sql += pgScheduleQueryParameter.getSort() + " ";
        }

        // Limit and Offset
        sql += "LIMIT :limit OFFSET :offset ";
        map.put("limit", pgScheduleQueryParameter.getLimit());
        map.put("offset", pgScheduleQueryParameter.getOffset());

        List<PGScheduleSearchList> petGroomerSchedulesList = namedParameterJdbcTemplate.query(sql, map, new RowMapper<PGScheduleSearchList>() {
            @Override
            public PGScheduleSearchList mapRow(ResultSet rs, int rowNum) throws SQLException {
                PGScheduleSearchList pgScheduleSearchList = new PGScheduleSearchList();
                pgScheduleSearchList.setPgsId(rs.getInt("PGS_ID"));
                pgScheduleSearchList.setPgId(rs.getInt("PG_ID"));
                pgScheduleSearchList.setPgsDate(rs.getDate("PGS_DATE"));
                pgScheduleSearchList.setPgsState(rs.getString("PGS_STATE"));
                pgScheduleSearchList.setPgName(rs.getString("PG_NAME"));
                return pgScheduleSearchList;
            }
        });

        return petGroomerSchedulesList;
    }


    @Override
    public List<PetGroomerSchedule> getPgScheduleByPgId(Integer pgId) {
        String sql = "SELECT PGS_ID, PG_ID, PGS_DATE, PGS_STATE " +
                "FROM PET_GROOMER_SCHEDULE " +
                "WHERE PG_ID = :pgId " +
                "ORDER BY PGS_DATE "; // Default sorting by PGS_DATE from earliest to latest

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pgId", pgId);

        List<PetGroomerSchedule> petGroomerSchedulesList = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(PetGroomerSchedule.class));
        return petGroomerSchedulesList;
    }

    @Override
    public List<PetGroomerSchedule> getAllPgScheduleRecentMonth(Integer pgId, java.sql.Date currentServerDate) {
        String sql = "SELECT PGS_ID, PG_ID, PGS_DATE, PGS_STATE " +
                "FROM PET_GROOMER_SCHEDULE " +
                "WHERE PG_ID = :pgId " +
                "AND PGS_DATE BETWEEN :startDate AND :endDate " +
                "ORDER BY PGS_DATE "; // Default sorting by PGS_DATE from earliest to latest

        // 計算一個月後的日期
        long oneMonthInMillis = 30L * 24L * 60L * 60L * 1000L;
        long endDateInMillis = currentServerDate.getTime() + oneMonthInMillis;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pgId", pgId);
        params.addValue("startDate", currentServerDate);
        params.addValue("endDate", new java.sql.Date(endDateInMillis));

        List<PetGroomerSchedule> petGroomerSchedulesList = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(PetGroomerSchedule.class));
        return petGroomerSchedulesList;
    }
}
