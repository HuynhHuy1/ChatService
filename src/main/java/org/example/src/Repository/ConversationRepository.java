package org.example.src.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.src.Utils.ConnectionUtil;

public class ConversationRepository {
    public static Connection connection = ConnectionUtil.getConnection();

    public static List<Integer> getConversations(int userId) throws SQLException {
        String sql = "SELECT c.user_create_id as another_user_id FROM conversation c WHERE c.another_user_id = ? " +
                "UNION " +
                "SELECT c.another_user_id FROM conversation c WHERE c.user_create_id = ?";
        List<Integer> listUserId = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, userId);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int userAnotherId = resultSet.getInt("another_user_id");
            listUserId.add(userAnotherId);
        }
        System.out.println(listUserId.get(0));
        return listUserId;
    }

    public static int getConversationId(int userLogin, int anotherUser) {
        String sql = "SELECT * FROM `conversation` c " +
                "WHERE ( c.user_create_id = ? AND c.another_user_id = ? ) or (c.user_create_id = ? and c.another_user_id = ?)";
        int conversationId = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userLogin);
            preparedStatement.setInt(2, anotherUser);
            preparedStatement.setInt(3, anotherUser);
            preparedStatement.setInt(4, userLogin);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                conversationId = resultSet.getInt("id");
            }
            return conversationId;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return conversationId;
        }
    }

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
}
