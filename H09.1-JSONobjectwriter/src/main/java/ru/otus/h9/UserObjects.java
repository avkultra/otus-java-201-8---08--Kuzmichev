package ru.otus.h9;

import java.util.*;

public class UserObjects {

    private String m_String;

    private byte m_Byte;
    private short m_Short;
    private int m_Int;
    private long m_Long;
    private float m_Float;
    private double m_Double;

    private boolean m_Boolean;

    private int[] m_ArrayOfint;
    private Integer[] m_ArrayOfInteger;
    private String[] m_ArrayOfString;

    private List<String> m_ListOfString;
    private Map<String, String> m_MapStrToStr;
    private Map<Integer, String> m_MapStrToInt;
    private Set<String> m_SetOfString;
    private Set<Integer> m_SetOfInteger;

    private List<Object> m_ObjectList;

    public UserObjects() {

        m_String = "Java";

        m_Byte = (byte)10;
        m_Short = 20;
        m_Int = 30;
        m_Long = 40;
        m_Float = 50f;
        m_Double = 60.0;

        m_Boolean = true;

        m_ArrayOfint = new int[] {10,20,30};
        m_ArrayOfInteger = new Integer[] {10,20,30};
        m_ArrayOfString = new String[] {"A", "Б", "В"};

        m_ListOfString = new ArrayList<>();
        m_ListOfString.add("str_1");
        m_ListOfString.add("str_2");
        m_ListOfString.add("str_3");

        m_SetOfString = new HashSet<>();
        m_SetOfString.add("set_1");
        m_SetOfString.add("set_2");
        m_SetOfString.add("set_3");

        m_MapStrToStr = new HashMap<>();
        m_MapStrToStr.put("key_1", "val_1");
        m_MapStrToStr.put("key_2", "val_2");
        m_MapStrToStr.put("key_3", "val_3");

        m_MapStrToInt = new HashMap<>();
        m_MapStrToInt.put(1, "int_1");
        m_MapStrToInt.put(2, "int_2");
        m_MapStrToInt.put(3, "int_3");

        m_SetOfInteger = new HashSet<>();
        m_SetOfInteger.add(1);
        m_SetOfInteger.add(2);
        m_SetOfInteger.add(3);
    }

    @Override
    public boolean equals(Object obj) {

        if (super.equals(obj)) return true;

        if (!(obj instanceof UserObjects)) return false;

        UserObjects uo = (UserObjects)obj;

        if (m_Boolean != uo.m_Boolean ||
                m_Byte != uo.m_Byte ||
                m_Short != uo.m_Short ||
                m_Int != uo.m_Int ||
                m_Long != uo.m_Long ||
                m_Float != uo.m_Float ||
                m_Double != uo.m_Double ||
                !m_String.equals(uo.m_String)) {
            return false;
        }

        if (!Arrays.equals(m_ArrayOfint, uo.m_ArrayOfint)) {
            return false;
        }
        if (!Arrays.equals(m_ArrayOfInteger, uo.m_ArrayOfInteger)) {
            return false;
        }
        if (!Arrays.equals(m_ArrayOfString, uo.m_ArrayOfString)) {
            return false;
        }

        if (!m_ListOfString.equals(uo.m_ListOfString)) {
            return false;
        }
        if (!m_MapStrToStr.equals(uo.m_MapStrToStr)) {
            return false;
        }
        if (!m_MapStrToInt.equals(uo.m_MapStrToInt)) {
            return false;
        }
        if (!m_SetOfString.equals(uo.m_SetOfString)) {
            return false;
        }
        if (!m_SetOfInteger.equals(uo.m_SetOfInteger)) {
            return false;
        }
        if (m_ObjectList != uo.m_ObjectList) {
            return false;
        }
        return true;
    }
}