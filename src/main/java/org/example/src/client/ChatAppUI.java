package org.example.src.client;

import javax.swing.*;

import org.example.src.Model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ChatAppUI {
    public static void main(String[] args) throws UnknownHostException, IOException {
        JFrame frame = new JFrame("Chat App");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket;
                try {
                    socket = new Socket("localhost",12345);
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    out.println("getConversations");
                    out.flush();
                    List<User> listUser;
                    listUser = (List<User>) in.readObject();
                    System.out.println(listUser.size());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } 

            }
        }).start();;


        // Tạo danh sách người chat
        JPanel userListPanel = new JPanel();
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));
        
        // Tạo danh sách các thành phần (ví dụ: JLabels)
        List<JLabel> userLabels = new ArrayList<>();
         List<String> userList = Arrays.asList("huy", "huy", "huy");
        // Thêm các thành phần vào userListPanel
        for (String userName : userList) {
            JLabel userLabel = new JLabel(userName);
            userLabels.add(userLabel);
            userListPanel.add(userLabel);
        }
        
        // Thêm userListPanel vào giao diện chính
        frame.add(userListPanel, BorderLayout.WEST);

        // Tạo khung chat
        JTextArea chatTextArea = new JTextArea(10, 40);
        chatTextArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatTextArea);

        // Tạo khung nhập tin nhắn
        JTextField messageTextField = new JTextField(30);
        JButton sendButton = new JButton("Send");

        // Sắp xếp các thành phần trên giao diện
        userListPanel.setPreferredSize(new Dimension(200, 0)); // Danh sách người chat
        userListPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(messageTextField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        frame.setLayout(new BorderLayout());
        frame.add(userListPanel, BorderLayout.WEST);
        frame.add(chatPanel, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Xử lý sự kiện khi nhấn nút "Send"
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageTextField.getText();
                if (!message.isEmpty()) {
                    chatTextArea.append("You: " + message + "\n");
                    messageTextField.setText("");
                }
            }
        });
    }
}
