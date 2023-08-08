package project_pet_backEnd.groomer.appointment.service.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import project_pet_backEnd.groomer.appointment.dao.GroomerAppointmentDao;
import project_pet_backEnd.groomer.appointment.dto.PageForAppointment;
import project_pet_backEnd.groomer.appointment.dto.response.GetAllGroomersForAppointmentRes;
import project_pet_backEnd.groomer.appointment.dto.response.UserPhAndNameRes;
import project_pet_backEnd.groomer.appointment.service.GroomerAppointmentService;
import project_pet_backEnd.groomer.petgroomer.dao.PetGroomerDao;
import project_pet_backEnd.groomer.petgroomer.vo.PetGroomer;
import project_pet_backEnd.groomer.petgroomerschedule.dao.PetGroomerScheduleDao;
import project_pet_backEnd.groomer.appointment.utils.AppointmentUtils;
import project_pet_backEnd.groomer.petgroomerschedule.dto.PetGroomerScheduleForAppointment;
import project_pet_backEnd.groomer.petgroomerschedule.vo.PetGroomerSchedule;
import project_pet_backEnd.user.dto.ResultResponse;
import project_pet_backEnd.utils.AllDogCatUtils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static project_pet_backEnd.groomer.appointment.utils.AppointmentUtils.convertToAppointmentScheduleList;

@Service
public class GroomerAppointmentServiceImp implements GroomerAppointmentService {
    @Autowired
    PetGroomerDao petGroomerDao;
    @Autowired
    PetGroomerScheduleDao petGroomerScheduleDao;

    @Autowired
    GroomerAppointmentDao groomerAppointmentDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;
    /*
     * 進入頁面後。取得全美容師給使用者選擇。進入預約頁面的使用者預先填寫電話。
     * 姓名單純顯示。不可修改。
     */
    @Override
    public PageForAppointment<List<GetAllGroomersForAppointmentRes>> getAllGroomersForAppointment(Integer userId) {
        String redisKey = "getAllGroomersForAppointmentRes";

        List<String> getAllGroomersForAppointmentRes = redisTemplate.opsForList().range(redisKey, 0, -1);

        if (getAllGroomersForAppointmentRes == null || getAllGroomersForAppointmentRes.isEmpty()) {
            // 查詢數據庫
            List<PetGroomer> allGroomer = petGroomerDao.getAllGroomer();
            // 將結果緩存到 Redis
            List<String> jsonStrings = new ArrayList<>();
            for (PetGroomer petGroomer : allGroomer) {
                try {
                    String json = objectMapper.writeValueAsString(petGroomer);
                    jsonStrings.add(json);
                } catch (JsonProcessingException e) {
                    // 如果需要，處理例外情況
                }
            }
            redisTemplate.opsForList().leftPushAll(redisKey, jsonStrings);
            redisTemplate.expire(redisKey, 60, TimeUnit.MINUTES);

            List<GetAllGroomersForAppointmentRes> getAllGroomersForAppointmentResList = new ArrayList<>();
            for (PetGroomer petGroomer : allGroomer) {
                GetAllGroomersForAppointmentRes res = new GetAllGroomersForAppointmentRes();
                res.setPgId(petGroomer.getPgId());
                res.setPgName(petGroomer.getPgName());
                int gender = petGroomer.getPgGender();
                switch (gender) {
                    case 0:
                        res.setPgGender("女性");
                        break;
                    case 1:
                        res.setPgGender("男性");
                        break;
                }
                res.setPgPic(AllDogCatUtils.base64Encode(petGroomer.getPgPic()));
                getAllGroomersForAppointmentResList.add(res);
            }

            UserPhAndNameRes userPhAndNameForAppointment = groomerAppointmentDao.getUserPhAndNameForAppointment(userId);
            PageForAppointment<List<GetAllGroomersForAppointmentRes>> page = new PageForAppointment<>();
            page.setRs(getAllGroomersForAppointmentResList);
            page.setUserName(userPhAndNameForAppointment.getUserName());
            page.setUserPh(userPhAndNameForAppointment.getUserPh());
            return page;
        } else {
            // Redis的結果為 JSON 字串
            List<PetGroomer> groomers = new ArrayList<>();
            for (String jsonString : getAllGroomersForAppointmentRes) {
                try {
                    PetGroomer petGroomer = objectMapper.readValue(jsonString, PetGroomer.class);
                    groomers.add(petGroomer);
                } catch (JsonProcessingException e) {
                    // 處理例外情況
                }
            }

            List<GetAllGroomersForAppointmentRes> getAllGroomersForAppointmentResList = new ArrayList<>();
            for (PetGroomer petGroomer : groomers) {
                GetAllGroomersForAppointmentRes res = new GetAllGroomersForAppointmentRes();
                res.setPgId(petGroomer.getPgId());
                res.setPgName(petGroomer.getPgName());
                int gender = petGroomer.getPgGender();
                switch (gender) {
                    case 0:
                        res.setPgGender("女性");
                        break;
                    case 1:
                        res.setPgGender("男性");
                        break;
                }
                res.setPgPic(AllDogCatUtils.base64Encode(petGroomer.getPgPic()));
                getAllGroomersForAppointmentResList.add(res);
            }

            UserPhAndNameRes userPhAndNameForAppointment = groomerAppointmentDao.getUserPhAndNameForAppointment(userId);
            PageForAppointment<List<GetAllGroomersForAppointmentRes>> page = new PageForAppointment<>();
            page.setRs(getAllGroomersForAppointmentResList);
            page.setUserName(userPhAndNameForAppointment.getUserName());
            page.setUserPh(userPhAndNameForAppointment.getUserPh());
            return page;
        }
    }

