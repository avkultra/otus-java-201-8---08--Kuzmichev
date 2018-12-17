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

    private final static boolean REF_NULL = false;


    public static String writeObject(Object obj) throws Exception {
        if (null == obj) return null;

        Type type = obj.getClass();

        if (isString(type)) {
            return "\"" + obj.toString() + "\"";
        }

        if (isSimple(obj.getClass())) {
            return obj.toString();
        }

        if (isArray(type)) {
            return "[" + printLnArray(obj) + "]";
        }

        if (obj instanceof List) {
            return "{" + printLnList((List)obj) + "}";
        }

        if (obj instanceof Set) {
            return "{" + printLnSet((Set)obj) + "}";
        }

        if (obj instanceof Map) {
            return "{" + printLnMap((Map)obj) + "}";
        }

        return "{" + printLnObjFields(obj) + "}";
    }


    private static String printLnObjFields(Object obj) throws Exception {

        if (null == obj) return null;

        StringBuilder sb = new StringBuilder();

        List<String> fields = printLnNoStaticFieldsOfClass(obj.getClass(), obj);

        if (fields.isEmpty()) {
            log.info("Отсутствуют поля");
        } else {
            sb.append(String.join(",", fields));
        }
        return sb.toString();
    }

    private static List<String> printLnNoStaticFieldsOfClass(Class clazz, Object obj) throws Exception {
        List<String> lstField = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();

        if (null != fields && 0 < fields.length) {
            for (Field f : fields) {

                if (!Modifier.isStatic(f.getModifiers())) {
                    f.setAccessible(true);

                    String val = writeObject(f.get(obj));

                    if (null != val) {
                        lstField.add("\"" + f.getName() + "\":" + val);
                    } else if (REF_NULL) {
                        lstField.add("\"" + f.getName() + "\":" + "null");
                    }
                }
            }
        }
        if (!clazz.getName().contains("Object")) {
            printLnNoStaticFieldsOfClass(clazz.getSuperclass(), obj);
        }
        return lstField;
    }

    private static boolean isSimple(Class clazz) {
        if (null == clazz) return false;
        //String name = type.getTypeName();
        return  clazz.equals(Byte.class) ||
                clazz.equals(Short.class) ||
                clazz.equals(Integer.class) ||
                clazz.equals(Long.class) ||
                clazz.equals(Float.class) ||
                clazz.equals(Double.class) ||
                clazz.equals(Boolean.class);
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

    private static String printLnMap(Map<Object, Object> map) throws Exception {
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

    private static String printLnList(List<Object> list) throws Exception {
        if (null == list) return null;
        return printLnArray(list.toArray(new Object[]{}));
    }

    private static String printLnSet(Set<Object> set) throws Exception {
        if (null == set) return null;
        return printLnArray(set.toArray(new Object[]{}));
    }

    private static String printLnArray(Object object) throws Exception {
        String typeName = object.getClass().getTypeName();


        StringBuilder sb = new StringBuilder();
        if (typeName.contains("boolean") ) {


            boolean[] arrBool = (boolean[]) object;
            sb.append(printLnArray(hlpArray(arrBool)));

        } else if (typeName.contains("byte")) {
           byte[] arrByte = (byte[]) object;
            sb.append(printLnArray(hlpArray(arrByte)));

        } else if (typeName.contains("short")) {
            short[] arrShort = (short[]) object;
            sb.append(printLnArray(hlpArray(arrShort)));

        } else if (typeName.contains("int")) {
           int[] arrInt = (int[]) object;
            sb.append(printLnArray(hlpArray(arrInt)));

        } else if (typeName.contains("long")) {
          long[] arrLong = (long[]) object;
            sb.append(printLnArray(hlpArray(arrLong)));

        } else if (typeName.contains("float")) {
           float[] arrFloat = (float[]) object;
           sb.append(printLnArray(hlpArray(arrFloat)));

        } else if (typeName.contains("double")) {
            double[] arrDouble = (double[]) object;
            sb.append(printLnArray(hlpArray(arrDouble)));

       } else if (typeName.contains("String")) {
            String[] arrString = (String[]) object;
            sb.append(printLnArray(arrString));

        } else {

            Object[] arrObj = (Object[]) object;
            sb.append(printLnArray(arrObj));
        }

        return sb.toString();
    }


    private static <T> String printLnArray(T[] array) throws Exception {
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
