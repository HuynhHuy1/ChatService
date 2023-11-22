package org.example.src.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.src.Model.Message;
import org.example.src.Utils.ConnectionUtil;

public class MessageRepository {
    public static Connection connection = ConnectionUtil.getConnection();

    // public static Message getNMessage(String email, String password) throws
    // SQLException {
    // String sql = "SELECT * FROM users WHERE email = ? AND password = ? ";
    // PreparedStatement preparedStatement = connection.prepareStatement(sql);
    // preparedStatement.setString(1, email);
    // preparedStatement.setString(2, password);

    // ResultSet resultSet = preparedStatement.executeQuery();
    // if (!resultSet.next()) {
    // return null;
    // } else {
    // System.out.println("vao day roi");
    // while (resultSet.next()) {
    // }
    // return null;
    // }
    // }

    // public static boolean getUserByEmail(String email) throws SQLException {
    // String sql = "SELECT * FROM users WHERE email = ?";
    // PreparedStatement preparedStatement = connection.prepareStatement(sql);
    // preparedStatement.setString(1, email);
    // ResultSet resultSet = preparedStatement.executeQuery();
    // if (!resultSet.next()) {
    // return false;
    // }
    // return true;
    // }
    // id conversation_id sender_id content timestamp
    public static void addMessage() throws SQLException {
        String sql = "INSERT INTO message (email, password) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // preparedStatement.setString(1, email);
        // preparedStatement.setString(2, password);
        int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Thêm người dùng thành công!");
        }
    }

    public static List<Message> getMessageByConversationId(int conversationId) {
        String sql = "SELECT * FROM `message` m " +
                "WHERE m.conversation_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, conversationId);
            List<Message> listMessage = new ArrayList<>();
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int conversation_id = rs.getInt("conversation_id");
                int sendId = rs.getInt("sender_id");
                String content = rs.getString("content");
                Message message = new Message(id, conversation_id, sendId, content);
                listMessage.add(message);
            }
            return listMessage;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void addNewMessage(int conversationId, int senderId, String content) {
        String sql = "INSERT INTO `message`(`conversation_id`, `sender_id`, `content`) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, conversationId);
            preparedStatement.setInt(2, senderId);
            preparedStatement.setString(3, content);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Thêm tin nhan thành công!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
