package project_pet_backEnd.groomer.petgroomerschedule.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import project_pet_backEnd.groomer.petgroomerschedule.dao.PetGroomerScheduleDao;
import project_pet_backEnd.groomer.petgroomerschedule.dto.PGScheduleQueryParameter;
import project_pet_backEnd.groomer.petgroomerschedule.vo.PetGroomerSchedule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetGroomerScheduleDaoImp implements PetGroomerScheduleDao {

    @Autowired
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PetGroomerScheduleDaoImp(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

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
    public List<PetGroomerSchedule> getAllPgSchedule() {
        String sql="SELECT PGS_ID,PG_ID,PGS_DATE,PGS_STATE FROM all_dog_cat.pet_groomer_schedule";
        Map map = new HashMap<>();

        List<PetGroomerSchedule> allPgScheduleList=namedParameterJdbcTemplate.query(sql,map,new RowMapper<PetGroomerSchedule>(){
            @Override
            public PetGroomerSchedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                PetGroomerSchedule petGroomerSchedule = new PetGroomerSchedule();
                petGroomerSchedule.setPgsId(rs.getInt("PGS_ID"));
                petGroomerSchedule.setPgId(rs.getInt("PG_ID"));
                petGroomerSchedule.setPgsDate(rs.getDate("PGS_DATE"));
                petGroomerSchedule.setPgsState(rs.getString("PGS_STATE"));
                return petGroomerSchedule;
            }
        });
        return allPgScheduleList;
    }

    @Override
    public List<PetGroomerSchedule> getAllPgScheduleWithSearch(PGScheduleQueryParameter pgScheduleQueryParameter) {
        String sql = "SELECT PGS_ID, PG_ID, PGS_DATE, PGS_STATE " +
                "FROM PET_GROOMER_SCHEDULE " +
                "WHERE 1=1 ";

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (pgScheduleQueryParameter.getSearch() != null) {
            sql += "AND (PG_ID LIKE :search OR PGS_DATE LIKE :search) ";
            params.addValue("search", "%" + pgScheduleQueryParameter.getSearch() + "%");
        }

        // Sort
        if (pgScheduleQueryParameter.getOrder() != null) {
            String orderBy;
            switch (pgScheduleQueryParameter.getOrder()) {
                case PG_ID:
                    orderBy = "PG_ID";
                    break;
                case PGS_DATE:
                    orderBy = "PGS_DATE";
                    break;
                default:
                    orderBy = "PGS_ID"; // Default sorting by PGS_ID
            }
            sql += "ORDER BY " + orderBy + " ";
        }

        // Sort
        if (pgScheduleQueryParameter.getSort() != null) {
            sql += pgScheduleQueryParameter.getSort() + " ";
        }

        // Limit and Offset
        sql += "LIMIT :limit OFFSET :offset ";
        params.addValue("limit", pgScheduleQueryParameter.getLimit());
        params.addValue("offset", pgScheduleQueryParameter.getOffset());

        List<PetGroomerSchedule> petGroomerSchedulesList = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(PetGroomerSchedule.class));
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
}
