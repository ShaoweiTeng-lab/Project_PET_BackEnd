package project_pet_backEnd.groomer.appointment.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import project_pet_backEnd.groomer.petgroomerschedule.dao.PetGroomerScheduleDao;
import project_pet_backEnd.groomer.petgroomerschedule.vo.PetGroomerSchedule;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@Service
public class AppointmentUtils {

    @Autowired
    PetGroomerScheduleDao petGroomerScheduleDao;

    @Autowired
    private RedisTemplate redisTemplate;

    private  AppointmentUtils(){}
    public List<PetGroomerSchedule> fetchFromDatabaseAndCache(Integer pgId) {
        // 生成亞洲/台北時區的當前伺服器時間
        Date currentServerDate = generateCurrentServerTime();

        // 從DAO中取得班表資料
        List<PetGroomerSchedule> pgScheduleByPgIdList = petGroomerScheduleDao.getAllPgScheduleRecentMonth(pgId, currentServerDate);

        // 將結果快取到Redis中，設定60分鐘的過期時間
        redisTemplate.opsForList().leftPushAll("pgschedules_" + pgId, pgScheduleByPgIdList);
        redisTemplate.expire("pgschedules_" + pgId, 60, TimeUnit.MINUTES);

        return pgScheduleByPgIdList;
    }

    // 方法用於生成亞洲/台北時區的當前伺服器時間
    public Date generateCurrentServerTime() {
        // 設定時區為亞洲/台北
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Taipei");

        // 取得當前時間的毫秒數
        long currentTimestampInMillis = System.currentTimeMillis();

        // 將當前時間轉換為Instant物件
        Instant instant = Instant.ofEpochMilli(currentTimestampInMillis);

        // 建立表示亞洲/台北時區的ZoneId
        ZoneId zoneId = ZoneId.of(timeZone.getID());

        // 將Instant物件轉換為ZonedDateTime物件，並指定時區
        ZonedDateTime zonedDateTime = instant.atZone(zoneId);

        // 從ZonedDateTime物件中提取日期部分
        LocalDate localDate = zonedDateTime.toLocalDate();

        // 將LocalDate物件轉換為java.sql.Date物件
        return java.sql.Date.valueOf(localDate);
    }

}
