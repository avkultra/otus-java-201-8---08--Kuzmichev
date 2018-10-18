package ru.otus.H061;
import ru.otus.H061.CacheClasses.*;

public class Main {

    public static void main(String[] args) throws Exception {
        softReference();
        //lifeCache();
        //idleTime();
     }

        private static void softReference() throws Exception {
            final int cacheSize = 10;

             EngineCaсhe<Integer, String> engineCache = new EngineCacheImpl<>(cacheSize, 0, 0, true);

            for (int i = 0; i < 20; i++) {
                engineCache.put(new ElementCache<>(i, "Name: " + i));
            }

            for (int i = 0; i < 20; i++) {
                String name = engineCache.get(i);
                System.out.println("name = " + (name != null ? name : "null"));
            }

            System.out.println("Cache hits: " + engineCache.getHitCount());
            System.out.println("Cache misses: " + engineCache.getMissCount());

            engineCache.dispose();
        }



    private static void lifeCache() throws Exception {
        final int cacheSize = 100;
        EngineCaсhe<Integer, String> cacheEngine = new EngineCacheImpl<>(cacheSize, 1000, 0, false);

        for (int i = 0; i < cacheSize; i++) {
            cacheEngine.put(new ElementCache<>(i, "String: " + i));
        }

        for (int i = 0; i < cacheSize; i++) {
            String name = cacheEngine.get(i);
            System.out.println("name = " + (name != null ? name : "null"));
        }

        System.out.println("Cache hits: " + cacheEngine.getHitCount());
        System.out.println("Cache misses: " + cacheEngine.getMissCount());

        Thread.sleep(1000);

        for (int i = 0; i < cacheSize; i++) {
            String name = cacheEngine.get(i);
            System.out.println("name = " + (name != null ? name : "null"));
        }

        System.out.println("Cache hits: " + cacheEngine.getHitCount());
        System.out.println("Cache misses: " + cacheEngine.getMissCount());

        cacheEngine.dispose();
    }


    private static void idleTime() throws Exception{
        final int cacheSize = 100;
        EngineCaсhe<Integer, String> cacheEngine = new EngineCacheImpl<>(cacheSize, 0, 100, false);

        for (int i = 0; i < cacheSize; i++) {
            cacheEngine.put(new ElementCache<>(i, "String: " + i));
        }

        for (int i = 0; i < cacheSize; i++) {
            String name = cacheEngine.get(i);
            System.out.println("name = " + (name != null ? name : "null"));
        }

        System.out.println("Cache hits: " + cacheEngine.getHitCount());
        System.out.println("Cache misses: " + cacheEngine.getMissCount());

        Thread.sleep(200);

        for (int i = 0; i < cacheSize; i++) {
            String name = cacheEngine.get(i);
            System.out.println("name = " + (name != null ? name : "null"));
        }

        System.out.println("Cache hits: " + cacheEngine.getHitCount());
        System.out.println("Cache misses: " + cacheEngine.getMissCount());

        cacheEngine.dispose();
    }
}


