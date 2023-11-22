package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SwingChatApplication extends JFrame {
    private DefaultListModel<String> contactListModel;
    private JList<String> contactList;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;

    public SwingChatApplication() {
        setTitle("Chat Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        // Tạo danh sách liên hệ
        contactListModel = new DefaultListModel<>();
        contactList = new JList<>(contactListModel);
        contactList.setFont(new Font("Arial", Font.PLAIN, 16));
        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactList.setBackground(Color.LIGHT_GRAY);
        contactList.setSelectionBackground(Color.GRAY);

        // Tạo hiệu ứng hover
        contactList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                contactList.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                contactList.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        // Thêm danh sách liên hệ vào JScrollPane
        JScrollPane contactScrollPane = new JScrollPane(contactList);
        contactScrollPane.setPreferredSize(new Dimension(200, 0));
        contactScrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));

        add(contactScrollPane, BorderLayout.WEST);

        // Tạo khung chat
        JPanel chatPanel = new JPanel(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setFont(new Font("Arial", Font.PLAIN, 16));
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);

        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);

        JPanel messagePanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        messageField.setFont(new Font("Arial", Font.PLAIN, 16));
        messagePanel.add(messageField, BorderLayout.CENTER);
        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.BOLD, 16));
        messagePanel.add(sendButton, BorderLayout.EAST);
        chatPanel.add(messagePanel, BorderLayout.SOUTH);

        add(chatPanel, BorderLayout.CENTER);

        // Xử lý sự kiện nút "Send"
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Thêm một số danh bạ mẫu
        contactListModel.addElement("Alice");
        contactListModel.addElement("Bob");

        pack();
        setLocationRelativeTo(null);
    }

    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            chatArea.append("You: " + message + "\n");
            messageField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingChatApplication app = new SwingChatApplication();
        app.setVisible(true);
    }
}
