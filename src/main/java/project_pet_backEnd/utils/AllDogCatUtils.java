package project_pet_backEnd.utils;

import java.util.Base64;

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
}
