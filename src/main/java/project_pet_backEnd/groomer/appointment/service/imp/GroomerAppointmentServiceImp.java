package project_pet_backEnd.groomer.appointment.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import project_pet_backEnd.groomer.appointment.dto.response.GetAllGroomersForAppointmentRes;
import project_pet_backEnd.groomer.appointment.service.GroomerAppointmentService;
import project_pet_backEnd.groomer.petgroomer.dao.PetGroomerDao;
import project_pet_backEnd.groomer.petgroomer.vo.PetGroomer;
import project_pet_backEnd.groomer.petgroomerschedule.dao.PetGroomerScheduleDao;
import project_pet_backEnd.groomer.petgroomerschedule.utils.AppointmentUtils;
import project_pet_backEnd.groomer.petgroomerschedule.vo.PetGroomerSchedule;
import project_pet_backEnd.utils.AllDogCatUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class GroomerAppointmentServiceImp implements GroomerAppointmentService {
    @Autowired
    PetGroomerDao petGroomerDao;
    @Autowired
    PetGroomerScheduleDao petGroomerScheduleDao;

    @Autowired
    AppointmentUtils appointmentUtils;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<GetAllGroomersForAppointmentRes> getAllGroomersForAppointment() {
        String redisKey = "getAllGroomersForAppointmentRes";

        List<Object> getAllGroomersForAppointmentRes = redisTemplate.opsForList().range(redisKey, 0, -1);

        if (getAllGroomersForAppointmentRes == null || getAllGroomersForAppointmentRes.isEmpty()) {
            // 查詢數據庫
            List<PetGroomer> allGroomer = petGroomerDao.getAllGroomer();
            // 將結果緩存到 Redis
            redisTemplate.opsForList().leftPushAll(redisKey, allGroomer);
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

            return getAllGroomersForAppointmentResList;
        } else {
            // 轉換緩存的結果為 List<PetGroomer>
            List<PetGroomer> groomers = new ArrayList<>();
            for (Object obj : getAllGroomersForAppointmentRes) {
                if (obj instanceof PetGroomer) {
                    groomers.add((PetGroomer) obj);
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
            return getAllGroomersForAppointmentResList;
        }
    }

    /*
     *配合getAllGroomersForAppointment 需在前端自帶隱含input 有pgId來使用
     * 取得當前伺服器(含當天)一個月內的該pg班表
     */
    @Override
    public List<PetGroomerSchedule> getGroomerScheduleByPgId(Integer pgId) {
        String redisKey = "pgschedules_" + pgId;

        List<Object> cachedSchedule = redisTemplate.opsForList().range(redisKey, 0, -1);
        List<PetGroomerSchedule> pgScheduleByPgIdList;

        if (cachedSchedule != null && !cachedSchedule.isEmpty()) {
            // 如果 Redis 中有緩存，直接從緩存中取出結果
            pgScheduleByPgIdList = convertToPetGroomerScheduleList(cachedSchedule);
        } else {
            // 如果 Redis 中沒有緩存，從數據庫中查詢 Schedule，並過濾出當前伺服器時間(含當日凌晨開始)至未來一個月內的 Schedule
            pgScheduleByPgIdList = filterFutureSchedules(petGroomerScheduleDao.getPgScheduleByPgId(pgId));

            // 將結果緩存到 Redis，設定過期時間為 1 小時
            redisTemplate.opsForList().leftPushAll(redisKey, pgScheduleByPgIdList);
            redisTemplate.expire(redisKey, 60, TimeUnit.MINUTES);
        }
        return pgScheduleByPgIdList;
    }
}
