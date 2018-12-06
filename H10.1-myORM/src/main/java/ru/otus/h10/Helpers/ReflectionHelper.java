package ru.otus.h10.Helpers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ReflectionHelper {
    public static Field[] getFields(Class clazz){

        if (null == clazz)
        {
            return null;
        }

        Field fields[] = clazz.getDeclaredFields();

        if (null == fields) {
            return null;
        }

        List<Field> lstFields= new ArrayList<>();

        for (Field field : fields) {
            field.setAccessible(true);

            int modifiers = field.getModifiers();
            if (Modifier.isTransient(modifiers) || Modifier.isStatic(modifiers))
                continue;

            lstFields.add(field);
        }

        return lstFields.toArray(new Field[lstFields.size()]);
    }

    public static String getValue(Field field, Object object){
        try {

            String strRes = field.get(object).toString();
            return strRes;
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Method getSetter(Class clazz, String column, Method[] methods){
            try {
                for (Method method : methods){
                    String setterMethodName = "set" +
                            Character.toUpperCase(column.charAt(0)) +
                            column.substring(1);

                    if (method.getName().equals(setterMethodName)){
                        return method;
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
}
