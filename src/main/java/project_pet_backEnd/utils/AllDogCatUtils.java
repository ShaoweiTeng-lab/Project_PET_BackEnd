package project_pet_backEnd.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;

public class AllDogCatUtils {
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
        InputStream inputStream=null;
        ByteArrayOutputStream outputStream=null;
        try {
            url = new URL(imageUrl);
            inputStream = url.openStream();
            outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if(inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return outputStream.toByteArray();
    }

    /**
     * 日期轉換格式 date-> yyyy-MM-dd
     * */
    public  static String timestampToDateFormat(Date date){
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String formattedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return formattedDate;
    }
}