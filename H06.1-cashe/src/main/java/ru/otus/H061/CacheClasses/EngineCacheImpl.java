package ru.otus.H061.CacheClasses;
import java.util.*;
import java.util.function.Function;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;



public class EngineCacheImpl<K, V> implements EngineCaсhe<K, V> {

    private static final int TIME_THRESHOLD_MS = 5;

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final Map<K, SoftReference<ElementCache<K, V>>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    private int hit = 0;
    private int miss = 0;

   public EngineCacheImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
    }

    @Override
    public void put(ElementCache<K, V> element) {

        System.out.println("put : key = " + element.getKey() + " val = "+ element.getValue());

        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
            System.out.println("max cache size. remove =" + firstKey);
        }

        K key = element.getKey();
        elements.put(key, new SoftReference<>(element));


        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
            }
        }
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public int max_size(){
        return maxElements;
    }

    @Override
    public void put(K key, V value) {
        put(new ElementCache<>(key, value));
    }

    @Override
    public V get(K key) {
        SoftReference<ElementCache<K, V>> softRef = elements.get(key);

        ElementCache<K, V> element = softRef != null ? softRef.get() : null;
        if (element != null) {
            System.out.println("hit = " + key);
            hit++;
            element.setAccessed();
            return element.getValue();
        } else {
            System.out.println("miss = " + key);
            miss++;
            return null;
        }
    }

    @Override
    public List<V> getAll() {
        List<V> values = new ArrayList<>();
        for(K key : elements.keySet()) {
            V value = get(key);
            if (value != null) {
                values.add(value);
            }
        }
        return values;
    }

    @Override
    public int getHitCount() {
        return hit;
    }

    @Override
    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
        elements.clear();
    }

    private TimerTask getTimerTask(final K key, Function<ElementCache<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                ElementCache<K, V> element = elements.get(key).get();
                if (element == null || isT1BeforeT2(timeFunction.apply(element), System.currentTimeMillis())) {
                   System.out.println("remove time = " + key);
                    elements.remove(key);
                    this.cancel();
                }
            }
        };
    }

    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }
}
