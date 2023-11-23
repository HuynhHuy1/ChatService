package org.example.src.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306" + "/" + "ChatService";
        // String url = "jdbc:mysql://chatservicedb.devops-tools.svc.cluster.local:3306" + "/" + "ChatService";

        System.out.println(url);
        String username = "root";
        String password = "";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("ket noi thanh cong");
            return connection;
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối đến MySQL: " + e.getMessage());
            return null;
        }
    }
}
