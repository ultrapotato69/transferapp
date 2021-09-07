package com.example.transferjdbc.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class InitDB {
    private final Connection connection;


    @Autowired
    public InitDB(final DataSource dataSource) throws SQLException, IOException {
        this.connection = dataSource.getConnection();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() throws SQLException {
        try (Statement stat = connection.createStatement();) {
            readSQLScriptFromFile(stat, "src/main/resources/schema.sql");
            if (!selectTest(stat)){
                readSQLScriptFromFile(stat, "src/main/resources/data.sql");
            }
        }
    }

    private void readSQLScriptFromFile(Statement stat, String pathName) {
        String s;
        StringBuffer sb = new StringBuffer();
        FileReader fr;
        try {
            fr = new FileReader(pathName);

            BufferedReader br = new BufferedReader(fr);
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            br.close();
            String[] inst = sb.toString().split(";");
            for (int i = 0; i < inst.length; i++) {
                if (!inst[i].trim().equals("")) {
                    stat.executeUpdate(inst[i]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean selectTest(Statement stat) throws SQLException {
        boolean isResult = false;
        try (ResultSet result = stat.executeQuery("SELECT * FROM account"))
        {
            if (result.next()) {
                isResult = true;
            }
        } finally {
            return isResult;
        }
    }
}
