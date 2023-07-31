package project_pet_backEnd.groomer.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import project_pet_backEnd.groomer.dao.PetGroomerDao;
import project_pet_backEnd.groomer.dto.ManagerGetByFunctionIdRequest;
import project_pet_backEnd.groomer.dto.PetGroomerInsertRequest;
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
    public void insert(PetGroomerInsertRequest petGroomerInsertRequest) {

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
        map.put("manID",petGroomerInsertRequest.getManId());
        map.put("pgName",petGroomerInsertRequest.getPgName());
        map.put("pgGender",petGroomerInsertRequest.getPgGender());
        map.put("pgPic",petGroomerInsertRequest.getPgPic());
        map.put("pgEmail",petGroomerInsertRequest.getPgEmail());
        map.put("pgPh",petGroomerInsertRequest.getPgPh());
        map.put("pgAddress",petGroomerInsertRequest.getPgAddress());
        map.put("pgBirthday",petGroomerInsertRequest.getPgBirthday());

    }



    @Override
    public void updateByPgIdOrPgName(Integer petGroomerId, String petGroomerName) {

    }

    @Override
    public List<PetGroomer> getAll() {
        return null;
    }

    @Override
    public void getGroomerByPgIdOrPgName(Integer petGroomerId, String petGroomerName) {

    }
}
