package ru.otus.H051;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.*;

public class MemoryStat implements Runnable {

    private final static int SEC = 1000;

    private final static int STAT_VAL= 60000 / SEC;

    private Map<String, MetricsGC> gcMetricMap = new HashMap<>();


    public void run() {
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();

         System.out.println("собираем статистику от" +  gcBeans.size() + "GC");

        while(true) {
            for (GarbageCollectorMXBean bean : gcBeans) {
                MetricsGC metricsgc = gcMetricMap.getOrDefault(bean.getName(), new MetricsGC());
                metricsgc.addTime(bean.getCollectionTime(), bean.getCollectionCount());
                gcMetricMap.put(bean.getName(), metricsgc);
            }
            pause();
            //Выведем статистику
            log();
        }
    }

    private void pause() {
        try {
            Thread.sleep(SEC);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void log() {
        int count = 0;
        for (String name : gcMetricMap.keySet()) {
            System.out.println("["+ (++count) + "]"+ name);
            MetricsGC metrics = gcMetricMap.get(name);
            System.out.println("count = " + metrics.getCount() + " AllCount = " + metrics.getAllCount());
            System.out.println(" time = " + metrics.getTime() + " AllTime = " +  metrics.getAllTime());
        }
        System.out.println("----------------------------");
    }

    private static class MetricsGC {

        MetricsGC() {

        }
        Deque<Long> tDec = new ArrayDeque<>();
        Deque<Long> cDec = new ArrayDeque<>();
        //предыдущее время
        private long preT;
        //предыдущее счётчик
        private long preC;

        void addTime(long time, long count) {
            long countDiff = count - preC;
            if (cDec.size() >= STAT_VAL) {
                cDec.pollFirst();
            }
            //Доб разницу счётчиков
            cDec.addLast(countDiff);
            preC = count;

            long timeDiff = time - preT;
            if (tDec.size() >= STAT_VAL) {
                tDec.pollFirst();
            }
            //Доб разницу времён
            tDec.addLast(timeDiff);
            preT = time;
        }

        long getAllCount() {
            long res = 0;
            for (Long cnt : cDec) {
                res += cnt;
            }
            return res;
        }

        long getAllTime() {
            long res = 0;
            for (Long t : tDec) {
                res += t;
            }
            return res;
        }

        long getTime() {
            return tDec.peekLast();
        }

        long getCount() {
            return cDec.peekLast();
        }


    }
}
