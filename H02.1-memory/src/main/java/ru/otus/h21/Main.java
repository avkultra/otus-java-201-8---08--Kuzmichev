package ru.otus.h21;

import  java.lang.String;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * VM options -Xmx512m -Xms512m
 * -XX:+UseCompressedOops //on
 * -XX:-UseCompressedOops //off
 * <p>
 * Runtime runtime = Runtime.getRuntime();
 * long mem = runtime.totalMemory() - runtime.freeMemory();
 * <p>
 * System.gc()
 * <p>
 * jconsole, connect to pid
 */

//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// Добавить
// VM options-javaagent:target/H02.2.jar
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

@SuppressWarnings({"RedundantStringConstructorCall", "InfiniteLoopStatement", "UnusedAssignment"})
public class Main {

    private static int SIZE = 30_000_000;

    private interface ObjectConstrictor {
        Object newConstructor();
    }
    public static void main(String... args) throws InterruptedException {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());

        basicTypes();

        System.out.println("Второй way :");

        long lSize = 0;
       //Размер объектов
       lSize = getSizeObject(new String(""));
       System.out.println("Размер String(empty): " + " size: " + lSize);

       lSize = getSizeObject(getObject());
       System.out.println("Размер Object: " + " size: " + lSize);

       Integer sampleInteger = 100;
       lSize = getSizeObject(sampleInteger);
       System.out.println("Integer: " + " size: " + lSize);

       Object[] array = new Object[SIZE];
       for (int i = 0; i < SIZE; i++) {
           array[i] = new byte[1];
       }

       lSize = getSizeObject(array);
       System.out.println("Object[]: " + " size: " + lSize);

       lSize = getSizeObject(new MyClass());
       System.out.println("MyClass: " + " size: " + lSize);
    }

    //Проблема данного метода что он измеряет только public поля. Чтобы сделать доступ к private,
    // то нужно получить по имени член класса и сделать privateField.setAccessible(true); таким образом подойдёт 1 способ измерения памяти
    public static long getSizeObject(Object obj)  throws InterruptedException{
        Field[] fields = obj.getClass().getFields();
        long lSize = Helper.instance().getInstrumentation().getObjectSize(obj);

        if(String.class == obj.getClass())
        {
            return lSize;
        }
        //Обойдём все поля
        for(Field field: fields)
        {
            try
            {
                Class objTmp = field.getType();
                if (objTmp.isPrimitive()) {
                    lSize =+ Helper.instance().getInstrumentation().getObjectSize(field);
                } else {
                    lSize += getSizeObject(field);
                }
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }
        return lSize;
    }

    private static Object getObject() {
        return new Object();
    }

    private static long getMem() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private static class MyClass {
        private boolean b = true;
        private boolean b2 = true;// +1
        private boolean b3 = true;
        private boolean b4 = true;// +1
        private boolean b5 = true;// +1
        //private byte b2 = 0;
        //private byte b3 = 0;     // +1
        //private byte b4 = 0;
        //private byte b5 = 0;
        private int i = 0;      // +4
        private long l = 1;     // +8
        private String s = ("");
    }

    private static void basicTypes() throws InterruptedException {
        System.gc();
        Thread.sleep(10);

        System.out.println("Object()");
        ClassInfo(Object::new);
        Thread.sleep(1000);
        System.gc();
        Thread.sleep(20);

        System.out.println("Integer = 1");
        ClassInfo(()->(1));
        Thread.sleep(1000);
        System.gc();
        Thread.sleep(20);


        System.out.println("Double = 1");
        ClassInfo(()->(1.0d));
        Thread.sleep(1000);
        System.gc();
        Thread.sleep(20);

        System.out.println("MyClass()");
        ClassInfo(()->(new MyClass() ));
        Thread.sleep(1000);
        System.gc();
        Thread.sleep(20);

        System.out.println("String()");
        ClassInfo(()->(new String() ));
        Thread.sleep(1000);
        System.gc();
        Thread.sleep(20);

        System.out.println("String( new char[0])");
        ClassInfo(()->(new String(new char[0]) ));
        Thread.sleep(1000);
        System.gc();
        Thread.sleep(20);

        Object[] array = new Object[SIZE];
        for (int i = 0; i < SIZE; i++) {
            array[i] = new byte[1];
        }
    }

    private static void ClassInfo(ObjectConstrictor obConst ) throws InterruptedException {

        Object[] array = new Object[SIZE];

        long mem1 = getMem();

        for (int i = 0; i < array.length; i++) {
            array[i] = obConst.newConstructor();
        }

        long mem2 = getMem();

        System.out.println("Class: " + array[0].getClass().getName() + " размер: " + (mem2 - mem1) / array.length);

        printFields(array[0]);
    }

    private static void printFields(Object object) {
        if (null == object) return;
        List<String> Namefields = new ArrayList<>();

        lstNonStaticFields(object.getClass(), Namefields);

        if (Namefields.isEmpty()) {
            System.out.println("У элемента нет полей");
        } else {
            System.out.println("У поля элемента");
            for (String s : Namefields) {
                System.out.println(s);
            }
        }
    }

    private static void lstNonStaticFields(Class clazz, List<String> listOfNames) {
        Field[] fields = clazz.getDeclaredFields();

        if (null != fields &&  0 < fields.length) {
            for (Field f : fields) {
                if (!Modifier.isStatic(f.getModifiers())) {
                    listOfNames.add(f.getType().getTypeName());
                }
            }
        }
        if (!clazz.getName().contains("Object")) {
            lstNonStaticFields(clazz.getSuperclass(), listOfNames);
        }
    }

}