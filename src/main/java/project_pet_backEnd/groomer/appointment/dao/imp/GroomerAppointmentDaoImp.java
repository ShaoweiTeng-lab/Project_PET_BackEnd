package project_pet_backEnd.groomer.appointment.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.groomer.appointment.dao.GroomerAppointmentDao;
import project_pet_backEnd.groomer.appointment.dto.GroomerAppointmentQueryParameter;
import project_pet_backEnd.groomer.appointment.dto.response.PGAppointmentRes;
import project_pet_backEnd.groomer.appointment.vo.PetGroomerAppointment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class GroomerAppointmentDaoImp implements GroomerAppointmentDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public void insertNewAppointment(PetGroomerAppointment petGroomerAppointment) {
        String sql = "INSERT INTO PET_GROOMER_APPOINTMENT (PG_ID, USER_ID, PGA_DATE, PGA_TIME, PGA_STATE, PGA_OPTION, PGA_NOTES, PGA_PHONE) " +
                "VALUES (:pgId, :userId, :pgaDate, :pgaTime, :pgaState, :pgaOption, :pgaNotes, :pgaPhone)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pgId", petGroomerAppointment.getPgId());
        params.addValue("userId", petGroomerAppointment.getUserId());
        params.addValue("pgaDate", petGroomerAppointment.getPgaDate());
        params.addValue("pgaTime", petGroomerAppointment.getPgaTime());
        params.addValue("pgaState", petGroomerAppointment.getPgaState());
        params.addValue("pgaOption", petGroomerAppointment.getPgaOption());
        params.addValue("pgaNotes", petGroomerAppointment.getPgaNotes());
        params.addValue("pgaPhone", petGroomerAppointment.getPgaPhone());

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void updateAppointmentByPgaNo(PetGroomerAppointment petGroomerAppointment) {
        String sql = "UPDATE PET_GROOMER_APPOINTMENT " +
                "SET PG_ID = :pgId, " +
                "USER_ID = :userId, " +
                "PGA_DATE = :pgaDate, " +
                "PGA_TIME = :pgaTime, " +
                "PGA_STATE = :pgaState, " +
                "PGA_OPTION = :pgaOption, " +
                "PGA_NOTES = :pgaNotes, " +
                "PGA_PHONE = :pgaPhone " +
                "WHERE PGA_NO = :pgaNo";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pgId", petGroomerAppointment.getPgId());
        params.addValue("userId", petGroomerAppointment.getUserId());
        params.addValue("pgaDate", petGroomerAppointment.getPgaDate());
        params.addValue("pgaTime", petGroomerAppointment.getPgaTime());
        params.addValue("pgaState", petGroomerAppointment.getPgaState());
        params.addValue("pgaOption", petGroomerAppointment.getPgaOption());
        params.addValue("pgaNotes", petGroomerAppointment.getPgaNotes());
        params.addValue("pgaPhone", petGroomerAppointment.getPgaPhone());

        namedParameterJdbcTemplate.update(sql, params);

    }

    @Override
    public List<PGAppointmentRes> getAllAppointment() {
        String sql = "SELECT a.PGA_NO, a.PG_ID, a.USER_ID, a.PGA_DATE, a.PGA_TIME, a.PGA_STATE, a.PGA_OPTION, a.PGA_NOTES, a.PGA_PHONE, g.PG_NAME, u.USER_NAME " +
                "FROM PET_GROOMER_APPOINTMENT a " +
                "LEFT JOIN PET_GROOMER g ON a.PG_ID = g.PG_ID " +
                "LEFT JOIN USER u ON a.USER_ID = u.USER_ID " +
                "ORDER BY a.PGA_NO"; // Default sorting by PGA_NO

        MapSqlParameterSource params = new MapSqlParameterSource();

        List<PGAppointmentRes> pgAppointmentResList = namedParameterJdbcTemplate.query(sql, params, new RowMapper<PGAppointmentRes>() {
            @Override
            public PGAppointmentRes mapRow(ResultSet rs, int rowNum) throws SQLException {
                PGAppointmentRes pgAppointmentRes = new PGAppointmentRes();
                pgAppointmentRes.setPgaNo(rs.getInt("PGA_NO"));
                pgAppointmentRes.setPgId(rs.getInt("PG_ID"));
                pgAppointmentRes.setUserId(rs.getInt("USER_ID"));
                pgAppointmentRes.setPgaDate(rs.getDate("PGA_DATE"));
                pgAppointmentRes.setPgaTime(rs.getString("PGA_TIME"));
                pgAppointmentRes.setPgaState(rs.getByte("PGA_STATE"));
                pgAppointmentRes.setPgaOption(rs.getByte("PGA_OPTION"));
                pgAppointmentRes.setPgaNotes(rs.getString("PGA_NOTES"));
                pgAppointmentRes.setPgaPhone(rs.getString("PGA_PHONE"));
                pgAppointmentRes.setPgName(rs.getString("PG_NAME"));
                pgAppointmentRes.setUserName(rs.getString("USER_NAME"));
                return pgAppointmentRes;
            }
        });

        return pgAppointmentResList;
    }

    @Override
    public List<PGAppointmentRes> getAllAppointmentWithSearch(GroomerAppointmentQueryParameter groomerAppointmentQueryParameter) {
        String sql = "SELECT a.PGA_NO, a.PG_ID, a.USER_ID, a.PGA_DATE, a.PGA_TIME, a.PGA_STATE, a.PGA_OPTION, a.PGA_NOTES, a.PGA_PHONE, g.PG_NAME, u.USER_NAME " +
                "FROM PET_GROOMER_APPOINTMENT a " +
                "LEFT JOIN PET_GROOMER g ON a.PG_ID = g.PG_ID " +
                "LEFT JOIN USER u ON a.USER_ID = u.USER_ID " +
                "WHERE 1=1 ";

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (groomerAppointmentQueryParameter.getSearch() != null) {
            sql += "AND (u.USER_NAME LIKE :search OR a.USER_ID LIKE :search OR g.PG_ID LIKE :search OR g.PG_NAME LIKE :search " +
                    "OR a.PGA_NO LIKE :search OR a.PGA_DATE LIKE :search OR a.PGA_STATE LIKE :search) ";
            params.addValue("search", "%" + groomerAppointmentQueryParameter.getSearch() + "%");
        }

        // Sort
        if (groomerAppointmentQueryParameter.getOrder() != null) {
            String orderBy;
            switch (groomerAppointmentQueryParameter.getOrder()) {
                case PG_ID:
                    orderBy = "a.PG_ID";
                    break;
                case PGA_DATE:
                    orderBy = "a.PGA_DATE";
                    break;
                case PGA_STATE:
                    orderBy = "a.PGA_STATE";
                    break;
                case PG_NAME:
                    orderBy = "g.PG_NAME";
                    break;
                case USER_NAME:
                    orderBy="u.USER_NAME";
                    break;
                case USER_ID:
                    orderBy="a.USER_ID";
                    break;
                case PGA_OPTION:
                    orderBy="a.PGA_OPTION";
                    break;
                default:
                    orderBy = "a.PGA_NO"; // Default sorting by PGA_NO
            }
            sql += "ORDER BY " + orderBy + " ";
        }

        // Sort
        if (groomerAppointmentQueryParameter.getSort() != null) {
            sql += groomerAppointmentQueryParameter.getSort() + " ";
        }

        // Limit and Offset
        sql += "LIMIT :limit OFFSET :offset ";
        params.addValue("limit", groomerAppointmentQueryParameter.getLimit());
        params.addValue("offset", groomerAppointmentQueryParameter.getOffset());

        List<PGAppointmentRes> pgAppointmentResList = namedParameterJdbcTemplate.query(sql, params, new RowMapper<PGAppointmentRes>() {
            @Override
            public PGAppointmentRes mapRow(ResultSet rs, int rowNum) throws SQLException {
                PGAppointmentRes pgAppointmentRes = new PGAppointmentRes();
                pgAppointmentRes.setPgaNo(rs.getInt("PGA_NO"));
                pgAppointmentRes.setPgId(rs.getInt("PG_ID"));
                pgAppointmentRes.setUserId(rs.getInt("USER_ID"));
                pgAppointmentRes.setPgaDate(rs.getDate("PGA_DATE"));
                pgAppointmentRes.setPgaTime(rs.getString("PGA_TIME"));
                pgAppointmentRes.setPgaState(rs.getByte("PGA_STATE"));
                pgAppointmentRes.setPgaOption(rs.getByte("PGA_OPTION"));
                pgAppointmentRes.setPgaNotes(rs.getString("PGA_NOTES"));
                pgAppointmentRes.setPgaPhone(rs.getString("PGA_PHONE"));
                pgAppointmentRes.setPgName(rs.getString("PG_NAME"));
                pgAppointmentRes.setUserName(rs.getString("USER_NAME"));
                return pgAppointmentRes;
            }
        });

        return pgAppointmentResList;
    }

    @Override
    public List<PetGroomerAppointment> getAllAppointmentByPgId(Integer PgId) {
        String sql = "SELECT PGA_NO, PG_ID, USER_ID, PGA_DATE, PGA_TIME, PGA_STATE, PGA_OPTION, PGA_NOTES, PGA_PHONE " +
                "FROM PET_GROOMER_APPOINTMENT " +
                "WHERE PG_ID = :pgId " +
                "ORDER BY PGA_DATE"; // Default sorting by PGA_DATE

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pgId", PgId);

        List<PetGroomerAppointment> petGroomerAppointmentList = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(PetGroomerAppointment.class));
        return petGroomerAppointmentList;
    }
}
