package com.nic.edetection.database;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Bibhuti
 */
@Component
public class LiveDbConnect {

	@Value("${livedb.url}")
	String url;
	@Value("${livedb.username}") 
	String username ;
	@Value("${livedb.password}")
	String password ;
	

    public Connection connection;

    public Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
//            connection = DriverManager.getConnection("jdbc:postgresql://10.246.40.238:5444/vow4", "or", "or#40018");
            System.out.println(url+" "+username+" "+password);
            connection = DriverManager.getConnection(url,username,password);
            // System.out.println("Connection to live Db Established");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}
