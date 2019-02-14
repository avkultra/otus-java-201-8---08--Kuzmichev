package ru.otus.h031;
import ru.otus.H031.MyArrayList;

import java.security.InvalidParameterException;
import java.util.*;

public class Main {
     public static void main(String... args) {
        //addAll();
        //copy();
        //sort();

         MyArrayList<Integer> list = new MyArrayList<>();

         for(int i = 0; i < 61; i++){
             list.add(i);
         }
    }

    private static void addAll() {
        System.out.println();
        System.out.println("Test addAll");
        MyArrayList<String> list = new MyArrayList<String>();
        System.out.println("List before: Empty = " + list.isEmpty());
        ///////
        Collections.addAll(list, "first", "second", "third");
        ///////
        System.out.println("List after: " + list.toString());
      }

    private static void copy() {
        System.out.println();
        System.out.println("Test copy");
        ArrayList<String> arrLst = new ArrayList() ;
        Collections.addAll(arrLst, "first", "second", "third");

        List<String> alst = Arrays.asList(new String[arrLst.size()]);
        MyArrayList<String> end = new MyArrayList<String>(alst);

        System.out.println("List before: " + arrLst.toString());
        System.out.println("End list: Empty = " + end.isEmpty());
        ///////
        Collections.copy(end, arrLst);
        ///////
        System.out.println("End list after copy(): " + end.toString());
       }

    private static void sort() {
        System.out.println();
        System.out.println("Test sort");
        MyArrayList<String> list = new MyArrayList() ;
        Collections.addAll(list, "first", "second", "third");

        System.out.println("List before: " + list.toString());
        ///////
        Collections.sort(list, new Comparator<String>() {

            public int compare(String s_1, String s_2) {
                if (null == s_1 || null == s_2)
                    throw new InvalidParameterException("cannot be null");

                return s_1.compareTo(s_2);
            }

        });
        ///////
        System.out.println("List after: " + list.toString());

    }
}
