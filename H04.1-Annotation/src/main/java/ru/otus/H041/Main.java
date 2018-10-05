package ru.otus.h041;
/*
ДЗ 04. Тестовый фреймворк на аннотациях
Написать свой тестовый фреймворк. Поддержать аннотации @Test, @Before, @After.
Запускать вызовом статического метода с
1. именем класса с тестами,
2. именем package в котором надо найти и запустить тесты 
*/


public class Main {
     public static void main(String[] args) {

         classWithTest();
         System.out.println();

         classWithPackage();
        }
    }


    private static void classWithTest() {
        System.out.println("ClassWithTest");
     }

    private static void classWithPackage() {
        String package ="ru.otus.h041.TestsClass";
        System.out.println("ClassWhithPackage with " + package);

    }