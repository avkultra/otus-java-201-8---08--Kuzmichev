package ru.otus.H061.CacheClasses;

import java.util.List;

public interface EngineCaсhe<K, V> {

    void put(ElementCache<K, V> element);

    void put(K key, V value);

    V get(K key);

    List<V> getAll();

    int getHitCount();

    int getMissCount();

    void dispose();

    int size();

    int max_size();
}