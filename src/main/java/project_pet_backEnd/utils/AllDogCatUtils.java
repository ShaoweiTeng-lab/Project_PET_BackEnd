package project_pet_backEnd.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class AllDogCatUtils {
    private  AllDogCatUtils(){}
    /**
     * 生成驗證碼
     */
    public static String returnAuthCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 8; i++) {
            int condition = (int) (Math.random() * 3) + 1;
            switch (condition) {
                case 1:
                    char c1 = (char)((int)(Math.random() * 26) + 65);
                    sb.append(c1);
                    break;
                case 2:
                    char c2 = (char)((int)(Math.random() * 26) + 97);
                    sb.append(c2);
                    break;
                case 3:
                    sb.append((int)(Math.random() * 10));
            }
        }
        return sb.toString();
    }

    /**
     *  byte[]轉base 64
     */

    public static  String base64Encode(byte[] byteArray){
        if(byteArray==null)
            return  null;
        return Base64.getEncoder().encodeToString(byteArray);
    }
    /**
     *  base 64轉byte[]
     */
    public static  byte[] base64Decode(String  base64String ){
        if(base64String==null)
            return  null;
        return Base64.getDecoder().decode(base64String);
    }
    /**
     *  下載圖片
     */

    public  static  byte[] downloadImageAsByteArray(String imageUrl)  {
        URL url = null;
        try {
            url = new URL(imageUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        try (InputStream inputStream = url.openStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 日期轉換格式 java.util.date-> yyyy-MM-dd
     * */
    public  static String timestampToDateFormat(Date date){
        if(date == null){
            date = new Date();
        }
//        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
////        String formattedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
////        return formattedDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    /**
     * 日期轉換格式 java.sql.Date-> java.lang.String yyyy-MM-dd
     * */
    public static String timestampToSqlDateFormat(java.sql.Date date) {
        if(date == null){
            date = (java.sql.Date) new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 日期轉換格式 yyyy-MM-dd-> java.sql.date
     * */
    public static java.sql.Date dateFormatToSqlDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateString);
            return toSqlDate(date);
    }

    /**
     * 日期轉換格式 java.util.date> java.sql.date
     * */
    public static java.sql.Date toSqlDate(Date date) {
        if (date != null) {
            return new java.sql.Date(date.getTime());
        } else {
            return null;
        }
    }
    /**
     * 將上傳的檔案轉成 byte[]
     * */
    public static byte[] convertMultipartFileToByteArray(MultipartFile file) {
        if(file==null)
            return  null;
        try {
            return file.getBytes();
        } catch (IOException e) {
            // 轉換失敗時的錯誤處理
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 生成UUID
     * */
    public static  String generateUUID(){
        UUID uuid = UUID.randomUUID();
        return  uuid.toString();
    }

    /**
     * 透過value找回key
     * */
    public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     *Timestamp轉成String
     *
     * */

    public static String timestampToString(long timestamp ) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateFormat.format(new Date(timestamp));
        return  dateString;
    }
    /**
     *String轉成Timestamp
     *
     * */
    public  static  long  parseStringToTimeStamp(String dateString){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        long parsedTimestamp = parsedDate.getTime();
        return parsedTimestamp;
    }

}
