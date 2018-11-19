package ru.otus.h9.jsonobjectwriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ru.otus.h9.jsonobjectwriter.Helper.*;

public class JsonObjectWriter {

    private static Logger log = LoggerFactory.getLogger(JsonObjectWriter.class);

    private static boolean prtNull = false;


    public static String writeObject(Object obj) throws Exception {
        if (null == obj) return null;

        Type type = obj.getClass();

        if (isString(type)) {
            return "\"" + obj.toString() + "\"";
        }

        if (isSimple(type)) {
            return obj.toString();
        }

        if (isArray(type)) {
            return "{" + prtArray(obj) + "}";
        }

        if (obj instanceof List) {
            return "{" + prtList((List)obj) + "}";
        }

        if (obj instanceof Set) {
            return "{" + prtSet((Set)obj) + "}";
        }

        if (obj instanceof Map) {
            return "{" + prtMap((Map)obj) + "}";
        }

        return "{" + prtObjFields(obj) + "}";
    }


    private static String prtObjFields(Object obj) throws Exception {

        if (null == obj) return null;

        StringBuilder sb = new StringBuilder();

        List<String> fields = prtNoStaticFieldsOfClass(obj.getClass(), obj);

        if (fields.isEmpty()) {
            log.info("Отсутствуют поля");
        } else {
            sb.append(String.join(",", fields));
        }
        return sb.toString();
    }

    private static List<String> prtNoStaticFieldsOfClass(Class clazz, Object obj) throws Exception {
        List<String> lstfield = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();

        if (null != fields && 0 < fields.length) {
            for (Field f : fields) {

                if (!Modifier.isStatic(f.getModifiers())) {
                    f.setAccessible(true);

                    String val = writeObject(f.get(obj));

                    if (null != val) {
                        lstfield.add("\"" + f.getName() + "\":" + val);
                    } else if (prtNull) {
                        lstfield.add("\"" + f.getName() + "\":" + "null");
                    }
                }
            }
        }
        if (!clazz.getName().contains("Object")) {
            prtNoStaticFieldsOfClass(clazz.getSuperclass(), obj);
        }
        return lstfield;
    }

    private static boolean isSimple(Type type) {
        if (null == type) return false;
        String name = type.getTypeName();
        return  name.equals("java.lang.Byte") ||
                name.equals("java.lang.Short") ||
                name.equals("java.lang.Integer") ||
                name.equals("java.lang.Long") ||
                name.equals("java.lang.Float") ||
                name.equals("java.lang.Double") ||
                name.equals("java.lang.Boolean");
    }

    private static boolean isString(Type type) {
        if (null == type) return false;
        String name = type.getTypeName();
        return name.equals("java.lang.String");
    }

    private static boolean isArray(Type type) {
        if (null ==  type) return false;
        return type.getTypeName().contains("[]");
    }

    private static String prtMap(Map<Object, Object> map) throws Exception {
        Set<Map.Entry<Object,Object>> setmap = map.entrySet();

        List<String> lines = new ArrayList<>();

        for (Map.Entry<Object, Object> entry : setmap) {
            String key;

            if (isSimple(entry.getKey().getClass())) {
                key = "\"" + writeObject(entry.getKey()) + "\"";
            } else {
                key = writeObject(entry.getKey());
            }
            String value = writeObject(entry.getValue());

            lines.add(String.format("%1$s:%2$s", key, value));
        }
        String delimiter = ",";
        String result = String.join(delimiter, lines);
        return result;
    }

    private static String prtList(List<Object> list) throws Exception {
        if (null == list) return null;
        return prtArray(list.toArray(new Object[]{}));
    }

    private static String prtSet(Set<Object> set) throws Exception {
        if (null == set) return null;
        return prtArray(set.toArray(new Object[]{}));
    }

    private static String prtArray(Object object) throws Exception {
        String typeName = object.getClass().getTypeName();

        StringBuilder sb = new StringBuilder();
        if (typeName.contains("boolean")) {

            boolean[] arrBool = (boolean[]) object;
            sb.append(prtArray(hlpArray(arrBool)));

        } else if (typeName.contains("byte")) {

            byte[] arrByte = (byte[]) object;
            sb.append(prtArray(hlpArray(arrByte)));

        } else if (typeName.contains("short")) {

            short[] arrShort = (short[]) object;
            sb.append(prtArray(hlpArray(arrShort)));

        } else if (typeName.contains("int")) {

            int[] arrInt = (int[]) object;
            sb.append(prtArray(hlpArray(arrInt)));

        } else if (typeName.contains("long")) {

            long[] arrLong = (long[]) object;
            sb.append(prtArray(hlpArray(arrLong)));

        } else if (typeName.contains("float")) {

            float[] arrFloat = (float[]) object;
            sb.append(prtArray(hlpArray(arrFloat)));

        } else if (typeName.contains("double")) {

            double[] arrDouble = (double[]) object;
            sb.append(prtArray(hlpArray(arrDouble)));

        } else if (typeName.contains("String")) {

            String[] arrString = (String[]) object;
            sb.append(prtArray(arrString));

        } else {

            Object[] arrObj = (Object[]) object;
            sb.append(prtArray(arrObj));
        }

        return sb.toString();
    }


    private static <T> String prtArray(T[] array) throws Exception {
        if (null == array) {
            return "";
        }
        StringBuilder sb = new StringBuilder();

        List<String> lns = new ArrayList<>();
        for (T anArr : array) {
            lns.add(writeObject(anArr));
        }
        if (!lns.isEmpty()) {
            sb.append(String.join(",", lns));
        }
        return sb.toString();
    }
}
