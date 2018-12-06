package ru.otus.h10.database;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.sql.*;

import ru.otus.h10.Helpers.ReflectionHelper;


public class DBCache {
    private String name = null;

    private Field[] fields;
    private Method[] methods;

    private String queryInsert;
    private String querySelect;

    private final static String SELECT_DATASET_TEMPLATE = "SELECT * from user WHERE id = ?";
    private final static String INSERT_DATASET_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s)";

    public String getName() {

        return name;
    }

    public Field[] getFields() {
        return fields;
    }

    public Method[] getMethods() {
        return methods;
    }

    public String getQueryInsert() {
        return queryInsert;
    }

    public String getQuerySelect() {
        return querySelect;
    }

    public static DBCache createCache(Class clazz) throws SQLException {
        DBCache cache = new DBCache();

        cache.name = clazz.getSimpleName();

        if (cache.name == null) {
            throw new SQLException("Class " + clazz.getSimpleName() + "doesn't have a name");
        }

        cache.fields = cacheFields(clazz);
        cache.methods = cacheMethods(clazz);

        if (cache.fields == null || cache.fields.length == 0) {
            throw new SQLException("Class " + clazz.getSimpleName() + "doesn't have any fields");
        }

        cache.querySelect = buildQuerySelect(cache);
        cache.queryInsert = buildQueryInsert(cache);

        if (cache.querySelect == null || cache.queryInsert == null) {
            throw new SQLException("Error while building queries for the class " + clazz.getSimpleName());
        }

        return cache;
    }

    private static Field[] cacheFields(Class clazz) {
        return ReflectionHelper.getFields(clazz);
    }

    private static Method[] cacheMethods(Class clazz) {
        return clazz.getMethods();
    }

    private static String buildQueryInsert(DBCache cache) {
        ArrayList<String> fieldsNames = new ArrayList<>();
        ArrayList<String> fieldsPlaceholders = new ArrayList<>();

        for (Field field : cache.fields) {
            fieldsNames.add(field.getName());
            fieldsPlaceholders.add("?");
        }

        return String.format(
                INSERT_DATASET_TEMPLATE,
                "user",
                String.join(",", fieldsNames),
                String.join(",", fieldsPlaceholders));
    }

    private static String buildQuerySelect(DBCache cache) {
        return SELECT_DATASET_TEMPLATE;
    }
}
