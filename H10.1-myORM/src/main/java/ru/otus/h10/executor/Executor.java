package ru.otus.h10.executor;

import java.sql.*;

public class Executor {

    private final Connection connection;

    Connection getConnection() {
        return connection;
    }

    @FunctionalInterface
    public interface PrepareHandler {
        void handle(PreparedStatement statement) throws SQLException;
    }



    public Executor(Connection connection) {
        this.connection = connection;
    }

    public void execQuery(String query, Handler handler) throws SQLException {
        try (Statement stm = connection.createStatement()) {
            stm.execute(query);
            ResultSet resset = stm.getResultSet();
            handler.handle(resset);
        }
    }

    public <T> T execQuery(String query, THandler<T> handler) throws SQLException {
        try(Statement stm = connection.createStatement()) {
            stm.execute(query);
            ResultSet resset = stm.getResultSet();
            return handler.handle(resset);
        }
    }


    public int execUpdate(String update) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(update);
            return stmt.getUpdateCount();
        }
    }

    public void execUpdate(String update, PrepareHandler prepare) {
        try {
            PreparedStatement prep = getConnection().prepareStatement(update, Statement.RETURN_GENERATED_KEYS);
            prepare.handle(prep);
            prep.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
