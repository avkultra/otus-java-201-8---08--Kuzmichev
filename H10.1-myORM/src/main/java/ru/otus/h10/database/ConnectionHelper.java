package ru.otus.h10.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionHelper {

    private ConnectionHelper() {
        }

    public static Connection getConnection() {
            try {
                    DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                    String url = "jdbc:mysql://" +       //db type
                        "localhost:" +               //host name
                        "3306/" +                    //port
                        "db_example?" +              //db name
                        "user=tully&" +              //login /**/
                        "password=tully&" +          //password /**/
                        "useSSL=false";              //do not use Secure Sockets Layer
                return DriverManager.getConnection(url);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
