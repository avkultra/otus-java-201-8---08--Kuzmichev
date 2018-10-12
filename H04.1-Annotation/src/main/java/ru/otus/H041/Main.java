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
     public static void main(String[] args) throws IOException, URISyntaxException, ClassNotFoundException {

         classWithTest();
         System.out.println();
         classWithPackage();
        }


    private static void classWithTest() {
        System.out.println("ClassWithTest");

        AllTest tst = new AllTest(TestsClass.class);
        tst.runTests();
     }





    private static void classWithPackage() throws IOException, URISyntaxException, ClassNotFoundException{
        String pckName ="H04.1-Annotation.jar";
        System.out.println("ClassWhithPackage with " + pckName);
        AllTest tst = new AllTest(pckName);
        tst.runTests();
    }

}