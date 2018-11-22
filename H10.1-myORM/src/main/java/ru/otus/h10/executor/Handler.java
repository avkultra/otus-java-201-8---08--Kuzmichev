package ru.otus.h10.executor;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface Handler {
    void handle(ResultSet result) throws SQLException;
}
