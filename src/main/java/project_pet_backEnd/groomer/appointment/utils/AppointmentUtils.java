package project_pet_backEnd.groomer.appointment.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import project_pet_backEnd.groomer.petgroomerschedule.dao.PetGroomerScheduleDao;
import project_pet_backEnd.groomer.petgroomerschedule.dto.PetGroomerScheduleForAppointment;
import project_pet_backEnd.groomer.petgroomerschedule.vo.PetGroomerSchedule;
import project_pet_backEnd.utils.AllDogCatUtils;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


public class AppointmentUtils {

    private  AppointmentUtils(){}

    /*
     * GroomerAppointmentServiceImp.getGroomerScheduleByPgId 使用 包裝成DTO
     */
    public static List<PetGroomerScheduleForAppointment> convertToAppointmentScheduleList(List<PetGroomerSchedule> scheduleList) {
        List<PetGroomerScheduleForAppointment> appointmentScheduleList = new ArrayList<>();
        for (PetGroomerSchedule schedule : scheduleList) {
            PetGroomerScheduleForAppointment appointmentSchedule = new PetGroomerScheduleForAppointment();
            appointmentSchedule.setPgsId(schedule.getPgsId());
            appointmentSchedule.setPgId(schedule.getPgId());
            appointmentSchedule.setPgsDate(AllDogCatUtils.timestampToSqlDateFormat(schedule.getPgsDate()));
            appointmentSchedule.setPgsState(schedule.getPgsState());
            appointmentScheduleList.add(appointmentSchedule);
        }
        return appointmentScheduleList;
    }

    /*
     * GroomerAppointmentServiceImp.getGroomerScheduleByPgId 方法用於生成亞洲/台北時區的當前伺服器時間
     */
    //
    public static Date generateCurrentServerTime() {
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


    //將預約單上的時間轉為xx:00~xx:00
    public static String convertTimeFrompgaTimeString(String daoTime) {
        int hourSlot = -1;

        for (int i = 0; i < daoTime.length(); i++) {
            if (daoTime.charAt(i) == '1') {
                hourSlot = i;
                break;
            }
        }

        String result = switch (hourSlot) {
            case 0 -> "0:00 ~ 1:00";
            case 1 -> "1:00 ~ 2:00";
            case 2 -> "2:00 ~ 3:00";
            case 3 -> "3:00 ~ 4:00";
            case 4 -> "4:00 ~ 5:00";
            case 5 -> "5:00 ~ 6:00";
            case 6 -> "6:00 ~ 7:00";
            case 7 -> "7:00 ~ 8:00";
            case 8 -> "8:00 ~ 9:00";
            case 9 -> "9:00 ~ 10:00";
            case 10 -> "10:00 ~ 11:00";
            case 11 -> "11:00 ~ 12:00";
            case 12 -> "12:00 ~ 13:00";
            case 13 -> "13:00 ~ 14:00";
            case 14 -> "14:00 ~ 15:00";
            case 15 -> "15:00 ~ 16:00";
            case 16 -> "16:00 ~ 17:00";
            case 17 -> "17:00 ~ 18:00";
            case 18 -> "18:00 ~ 19:00";
            case 19 -> "19:00 ~ 20:00";
            case 20 -> "20:00 ~ 21:00";
            case 21 -> "21:00 ~ 22:00";
            case 22 -> "22:00 ~ 23:00";
            case 23 -> "23:00 ~ 0:00";
            default -> "Unknown Time Slot";
        };

        return result;
    }
    //將預約單上的選項轉為字串項目回傳
    public static String convertServiceOption(int serviceOption) {
        String result = switch (serviceOption) {
            case 0 -> "狗狗洗澡";
            case 1 -> "狗狗半手剪 (洗澡+剃毛)";
            case 2 -> "狗狗全手剪(洗澡+全身手剪造型)";
            case 3 -> "貓咪洗澡";
            case 4 -> "貓咪大美容";
            default -> "未知服務選項";
        };

        return result;
    }

}
