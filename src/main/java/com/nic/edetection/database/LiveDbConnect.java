package com.nic.edetection.database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author soumya
 */
public class LiveDbConnect {

    public Connection connection;

    public Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://10.246.40.187:5432/vow4", "or", "or#40018");
            // System.out.println("Connection to live Db Established");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}
