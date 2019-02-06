package ru.otus.h13;

public class Main {

    private static final int DATA_SIZE = 4000000;
    private static final int MAX_THREADS = 10;

    public static void main(String[] args) throws Exception {

        System.out.println("Начало сортировки массива " + DATA_SIZE + " элементов");

        ArrayHelper arr = new ArrayHelper(DATA_SIZE, MAX_THREADS);
        arr.sort();
    }
}
