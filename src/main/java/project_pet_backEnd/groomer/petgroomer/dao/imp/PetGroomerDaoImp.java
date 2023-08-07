package project_pet_backEnd.groomer.petgroomer.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.groomer.petgroomer.dao.PetGroomerDao;
import project_pet_backEnd.groomer.petgroomer.dto.GetAllGroomers;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.response.GetAllGroomerListSortResForUser;
import project_pet_backEnd.groomer.petgroomer.dto.response.ManagerGetByFunctionIdRes;
import project_pet_backEnd.groomer.petgroomer.vo.PetGroomer;
import project_pet_backEnd.utils.AllDogCatUtils;

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
    public List<ManagerGetByFunctionIdRes> getManagerByFunctionId(Integer functionId) {
        String sql="SELECT m.MANAGER_ID,m.MANAGER_ACCOUNT FROM all_dog_cat.manager m\n" +
                "join permission on m.MANAGER_ID = permission.manager_id\n" +
                "join `function` on `function`.function_id = permission.function_id\n" +
                "where permission.FUNCTION_ID = :functionId and MANAGER_STATE = 1";
        Map<String , Object> map =new HashMap<>();
        map.put("functionId", functionId);
        List<ManagerGetByFunctionIdRes> rsList=namedParameterJdbcTemplate.query(sql, map, new RowMapper<ManagerGetByFunctionIdRes>() {
            @Override
            public ManagerGetByFunctionIdRes mapRow(ResultSet rs, int rowNum) throws SQLException {
                ManagerGetByFunctionIdRes managerGetByFunctionIdRes =new ManagerGetByFunctionIdRes(
                        rs.getInt("MANAGER_ID"),
                        rs.getString("MANAGER_ACCOUNT"));
                return managerGetByFunctionIdRes;
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
    public List<GetAllGroomers> getAllGroomersWithSearch(PGQueryParameter PGQueryParameter) {
        String sql = "SELECT pet_groomer.PG_ID, MAN_ID, PG_NAME, PG_GENDER, PG_PIC, PG_EMAIL, PG_PH, PG_ADDRESS, PG_BIRTHDAY, COUNT(pet_groomer_appointment.PGA_NO) AS NUM_APPOINTMENTS " +
                "FROM pet_groomer " +
                "JOIN manager ON pet_groomer.MAN_ID = manager.manager_id " +
                "LEFT JOIN pet_groomer_appointment ON pet_groomer.PG_ID = pet_groomer_appointment.PG_ID " +
                "WHERE MANAGER_STATE = 1 ";

        Map<String, Object> map = new HashMap<>();

        if (PGQueryParameter.getSearch() != null) {
            sql += "AND (PG_NAME LIKE :search OR MAN_ID LIKE :search OR pet_groomer.PG_ID LIKE :search) ";
            map.put("search", "%" + PGQueryParameter.getSearch() + "%");
        }

        // Group by
        sql += "GROUP BY pet_groomer.PG_ID, MAN_ID, PG_NAME, PG_GENDER, PG_PIC, PG_EMAIL, PG_PH, PG_ADDRESS, PG_BIRTHDAY ";

        // Sort
        if (PGQueryParameter.getOrder() != null) {
            String orderBy;
            switch (PGQueryParameter.getOrder()) {
                case NUM_APPOINTMENTS:
                    orderBy = "NUM_APPOINTMENTS";
                    break;
                case PG_NAME:
                    orderBy = "PG_NAME";
                    break;
                default:
                    orderBy = "PG_ID"; // Default sorting by PG_ID
            }
            sql += "ORDER BY " + orderBy + " ";
        }

        // Sort
        if (PGQueryParameter.getSort() != null) {
            sql += PGQueryParameter.getSort() + " ";
        }

        // Limit and Offset
        sql += "LIMIT :limit OFFSET :offset ";
        map.put("limit", PGQueryParameter.getLimit());
        map.put("offset", PGQueryParameter.getOffset());

        List<GetAllGroomers> petGroomersAllList = namedParameterJdbcTemplate.query(sql, map, new RowMapper<GetAllGroomers>() {
            @Override
            public GetAllGroomers mapRow(ResultSet rs, int rowNum) throws SQLException {
                GetAllGroomers getAllGroomers = new GetAllGroomers();
                getAllGroomers.setPgId(rs.getInt("PG_ID"));
                getAllGroomers.setManId(rs.getInt("MAN_ID"));
                getAllGroomers.setPgName(rs.getString("PG_NAME"));
                getAllGroomers.setPgGender(rs.getInt("PG_GENDER"));
                getAllGroomers.setPgPic(rs.getBytes("PG_PIC"));
                getAllGroomers.setPgEmail(rs.getString("PG_EMAIL"));
                getAllGroomers.setPgPh(rs.getString("PG_PH"));
                getAllGroomers.setPgAddress(rs.getString("PG_ADDRESS"));
                getAllGroomers.setPgBirthday(rs.getDate("PG_BIRTHDAY"));
                getAllGroomers.setNumAppointments(rs.getInt("NUM_APPOINTMENTS"));
                return getAllGroomers;
            }
        });
        return petGroomersAllList;
    }

    /**
     * 供給updateGroomerById使用。
     * @param manId
     * @return
     */
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
    /**
     * 給使用者點單一美容師時回傳資訊。同時查詢作品集。 for教登Service層實作
     * @param pgId
     * @return
     *    Integer pgId;
     *    String pgName;
     *    String pgGender;//String 男性 / 女性
     *    String pgPic;//Base64
     *    Integer NumAppointments;//預約數量
     */
    @Override
    public GetAllGroomerListSortResForUser getPetGroomerByPgId(Integer pgId) {

        String sql = "SELECT pet_groomer.PG_ID, MAN_ID, PG_NAME, PG_GENDER, PG_PIC, PG_EMAIL, PG_PH, PG_ADDRESS, PG_BIRTHDAY, COUNT(pet_groomer_appointment.PGA_NO) AS NUM_APPOINTMENTS\n" +
                "FROM PET_GROOMER\n" +
                "JOIN MANAGER ON pet_groomer.MAN_ID = manager.MANAGER_ID\n" +
                "LEFT JOIN PET_GROOMER_APPOINTMENT ON pet_groomer.PG_ID = pet_groomer_appointment.PG_ID\n" +
                "WHERE MANAGER_STATE = 1 AND pet_groomer.PG_ID = :pgId\n" +
                "GROUP BY pet_groomer.PG_ID, MAN_ID, PG_NAME, PG_GENDER, PG_PIC, PG_EMAIL, PG_PH, PG_ADDRESS, PG_BIRTHDAY";
        Map map = new HashMap<>();
        map.put("pgId",pgId);
        List<GetAllGroomerListSortResForUser> petGroomerList = namedParameterJdbcTemplate.query(sql, map, new RowMapper<GetAllGroomerListSortResForUser>() {
            @Override
            public GetAllGroomerListSortResForUser mapRow(ResultSet rs, int rowNum) throws SQLException {
                GetAllGroomerListSortResForUser petGroomer =  new GetAllGroomerListSortResForUser();
                petGroomer.setPgId(rs.getInt("PG_ID"));
                petGroomer.setPgName(rs.getString("PG_NAME"));

                if(rs.getInt("PG_GENDER")==0){
                    petGroomer.setPgGender("女性");
                }else {
                    petGroomer.setPgGender("男性");
                }
                petGroomer.setPgPic(AllDogCatUtils.base64Encode(rs.getBytes("PG_PIC")));
                petGroomer.setNumAppointments(rs.getInt("NUM_APPOINTMENTS"));
                return petGroomer;
            }
        });
        if(petGroomerList.size()>0){
            return petGroomerList.get(0);
        }
        return null;
    }

    @Override
    public void updateGroomerByPgId(PetGroomer petGroomer) {
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
    public Integer countPetGroomer(PGQueryParameter PGQueryParameter){
        String sql = "SELECT COUNT(*) AS total_count " +
                "FROM pet_groomer " +
                "JOIN manager ON pet_groomer.MAN_ID = manager.MANAGER_ID " +
                "WHERE MANAGER_STATE = :managerState";

        Map<String, Object> map = new HashMap<>();
        map.put("managerState", 1);
        if (PGQueryParameter.getSearch() != null) {
            sql += " AND PG_NAME LIKE :search";
            map.put("search", "%" + PGQueryParameter.getSearch() + "%");
        }
        Integer total= namedParameterJdbcTemplate.queryForObject(sql, map, new RowMapper<Integer>() {

            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("total_count");
            }
        });
        return  total;
    }

//    @Override
//    public List<PetGroomer> getGroomerByPgName(String petGroomerName) {
//        String sql = "SELECT pet_groomer.PG_ID, pet_groomer.MAN_ID, pet_groomer.PG_NAME, pet_groomer.PG_GENDER, pet_groomer.PG_PIC, " +
//                "pet_groomer.PG_EMAIL, pet_groomer.PG_PH, pet_groomer.PG_ADDRESS, pet_groomer.PG_BIRTHDAY " +
//                "FROM pet_groomer " +
//                "JOIN manager ON pet_groomer.MAN_ID = manager.manager_id " +
//                "WHERE pet_groomer.PG_NAME LIKE :pgName AND manager.MANAGER_STATE = 1";
//        Map<String, Object> map = new HashMap<>();
//        map.put("pgName", "%" + petGroomerName + "%");
//        List<PetGroomer> petGroomerList = namedParameterJdbcTemplate.query(sql, map, new RowMapper<PetGroomer>() {
//            @Override
//            public PetGroomer mapRow(ResultSet rs, int rowNum) throws SQLException {
//                PetGroomer petGroomer = new PetGroomer();
//                petGroomer.setPgId(rs.getInt("PG_ID"));
//                petGroomer.setManId(rs.getInt("MAN_ID"));
//                petGroomer.setPgName(rs.getString("PG_NAME"));
//                petGroomer.setPgGender(rs.getInt("PG_GENDER"));
//                petGroomer.setPgPic(rs.getBytes("PG_PIC"));
//                petGroomer.setPgEmail(rs.getString("PG_EMAIL"));
//                petGroomer.setPgPh(rs.getString("PG_PH"));
//                petGroomer.setPgAddress(rs.getString("PG_ADDRESS"));
//                petGroomer.setPgBirthday(rs.getDate("PG_BIRTHDAY"));
//                return petGroomer;
//            }
//        });
//        return petGroomerList;
}

