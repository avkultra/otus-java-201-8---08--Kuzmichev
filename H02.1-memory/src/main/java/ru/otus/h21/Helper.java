package ru.otus.h21;


import java.lang.instrument.Instrumentation;


public class Helper {
    private static Helper hlp;
    private Instrumentation instrumentation;

    private Helper(){}

    static Helper instance(){
        if(hlp == null){
            hlp = new Helper();
        }
        return hlp;
    }

    public static void premain(String args, Instrumentation instrumentation) {
        instance().instrumentation = instrumentation;
        System.out.println("Classes loaded: " + instrumentation.getAllLoadedClasses().length);


        System.out.println("String size: " + instrumentation.getObjectSize(new String(new char[100]))); //"shallow" size.
        System.out.println("int[10] size: " + instrumentation.getObjectSize(new int[10]));
    }

    Instrumentation getInstrumentation() {
        return instrumentation;
    }
}
