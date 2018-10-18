package ru.otus.H061.CacheClasses;

public class ElementCache<K, V> {

    private final K key;
    private final V value;
    // Время создания
    private final long creationTime;
    // Последние время доступа
    private long lastAccessTime;


    public ElementCache(K key, V value) {
        this.key = key;
        this.value = value;
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setAccessed() {
        lastAccessTime = getCurrentTime();
    }
}
