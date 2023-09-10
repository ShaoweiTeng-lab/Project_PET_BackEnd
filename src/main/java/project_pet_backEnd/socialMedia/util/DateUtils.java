package project_pet_backEnd.socialMedia.util;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtils {
    //格式：yyyy-MM-dd HH:mm:ss
    public static final String STANDARD_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    //格式：yyyy-MM-dd
    public static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd";


    private DateUtils() {
    }

    // int to date string
    public static String longToString(long date) {
        java.sql.Date sqlDate = new java.sql.Date(date);
        String dateSqlStr = dateSqlToStr(sqlDate);
        return dateSqlStr;
    }

    /*
     * 標準時間格式（yyyy-MM-dd HH:mm:ss）
     * */

    // datetime str -> datetime sql(timestamp)完整轉換
    public static java.sql.Timestamp dateTimeStrToSql(String dateTimeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATETIME_FORMAT);
        Date date = null;
        try {
            date = sdf.parse(dateTimeStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Timestamp formatDateTime = new Timestamp(date.getTime());
        return formatDateTime;
    }

    // datetime sql(timestamp) -> datetime str完整轉換
    public static String dateTimeSqlToStr(java.sql.Timestamp dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATETIME_FORMAT);
        String dateTimeStr = sdf.format(dateTime);
        return dateTimeStr;
    }

    // date str -> date sql(date)完整轉換
    public static java.sql.Date dateStrToSql(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_FORMAT);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date formatDate = new java.sql.Date(date.getTime());
        return formatDate;
    }

    // date sql(date) -> date str完整轉換
    public static String dateSqlToStr(java.sql.Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_FORMAT);
        String dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * 標準時間格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatStandardDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATETIME_FORMAT);
        return sdf.format(date);
    }

    public static Timestamp getCurrentFormatTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATETIME_FORMAT);
        Date date = null;
        try {
            date = sdf.parse(String.valueOf(timestamp));
        } catch (ParseException e) {
            return new Timestamp(System.currentTimeMillis());
        }
        Timestamp newTime = new Timestamp(date.getTime());
        return newTime;

    }

    /**
     * 日期時間字串->日期格式（yyyy-MM-dd HH:mm:ss）
     */
    public static Date parseStandardDateTime(String dateTimeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATETIME_FORMAT);
        return sdf.parse(dateTimeStr);
    }


    /**
     * 標準日期格式（yyyy-MM-dd）
     */
    public static String formatStandardDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_FORMAT);
        return sdf.format(date);
    }

    /**
     * 日期字串->日期格式（yyyy-MM-dd）
     */
    public static Date parseStandardDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_FORMAT);
        return sdf.parse(dateStr);
    }


}


