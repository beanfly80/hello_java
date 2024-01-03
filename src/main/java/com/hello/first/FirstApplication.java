package com.hello.first;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.*;
import java.util.Arrays;
import java.lang.*;


@SpringBootApplication
public class FirstApplication {
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

    public static void main(String[] args) {
        SpringApplication.run(FirstApplication.class, args);
    }

    private static String testDB() throws SQLException {
        // create connection for a server installed in localhost, with a user "root"
        // with no password
        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/chat_db", "root", "1")) {
            // create a Statement
            try (Statement stmt = conn.createStatement()) {
                // execute query
                try (ResultSet rs = stmt.executeQuery("select * from accounts_tbl")) {
                    // position result to first
                    rs.first();
                    System.out.println(rs.getString(1));
                    return rs.getString(1);
                }
            }
        }
    }
}