    /*
     *配合getAllGroomersForAppointment 需在前端自帶隱含input 有pgId來使用
     * 取得當前伺服器(含當天)一個月內的該pg班表
     */
    @Override
    public ResultResponse getGroomerScheduleByPgId(Integer pgId) {
        // generateCurrentServerTime方法獲取現在的伺服器日期
        Date currentServerDate = AppointmentUtils.generateCurrentServerTime();

        // 構建快取的 key 值
        String redisKey = "pgschedules_" + pgId + "_" + currentServerDate;

        List<PetGroomerScheduleForAppointment> pgScheduleByPgIdList = new ArrayList<>();

        // 先從 Redis 中取出快取資料
        List<String> cachedSchedule = redisTemplate.opsForList().range(redisKey, 0, -1);

        if (cachedSchedule != null && !cachedSchedule.isEmpty()) {
            // Redis中已有快取資料，將JSON String反序列化為PetGroomerScheduleForAppointment
            for (String jsonString : cachedSchedule) {
                try {
                    PetGroomerScheduleForAppointment appointmentSchedule = objectMapper.readValue(jsonString, PetGroomerScheduleForAppointment.class);
                    pgScheduleByPgIdList.add(appointmentSchedule);
                } catch (JsonProcessingException e) {
                    // 處理例外情況
                }
            }
        } else {
            // 如果Redis中沒有快取資料，從資料庫中取得資料
            List<PetGroomerSchedule> petGroomerSchedulesList = petGroomerScheduleDao.getAllPgScheduleRecentMonth(pgId, currentServerDate);

            // 將PetGroomerSchedule轉換為PetGroomerScheduleForAppointment
            pgScheduleByPgIdList = convertToAppointmentScheduleList(petGroomerSchedulesList);

            // 將結果序列化為JSON字符串後，快取到Redis中，設定60分鐘的過期時間
            List<String> jsonStrings = new ArrayList<>();
            for (PetGroomerScheduleForAppointment schedule : pgScheduleByPgIdList) {
                try {
                    String json = objectMapper.writeValueAsString(schedule);
                    jsonStrings.add(json);
                } catch (JsonProcessingException e) {
                    // 處理例外情況
                }
            }
            redisTemplate.opsForList().leftPushAll(redisKey, jsonStrings);
            redisTemplate.expire(redisKey, 60, TimeUnit.MINUTES);
        }

        ResultResponse rs = new ResultResponse();
        rs.setMessage(pgScheduleByPgIdList);
        return rs;
    }

    @Override
    public ResultResponse insertNewAppointmentUpdateScheduleByPgId() {
        return null;
    }
}
