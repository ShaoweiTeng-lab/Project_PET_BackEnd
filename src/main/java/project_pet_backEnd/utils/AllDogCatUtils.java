package project_pet_backEnd.utils;

public class AllDogCatUtils {
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
}
