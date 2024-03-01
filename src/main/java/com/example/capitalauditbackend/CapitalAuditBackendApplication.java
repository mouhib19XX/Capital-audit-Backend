package com.example.capitalauditbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.example.capitalauditbackend.DOA.DatabaseConnector;

import java.sql.Connection;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CapitalAuditBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CapitalAuditBackendApplication.class, args);
        DatabaseConnector db = new DatabaseConnector();
        db.connect();
        Connection connection = db.getConnection();
        db.setConnection(connection);


    }
}
