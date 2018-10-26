package ru.otus.h041;
/*
ДЗ 04. Тестовый фреймворк на аннотациях
Написать свой тестовый фреймворк. Поддержать аннотации @Test, @Before, @After.
Запускать вызовом статического метода с
1. именем класса с тестами,
2. именем package в котором надо найти и запустить тесты 
*/



import ru.otus.H041.TestsClass;
import ru.otus.H041.AllTest;

import java.io.IOException;
import java.net.URISyntaxException;


public class Main {
     public static void main(String[] args) {
             try {
                 classWithTest();
                 System.out.println();
                 //classWithPackage();
             }
             catch(Exception ex)
             {
                System.out.println( ex.getMessage() );
             }
        }


    private static void classWithTest()  {
        System.out.println("ClassWithTest");

        AllTest tst = new AllTest(TestsClass.class);
        tst.runTests();
     }





    private static void classWithPackage()throws IOException, URISyntaxException, ClassNotFoundException  {
        String pckName ="ru.otus.H041.TestClass";
        System.out.println("ClassWhithPackage with " + pckName);
        AllTest tst = new AllTest(pckName);
        tst.runTests();
    }

}