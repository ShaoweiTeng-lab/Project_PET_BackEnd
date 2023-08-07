package project_pet_backEnd.groomer.petgroomerschedule.utils;

import project_pet_backEnd.groomer.petgroomerschedule.vo.PetGroomerSchedule;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class AppointmentUtils {
    // 過濾出當前伺服器時間至未來一個月內的 Schedule
    public List<PetGroomerSchedule> filterFutureSchedules(List<PetGroomerSchedule> schedules) {
        List<PetGroomerSchedule> futureSchedules = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Taipei"));
        LocalDateTime oneMonthLater = now.plusMonths(1);

        for (PetGroomerSchedule schedule : schedules) {
            Date pgsDate = schedule.getPgsDate();
            LocalDateTime scheduleDateTime = pgsDate.toInstant().atZone(ZoneId.of("Asia/Taipei")).toLocalDateTime();

            // 將時間設置為當日凌晨 00:00:00
            LocalDateTime startOfDay = scheduleDateTime.toLocalDate().atStartOfDay();

            if (startOfDay.isEqual(now) || (startOfDay.isAfter(now) && startOfDay.isBefore(oneMonthLater))) {
                futureSchedules.add(schedule);
            }
        }
        return futureSchedules;
    }

    // 將 List<Object> 轉換為 List<PetGroomerSchedule>
    public List<PetGroomerSchedule> convertToPetGroomerScheduleList(List<Object> objList) {
        List<PetGroomerSchedule> scheduleList = new ArrayList<>();
        for (Object obj : objList) {
            if (obj instanceof PetGroomerSchedule) {
                scheduleList.add((PetGroomerSchedule) obj);
            }
        }
        return scheduleList;
    }
}
