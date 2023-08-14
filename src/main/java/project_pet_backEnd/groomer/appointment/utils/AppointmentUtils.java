package project_pet_backEnd.groomer.appointment.utils;

import java.sql.Date;
import java.time.*;
import java.util.TimeZone;

public class AppointmentUtils {

    private  AppointmentUtils(){}

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

    //時間範圍xx:00~xx:00字串轉換時間字串
    public static String convertTimeStringToHourSlotString(String timeString) {
        String[] parts = timeString.split("~");
        String startTime = parts[0].trim();
        String[] startTimeParts = startTime.split(":");
        int startHour = Integer.parseInt(startTimeParts[0]);

        StringBuilder hourSlotString = new StringBuilder("000000000000000000000000");
        hourSlotString.setCharAt(startHour, '1');

        return hourSlotString.toString();
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
    //將預約單上服務選項字串轉換回整數方法
    public static int convertServiceOptionToInt(String serviceOptionString) {
        int serviceOption = switch (serviceOptionString.trim()) {
            case "狗狗洗澡" -> 0;
            case "狗狗半手剪 (洗澡+剃毛)" -> 1;
            case "狗狗全手剪(洗澡+全身手剪造型)" -> 2;
            case "貓咪洗澡" -> 3;
            case "貓咪大美容" -> 4;
            default -> -1; // 或其他代表未知的數值
        };
        return serviceOption;
    }

    //已存在的預約單日期（參數）。已存在的預約單時間。
    //比對預約日期是否在伺服器時間當天之前（含當天），並且比對預約時間是否在當日伺服器時間的兩小時前。
    public static boolean validateAppointment(Date appointmentDate, String timeSlotStatus) {
        // 步驟 2：抓取伺服器日期時間（亞洲台北時區）
        LocalDateTime serverDateTime = LocalDateTime.now(ZoneId.of("Asia/Taipei"));

        // 步驟 3：比對預約日期是否在伺服器時間當天之前（含當天）
        LocalDate serverDate = serverDateTime.toLocalDate();

        // 將24個數字的字串轉換為 LocalTime
        int startHour = timeSlotStatus.indexOf('1'); // 找到第一個 '1' 的位置，表示起始小時
        LocalTime startTime = LocalTime.of(startHour, 0); // 假設每個時間槽長度為1小時

        if (appointmentDate.toLocalDate().isEqual(serverDate)) {
            // 如果預約日期是今天
            if (serverDate.atTime(startTime).isBefore(serverDateTime.plusHours(2))) {
                // 如果預約的時間在兩小時內，則不可修改
                return false;
            }
        } else if (appointmentDate.toLocalDate().isBefore(serverDate)) {
            // 如果預約日期已經過期，則不可修改
            return false;
        }

        return true;
    }
    //傳入一個時間區間的字串(x:xx ~ x:xx)。
    // 然後根據伺服器的亞洲台北時間來驗證傳入的時間是否在當日的兩小時前。如果是，則返回 true，否則返回 false
    public static boolean validateNewTimeSlot(Date newAppointmentDate, String timeSlot) {
        // 步驟 1：抓取伺服器時間（亞洲台北時區）
        LocalDateTime serverDateTime = LocalDateTime.now(ZoneId.of("Asia/Taipei"));

        // 步驟 2：取得傳進來的時間區間的開始和結束時間
        String[] parts = timeSlot.split("~");
        String startTime = parts[0].trim();
        String endTime = parts[1].trim();

        // 將傳進來的時間轉換為 LocalTime
        LocalTime startLocalTime = LocalTime.parse(startTime);

        // 步驟 3：計算兩小時前的時間
        LocalTime twoHoursAgo = serverDateTime.toLocalTime().minusHours(2);

        // 比較開始時間是否在當前伺服器時間的兩小時前，或者傳入的日期是未來的日期
        if (startLocalTime.isBefore(twoHoursAgo) || newAppointmentDate.toLocalDate().isAfter(serverDateTime.toLocalDate())) {
            return false;
        }

        return true;
    }

    //遍歷預約單時間字串，找到第一個 '1' 的位置，並將其視為起始小時
    public static int convertTimeStringToHourSlot(String timeSlot) {
        for (int i = 0; i < timeSlot.length(); i++) {
            if (timeSlot.charAt(i) == '1') {
                return i;
            }
        }
        return -1; // 若找不到 '1'，表示時間槽為無效的值
    }

}
