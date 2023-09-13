package project_pet_backEnd.productMall.lineNotify.Util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ApiUtil {
    public static <T> Map<String, Object> getRequestUriVariables(T source) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map<String, Object> returnMap = new HashMap<>();
        Field[] fields = source.getClass().getDeclaredFields();
        for(int i=0;i<fields.length;i++) {
            String fieldName = fields[i].getName();
            String firstLetter = fieldName.substring(0,1).toUpperCase();
            String getter = "get"+firstLetter+fieldName.substring(1);
            Method method = source.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(source, new Object[] {});
            returnMap.put(fieldName, value);
        }
        return returnMap;
    }
}
