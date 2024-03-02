package com.example.capitalauditbackend;

import com.example.capitalauditbackend.DOA.DatabaseConnector;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.crypto.Data;

@SpringBootTest
public class DatabaseConnectorTests {

    @Test
    void connectTest()
    {
        for(int i = 0; i < 10; i++)
        {
            DatabaseConnector db = new DatabaseConnector();
            db.connect();
        }
    }
}
