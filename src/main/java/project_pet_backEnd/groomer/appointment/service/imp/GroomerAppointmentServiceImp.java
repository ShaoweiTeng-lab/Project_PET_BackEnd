package project_pet_backEnd.groomer.appointment.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_pet_backEnd.groomer.appointment.dto.response.GetAllGroomersForAppointmentRes;
import project_pet_backEnd.groomer.appointment.service.GroomerAppointmentService;
import project_pet_backEnd.groomer.petgroomer.dao.PetGroomerDao;
import project_pet_backEnd.groomer.petgroomer.vo.PetGroomer;
import project_pet_backEnd.groomer.petgroomerschedule.dao.PetGroomerScheduleDao;
import project_pet_backEnd.groomer.petgroomerschedule.utils.AppointmentUtils;
import project_pet_backEnd.groomer.petgroomerschedule.vo.PetGroomerSchedule;
import project_pet_backEnd.groomer.redis.RedisService;
import project_pet_backEnd.utils.AllDogCatUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroomerAppointmentServiceImp implements GroomerAppointmentService {
   @Autowired
   PetGroomerDao petGroomerDao;
   @Autowired
   PetGroomerScheduleDao petGroomerScheduleDao;

   @Autowired
   AppointmentUtils appointmentUtils;

   @Autowired
   RedisService redisService;

    @Override
    public List<GetAllGroomersForAppointmentRes> getAllGroomersForAppointment() {

        List<Object> getAllGroomersForAppointmentRes = redisService.getCachedQueryResults("getAllGroomersForAppointmentRes");

        if (getAllGroomersForAppointmentRes == null || getAllGroomersForAppointmentRes.isEmpty()) {
            // 查詢數據庫
            List<PetGroomer> allGroomer = petGroomerDao.getAllGroomer();
            // 將結果緩存到 Redis
            redisService.cacheQueryResults(allGroomer, "getAllGroomersForAppointmentRes", 60);
            List<GetAllGroomersForAppointmentRes> getAllGroomersForAppointmentResList= new ArrayList<>();
            for(PetGroomer petGroomer:allGroomer){
                GetAllGroomersForAppointmentRes res = new GetAllGroomersForAppointmentRes();
                res.setPgId(petGroomer.getPgId());
                res.setPgName(petGroomer.getPgName());
                int gender=petGroomer.getPgGender();
                switch (gender){
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
            List<GetAllGroomersForAppointmentRes> getAllGroomersForAppointmentResList= new ArrayList<>();
            for(PetGroomer petGroomer : groomers){
                GetAllGroomersForAppointmentRes res = new GetAllGroomersForAppointmentRes();
                res.setPgId(petGroomer.getPgId());
                res.setPgName(petGroomer.getPgName());
                int gender=petGroomer.getPgGender();
                switch (gender){
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

    //

    /*
     *配合getAllGroomersForAppointment 需在前端自帶隱含input 有pgId來使用
     * 取得當前伺服器(含當天)一個月內的該pg班表
     */
    @Override
    public List<PetGroomerSchedule> getGroomerScheduleByPgId(Integer pgId) {
        String redisKey = "pgschedules_" + pgId;

        List<Object> cachedSchedule = redisService.getCachedQueryResults(redisKey);

        List<PetGroomerSchedule> pgScheduleByPgIdList;
        // 檢查 Redis 中是否已經緩存了相應的 Schedule
        if (cachedSchedule != null && !cachedSchedule.isEmpty()) {
            // 如果 Redis 中有緩存，直接從緩存中取出結果
            pgScheduleByPgIdList = appointmentUtils.convertToPetGroomerScheduleList(cachedSchedule);
        } else {
            // 如果 Redis 中沒有緩存，從數據庫中查詢 Schedule，並過濾出當前伺服器時間(含當日凌晨開始)至未來一個月內的 Schedule
            pgScheduleByPgIdList = appointmentUtils.filterFutureSchedules(petGroomerScheduleDao.getPgScheduleByPgId(pgId));

            // 將結果緩存到 Redis，設定過期時間為 1 小時
            redisService.cacheQueryResults(pgScheduleByPgIdList, redisKey, 60);
        }
        return pgScheduleByPgIdList;
    }
}
