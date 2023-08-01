package project_pet_backEnd.groomer.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import project_pet_backEnd.groomer.dao.PetGroomerDao;
import project_pet_backEnd.groomer.dto.ManagerGetByFunctionIdRequest;
import project_pet_backEnd.groomer.vo.PetGroomer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetGroomerImp implements PetGroomerDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<ManagerGetByFunctionIdRequest> getManagerByFunctionId(Integer functionId) {
        String sql="SELECT m.MANAGER_ID,m.MANAGER_ACCOUNT FROM all_dog_cat.manager m" +
                "join permission on m.MANAGER_ID = permission.manager_id" +
                "join `function` on `function`.function_id = permission.function_id" +
                "where permission.FUNCTION_ID = :functionId";
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

        String sql = "select * from pet_groomer where MAN_ID = :manId";
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
        return null;
    }


    @Override
    public void updateByPgIdOrPgName(Integer petGroomerId, String petGroomerName) {

    }



    @Override
    public void getGroomerByPgIdOrPgName(Integer petGroomerId, String petGroomerName) {

    }
}
