package ru.otus.h13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {

    private static final int DATA_SIZE = 4000000;
    private static final int MAX_THREADS = 4;
    private static final int REPEAT_COUNT = 10;

    private static int[] out;
    private static int threadsCount;
    private static long sortTime = 0;



    public static void main(String[] args) throws Exception {

        System.out.println("Начало сортировки массива " + DATA_SIZE + " элементов");

        int[] data1 = createArray(DATA_SIZE);

        for (threadsCount = 1; threadsCount <= MAX_THREADS; threadsCount++) {
            for (int k = 0; k < REPEAT_COUNT; k++) {
                Thread worker = new Thread(() -> {
                    try {
                        long t1 = System.currentTimeMillis();
                        threadsCount = checkThreads(data1.length, threadsCount);
                        out = sortInThreadsSync(data1, threadsCount);
                        long t2 = System.currentTimeMillis();
                        sortTime += t2 - t1;

                    } catch (Exception e) {

                        System.out.println("Ошибка сортировки " + e);
                    }
                });
                worker.start();
                worker.join();
            }
            sortTime = sortTime / REPEAT_COUNT;

            System.out.println("Поток #" + threadsCount + " время " + sortTime + " мс");

            out = null;
            System.gc();
            Thread.sleep(10);
        }

        for (int k = 0; k < REPEAT_COUNT; k++) {

            int[] data2 = createArray(DATA_SIZE);
            long t1 = System.currentTimeMillis();
            Arrays.sort(data2, 0, data2.length);
            long t2 = System.currentTimeMillis() - t1;
            System.out.println("библиотечная сортировка: " + t2 + "мс");
        }

        System.out.println("Конец");
    }

    static int[] sortInThreadsSync(int[] data, int threadsCount) throws Exception {

        int sizeData = data.length;

        if (threadsCount > sizeData/2) {
            threadsCount = sizeData/2;
        }
        int sizePart = sizeData / threadsCount;
        int iStart = 0;

        Thread[] threads = new Thread[threadsCount];
        List<int[]> dataParts = new ArrayList<>();

        for (int i = 0; i < threadsCount; i++) {
            int partEnd = ((i == threadsCount - 1) ? sizeData : (iStart + sizePart));

            int[] part = Arrays.copyOfRange(data, iStart, partEnd);
            dataParts.add(part);

            threads[i] = new Thread(new SortTask(part));
            threads[i].start();

            iStart += sizePart;
        }

        for (int i = 0; i < threadsCount; i++) {
            threads[i].join();
        }

        if (1 == threadsCount) {
            return dataParts.get(0);
        } else {
            int[] res = mergeSorted(dataParts.get(0), dataParts.get(1));
            for (int i = 2; i < threadsCount; i++) {
                res = mergeSorted(res, dataParts.get(i));
            }
            return res;
        }
    }


    private static int checkThreads(int sizeData, int threadsCount) {

        if (sizeData < 10) return 1;

        while (sizeData/10 < threadsCount) {
            threadsCount = threadsCount/2;
        }
        return threadsCount;
    }

    static int[] mergeSorted(int[] src1, int[] src2) {
        int[] out = new int[src1.length + src2.length];

        if (src1.length == 0) {
            return Arrays.copyOf(src2, src2.length);
        }
        if (src2.length == 0) {
            return Arrays.copyOf(src1, src1.length);
        }

        int i1 = 0;
        int i2 = 0;

        for (int j = 0; j < out.length; j++) {
            if (src1[i1] < src2[i2]) {
                out[j] = src1[i1];
                i1++;
                if (i1 == src1.length) {
                    System.arraycopy(src2, i2, out, j + 1, src2.length - i2);
                    break;
                }
            } else {
                out[j] = src2[i2];
                i2++;
                if (i2 == src2.length) {
                    System.arraycopy(src1, i1, out, j + 1, src1.length - i1);
                    break;
                }
            }
        }
        return out;
    }

    private static int[] createArray(int size) {
        Random rnd = new Random();
        IntStream is = rnd.ints(size);
        return is.toArray();
    }

    static class SortTask implements Runnable {

        private int[] data;

        SortTask(int[] data) {
            this.data = data;
        }

        @Override
        public void run() {
            Arrays.sort(data, 0, data.length);
        }
    }
}
