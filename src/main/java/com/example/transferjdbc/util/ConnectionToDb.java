package com.example.transferjdbc.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionToDb {

    @Value("${spring.datasource.url}")
    private  String url;

    @Value("${spring.datasource.username}")
    private  String username;

    @Value("${spring.datasource.password}")
    private  String password;

    public Connection getConnection() throws SQLException, IOException {
        return DriverManager.getConnection(url, username, password);
    }
}
