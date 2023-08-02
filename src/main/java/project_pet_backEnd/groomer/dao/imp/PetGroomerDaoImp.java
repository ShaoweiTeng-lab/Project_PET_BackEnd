package project_pet_backEnd.groomer.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.groomer.dao.PetGroomerDao;
import project_pet_backEnd.groomer.dto.ManagerGetByFunctionIdRequest;
import project_pet_backEnd.groomer.vo.PetGroomer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class PetGroomerDaoImp implements PetGroomerDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<ManagerGetByFunctionIdRequest> getManagerByFunctionId(Integer functionId) {
        String sql="SELECT m.MANAGER_ID,m.MANAGER_ACCOUNT FROM all_dog_cat.manager m\n" +
                "join permission on m.MANAGER_ID = permission.manager_id\n" +
                "join `function` on `function`.function_id = permission.function_id\n" +
                "where permission.FUNCTION_ID = :functionId and MANAGER_STATE = 1";
        Map<String , Object> map =new HashMap<>();
        map.put("functionId", functionId);
        List<ManagerGetByFunctionIdRequest> rsList=namedParameterJdbcTemplate.query(sql, map, new RowMapper<ManagerGetByFunctionIdRequest>() {
            @Override
            public ManagerGetByFunctionIdRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
                ManagerGetByFunctionIdRequest managerGetByFunctionIdRequest =new ManagerGetByFunctionIdRequest(rs.getInt("manager_id"),rs.getString("manager_account"));
                return managerGetByFunctionIdRequest;
            }
        });
        return rsList;
    }
    @Override
    public void insertGroomer(PetGroomer petGroomer) {
        String sql ="INSERT INTO  PET_GROOMER(" +
                "MAN_ID," +
                "PG_NAME," +
                "PG_GENDER," +
                "PG_PIC,"+
                "PG_EMAIL," +
                "PG_PH," +
                "PG_ADDRESS," +
                "PG_BIRTHDAY)" +
                "VALUES(" +
                ":manID," +
                ":pgName," +
                ":pgGender," +
                ":pgPic," +
                ":pgEmail," +
                ":pgPh," +
                ":pgAddress," +
                ":pgBirthday)";
        Map<String,Object> map = new HashMap<>();
        map.put("manID",petGroomer.getManId());
        map.put("pgName",petGroomer.getPgName());
        map.put("pgGender",petGroomer.getPgGender());
        map.put("pgPic",petGroomer.getPgPic());
        map.put("pgEmail",petGroomer.getPgEmail());
        map.put("pgPh",petGroomer.getPgPh());
        map.put("pgAddress",petGroomer.getPgAddress());
        map.put("pgBirthday",petGroomer.getPgBirthday());

        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public PetGroomer getPetGroomerByManId(Integer manId) {

        String sql = "select PG_ID, MAN_ID, PG_NAME, PG_GENDER, PG_PIC, PG_EMAIL, PG_PH, PG_ADDRESS, PG_BIRTHDAY from pet_groomer\n" +
                "join manager on pet_groomer.MAN_ID = manager.manager_id\n" +
                "where MAN_ID = :manId and MANAGER_STATE = 1";
        Map map = new HashMap<>();
        map.put("manId",manId);
        List<PetGroomer> petGroomerList = namedParameterJdbcTemplate.query(sql, map, new RowMapper<PetGroomer>() {
            @Override
            public PetGroomer mapRow(ResultSet rs, int rowNum) throws SQLException {
                PetGroomer petGroomer =  new PetGroomer();
                petGroomer.setPgId(rs.getInt("PG_ID"));
                petGroomer.setManId(rs.getInt("MAN_ID"));
                petGroomer.setPgName(rs.getString("PG_NAME"));
                petGroomer.setPgGender(rs.getInt("PG_GENDER"));
                petGroomer.setPgPic(rs.getBytes("PG_PIC"));
                petGroomer.setPgEmail(rs.getString("PG_EMAIL"));
                petGroomer.setPgPh(rs.getString("PG_PH"));
                petGroomer.setPgAddress(rs.getString("PG_ADDRESS"));
                petGroomer.setPgBirthday(rs.getDate("PG_BIRTHDAY"));
                return petGroomer;
            }
        });
        if(petGroomerList.size()>0){
            return petGroomerList.get(0);
        }
        return null;
    }

    @Override
    public List<PetGroomer> getAllGroomer() {
        String sql = "select PG_ID, MAN_ID, PG_NAME, PG_GENDER, PG_PIC, PG_EMAIL, PG_PH, PG_ADDRESS, PG_BIRTHDAY from pet_groomer\n" +
                "join manager on pet_groomer.MAN_ID = manager.manager_id\n" +
                "where MANAGER_STATE = 1;";
        Map map = new HashMap<>();
        List<PetGroomer> petGroomerAllList = namedParameterJdbcTemplate.query(sql, map, new RowMapper<PetGroomer>() {
            @Override
            public PetGroomer mapRow(ResultSet rs, int rowNum) throws SQLException {
                PetGroomer petGroomer =  new PetGroomer();
                petGroomer.setPgId(rs.getInt("PG_ID"));
                petGroomer.setManId(rs.getInt("MAN_ID"));
                petGroomer.setPgName(rs.getString("PG_NAME"));
                petGroomer.setPgGender(rs.getInt("PG_GENDER"));
                petGroomer.setPgPic(rs.getBytes("PG_PIC"));
                petGroomer.setPgEmail(rs.getString("PG_EMAIL"));
                petGroomer.setPgPh(rs.getString("PG_PH"));
                petGroomer.setPgAddress(rs.getString("PG_ADDRESS"));
                petGroomer.setPgBirthday(rs.getDate("PG_BIRTHDAY"));
                return petGroomer;
            }
        });
        return petGroomerAllList;
    }


    @Override
    public void updateGroomerById(PetGroomer petGroomer) {
        String sql = "UPDATE pet_groomer SET PG_NAME = :pgName, PG_GENDER = :pgGender, PG_PIC = :pgPic, PG_EMAIL = :pgEmail, PG_PH = :pgPh, PG_ADDRESS = :pgAddress, PG_BIRTHDAY = :pgBirthday WHERE PG_ID = :pgId";

        Map<String, Object> map = new HashMap<>();
        map.put("pgId", petGroomer.getPgId());
        map.put("pgName", petGroomer.getPgName());
        map.put("pgGender", petGroomer.getPgGender());
        map.put("pgPic", petGroomer.getPgPic());
        map.put("pgEmail", petGroomer.getPgEmail());
        map.put("pgPh", petGroomer.getPgPh());
        map.put("pgAddress", petGroomer.getPgAddress());
        map.put("pgBirthday", petGroomer.getPgBirthday());

        namedParameterJdbcTemplate.update(sql, map);
    }



    @Override
    public List<PetGroomer> getGroomerByPgName(String petGroomerName) {
        String sql = "SELECT pet_groomer.PG_ID, pet_groomer.MAN_ID, pet_groomer.PG_NAME, pet_groomer.PG_GENDER, pet_groomer.PG_PIC, " +
                "pet_groomer.PG_EMAIL, pet_groomer.PG_PH, pet_groomer.PG_ADDRESS, pet_groomer.PG_BIRTHDAY " +
                "FROM pet_groomer " +
                "JOIN manager ON pet_groomer.MAN_ID = manager.manager_id " +
                "WHERE pet_groomer.PG_NAME LIKE :pgName AND manager.MANAGER_STATE = 1";
        Map<String, Object> map = new HashMap<>();
        map.put("pgName", "%" + petGroomerName + "%");
        List<PetGroomer> petGroomerList = namedParameterJdbcTemplate.query(sql, map, new RowMapper<PetGroomer>() {
            @Override
            public PetGroomer mapRow(ResultSet rs, int rowNum) throws SQLException {
                PetGroomer petGroomer = new PetGroomer();
                petGroomer.setPgId(rs.getInt("PG_ID"));
                petGroomer.setManId(rs.getInt("MAN_ID"));
                petGroomer.setPgName(rs.getString("PG_NAME"));
                petGroomer.setPgGender(rs.getInt("PG_GENDER"));
                petGroomer.setPgPic(rs.getBytes("PG_PIC"));
                petGroomer.setPgEmail(rs.getString("PG_EMAIL"));
                petGroomer.setPgPh(rs.getString("PG_PH"));
                petGroomer.setPgAddress(rs.getString("PG_ADDRESS"));
                petGroomer.setPgBirthday(rs.getDate("PG_BIRTHDAY"));
                return petGroomer;
            }
        });
        return petGroomerList;
    }
}
