package project_pet_backEnd.groomer.appointment.service.imp;

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
import project_pet_backEnd.groomer.petgroomerschedule.vo.PetGroomerSchedule;
import project_pet_backEnd.user.dto.ResultResponse;
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
    GroomerAppointmentDao groomerAppointmentDao;
    @Autowired
    AppointmentUtils appointmentUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageForAppointment<List<GetAllGroomersForAppointmentRes>> getAllGroomersForAppointment(Integer userId) {
        String redisKey = "getAllGroomersForAppointmentRes";

        List<PetGroomer> getAllGroomersForAppointmentRes = redisTemplate.opsForList().range(redisKey, 0, -1);

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

            UserPhAndNameRes userPhAndNameForAppointment = groomerAppointmentDao.getUserPhAndNameForAppointment(userId);
            PageForAppointment page = new PageForAppointment<>();
            page.setRs(getAllGroomersForAppointmentResList);
            page.setUserName(userPhAndNameForAppointment.getUserName());
            page.setUserPh(userPhAndNameForAppointment.getUserPh());
            return page;
        } else {
            // Redis的結果為 List<PetGroomer>
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

            UserPhAndNameRes userPhAndNameForAppointment = groomerAppointmentDao.getUserPhAndNameForAppointment(userId);
            PageForAppointment page = new PageForAppointment();
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
        String redisKey = "pgschedules_" + pgId;

        List<Object> cachedSchedule = redisTemplate.opsForList().range(redisKey, 0, -1);
        List<PetGroomerSchedule> pgScheduleByPgIdList = new ArrayList<>();

        if (cachedSchedule != null && !cachedSchedule.isEmpty()) {
            // 檢查列表中的每個元素是否是PetGroomerSchedule對象
            boolean allElementsArePetGroomerSchedule = true;
            for (Object obj : cachedSchedule) {
                if (!(obj instanceof PetGroomerSchedule)) {
                    allElementsArePetGroomerSchedule = false;
                    break;
                }
            }

            if (allElementsArePetGroomerSchedule) {
                // 過濾出PetGroomerSchedule對象，並轉換為List<PetGroomerSchedule>
                for (Object obj : cachedSchedule) {
                    if (obj instanceof PetGroomerSchedule) {
                        pgScheduleByPgIdList.add((PetGroomerSchedule) obj);
                    }
                }
            } else {
                // 處理當Redis中的資料不全是PetGroomerSchedule的情況，從資料庫中取得資料
                pgScheduleByPgIdList = appointmentUtils.fetchFromDatabaseAndCache(pgId);
            }
        } else {
            // 如果Redis中沒有快取資料，從資料庫中取得資料
            pgScheduleByPgIdList = appointmentUtils.fetchFromDatabaseAndCache(pgId);
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
