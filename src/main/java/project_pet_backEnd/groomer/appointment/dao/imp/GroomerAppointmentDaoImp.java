package project_pet_backEnd.groomer.appointment.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.groomer.appointment.dao.GroomerAppointmentDao;
import project_pet_backEnd.groomer.appointment.dto.AppointmentListForUser;
import project_pet_backEnd.groomer.appointment.dto.GroomerAppointmentQueryParameter;
import project_pet_backEnd.groomer.appointment.dto.UserAppoQueryParameter;
import project_pet_backEnd.groomer.appointment.dto.response.PGAppointmentRes;
import project_pet_backEnd.groomer.appointment.dto.response.UserPhAndNameRes;
import project_pet_backEnd.groomer.appointment.vo.PetGroomerAppointment;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        params.addValue("pgaNo",petGroomerAppointment.getPgaNo());
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
                pgAppointmentRes.setPgaState(rs.getInt("PGA_STATE"));
                pgAppointmentRes.setPgaOption(rs.getInt("PGA_OPTION"));
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
                pgAppointmentRes.setPgaState(rs.getInt("PGA_STATE"));
                pgAppointmentRes.setPgaOption(rs.getInt("PGA_OPTION"));
                pgAppointmentRes.setPgaNotes(rs.getString("PGA_NOTES"));
                pgAppointmentRes.setPgaPhone(rs.getString("PGA_PHONE"));
                pgAppointmentRes.setPgName(rs.getString("PG_NAME"));
                pgAppointmentRes.setUserName(rs.getString("USER_NAME"));
                return pgAppointmentRes;
            }
        });

        return pgAppointmentResList;
    }

    ////計算搜尋預約單總筆數
    @Override
    public Integer countAllAppointmentWithSearch(GroomerAppointmentQueryParameter groomerAppointmentQueryParameter) {
        String sql = "SELECT COUNT(*) " +
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

        Integer totalCount = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        return totalCount;
    }

    @Override
    public List<PetGroomerAppointment> getAllAppointmentByPgId(Integer pgId) {
        String sql = "SELECT PGA_NO, PG_ID, USER_ID, PGA_DATE, PGA_TIME, PGA_STATE, PGA_OPTION, PGA_NOTES, PGA_PHONE " +
                "FROM PET_GROOMER_APPOINTMENT " +
                "WHERE PG_ID = :pgId " +
                "ORDER BY PGA_DATE"; // Default sorting by PGA_DATE

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pgId", pgId);

        List<PetGroomerAppointment> petGroomerAppointmentList = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(PetGroomerAppointment.class));
        return petGroomerAppointmentList;
    }
    /*
     * GroomerAppointmentServiceImp.getAllGroomersForAppointment 使用。提供進入預約頁面的使用者預先填寫電話。
     * 姓名單純顯示。不可修改。
     */
    @Override
    public UserPhAndNameRes getUserPhAndNameForAppointment(Integer userId) {
        String sql = "SELECT USER_NAME, USER_PHONE " +
                "FROM USER " +
                "WHERE USER_ID = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        List<UserPhAndNameRes>  userPhAndNameResList= namedParameterJdbcTemplate.query(sql, map, new RowMapper<UserPhAndNameRes>() {
            @Override
            public UserPhAndNameRes mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserPhAndNameRes userPhAndNameRes = new  UserPhAndNameRes();
                userPhAndNameRes.setUserName(rs.getString("USER_NAME"));
                userPhAndNameRes.setUserPh(rs.getString("USER_PHONE"));
                return userPhAndNameRes;
            }
        });

        if(userPhAndNameResList.size()>0){
            return userPhAndNameResList.get(0);
        }
        return null;
    }

    /*
     *查詢使用者所有預約單ByUserId。
     */
    @Override
    public List<AppointmentListForUser> getAppointmentForUserByUserId(Integer userId, UserAppoQueryParameter userAppoQueryParameter) {
        String sql = "SELECT PGA_NO, PGA_DATE, PGA_TIME, PGA_STATE, PGA_OPTION, PGA_NOTES, PGA_PHONE, USER_NAME, PG_NAME, PG_GENDER, PG_PIC " +
                "FROM all_dog_cat.pet_groomer_appointment pga " +
                "JOIN `user` ON pga.USER_ID = `user`.USER_ID " +
                "JOIN pet_groomer ON pga.PG_ID = pet_groomer.PG_ID " +
                "WHERE pga.USER_ID = :userId ";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        //sort
        String orderBy = "PGA_DATE";
        if (userAppoQueryParameter.getSort() != null) {
            orderBy += " " + userAppoQueryParameter.getSort(); // Add "ASC" or "DESC" to orderBy
        }
        sql += "ORDER BY " + orderBy + " ";

        // Limit and Offset
        sql += "LIMIT :limit OFFSET :offset ";
        map.put("limit", userAppoQueryParameter.getLimit());
        map.put("offset", userAppoQueryParameter.getOffset());

        List<AppointmentListForUser>  appListForUser= namedParameterJdbcTemplate.query(sql, map, new RowMapper<AppointmentListForUser>() {
            @Override
            public AppointmentListForUser mapRow(ResultSet rs, int rowNum) throws SQLException {
                AppointmentListForUser appList = new  AppointmentListForUser();
                appList.setPgaNo(rs.getInt("PGA_NO"));
                appList.setPgaDate(rs.getDate("PGA_DATE"));
                appList.setPgaTime(rs.getString("PGA_TIME"));
                appList.setPgaState(rs.getInt("PGA_STATE"));
                appList.setPgaOption(rs.getInt("PGA_OPTION"));
                appList.setPgaNotes(rs.getString("PGA_NOTES"));
                appList.setPgaPhone(rs.getString("PGA_PHONE"));
                appList.setUserName(rs.getString("USER_NAME"));
                appList.setPgName(rs.getString("PG_NAME"));
                appList.setPgGender(rs.getInt("PG_GENDER"));
                appList.setPgPic(rs.getBytes("PG_PIC"));
                return appList;
            }
        });
        return appListForUser;
    }

    /*
     *查詢使用者所有預約單數量ByUserId 方便計算頁數。
     */
    @Override
    public Integer countAppointmentByUserId(Integer userId) {
        String sql = "SELECT COUNT(*) AS total_count " +
                "FROM all_dog_cat.pet_groomer_appointment " +
                "WHERE USER_ID = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        Integer totalCount = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
        return totalCount;
    }

    @Override
    public PetGroomerAppointment getAppointmentByPgaNo(Integer pgaNo) {
        String sql ="SELECT PGA_NO,PG_ID,USER_ID,PGA_DATE,PGA_TIME,PGA_STATE,PGA_OPTION,PGA_NOTES,PGA_PHONE\n" +
                    "FROM all_dog_cat.pet_groomer_appointment pga WHERE pga.PGA_NO= :pgaNo";
        Map<String, Object> map = new HashMap<>();
        map.put("pgaNo", pgaNo);
        List<PetGroomerAppointment>  appListByPgaNo= namedParameterJdbcTemplate.query(sql, map, new RowMapper<PetGroomerAppointment>() {
            @Override
            public PetGroomerAppointment mapRow(ResultSet rs, int rowNum) throws SQLException {
                PetGroomerAppointment appList = new  PetGroomerAppointment();
                appList.setPgaNo(rs.getInt("PGA_NO"));
                appList.setPgId(rs.getInt("PG_ID"));
                appList.setUserId(rs.getInt("USER_ID"));
                appList.setPgaDate(rs.getDate("PGA_DATE"));
                appList.setPgaTime(rs.getString("PGA_TIME"));
                appList.setPgaState(rs.getInt("PGA_STATE"));
                appList.setPgaOption(rs.getInt("PGA_OPTION"));
                appList.setPgaNotes(rs.getString("PGA_NOTES"));
                appList.setPgaPhone(rs.getString("PGA_PHONE"));
                return appList;
            }
        });

        if(appListByPgaNo.size()>0){
            return appListByPgaNo.get(0);
        }
        return null;
    }
    @Override
    public List<PetGroomerAppointment> getAppointmentByPgIdAndDate(Integer pgId, Date date) {
        String sql ="SELECT PGA_NO,PG_ID,USER_ID,PGA_DATE,PGA_TIME,PGA_STATE,PGA_OPTION,PGA_NOTES,PGA_PHONE\n" +
                "FROM all_dog_cat.pet_groomer_appointment pga WHERE pga.PG_ID= :pgId AND pga.PGA_DATE= :date";
        Map<String, Object> map = new HashMap<>();
        map.put("pgId", pgId);
        map.put("date", date);
        List<PetGroomerAppointment>  appList= namedParameterJdbcTemplate.query(sql, map, new RowMapper<PetGroomerAppointment>() {
            @Override
            public PetGroomerAppointment mapRow(ResultSet rs, int rowNum) throws SQLException {
                PetGroomerAppointment appList = new  PetGroomerAppointment();
                appList.setPgaNo(rs.getInt("PGA_NO"));
                appList.setPgId(rs.getInt("PG_ID"));
                appList.setUserId(rs.getInt("USER_ID"));
                appList.setPgaDate(rs.getDate("PGA_DATE"));
                appList.setPgaTime(rs.getString("PGA_TIME"));
                appList.setPgaState(rs.getInt("PGA_STATE"));
                appList.setPgaOption(rs.getInt("PGA_OPTION"));
                appList.setPgaNotes(rs.getString("PGA_NOTES"));
                appList.setPgaPhone(rs.getString("PGA_PHONE"));
                return appList;
            }
        });
        if(appList.isEmpty()){
            return null;
        }
        return appList;
    }
}
