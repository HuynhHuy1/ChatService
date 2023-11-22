package org.example.src.client;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JEditorPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Label;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.JTextPane;

import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.example.src.Main.Server;
import org.example.src.Model.User;

import org.example.src.Model.Message;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class SimpleChatUI {

    private static String URL_DIR = System.getProperty("user.dir");
    // private static String TEMP = "/temp/";

    // private ChatRoom chat;
    private Socket socketChat;
    private String nameUser = "";
    private JFrame frameChatGui;
    private JTextField textName;
    private JPanel panelMessage;
    private JTextPane txtDisplayChat;
    private Label textState;
    private JButton btnSend;
    public boolean isStop = false, isSendFile = false, isReceiveFile = false;
    private JProgressBar progressSendFile;
    // private JTextField txtPath;
    // private int portServer = 0;
    private JTextField txtMessage;
    private JScrollPane scrollPane;
    private StyledDocument chatDocument;
    private JPanel panel_1;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private JLabel lblNewLabel_2;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SimpleChatUI window = new SimpleChatUI("HaiNam");
                    window.frameChatGui.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public SimpleChatUI(String username) {
        initialize(username);
    }

    private void initialize(String username) {
        File fileTemp = new File(URL_DIR + "/temp");
        if (!fileTemp.exists()) {
            fileTemp.mkdirs();
        }
        frameChatGui = new JFrame();
        frameChatGui.setTitle("Private Chat");
        frameChatGui.setResizable(false);
        frameChatGui.setBounds(200, 200, 909, 645);
        frameChatGui.getContentPane().setLayout(null);
        frameChatGui.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JLabel lblClientIP = new JLabel("");
        lblClientIP.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblClientIP.setBounds(30, 6, 41, 40);
        lblClientIP.setIcon(new javax.swing.ImageIcon(SimpleChatUI.class.getResource("../images/user_chat.png")));
        frameChatGui.getContentPane().add(lblClientIP);

        textName = new JTextField(nameUser);
        textName.setForeground(Color.RED);
        textName.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        textName.setEditable(false);
        textName.setBounds(70, 6, 148, 40);
        frameChatGui.getContentPane().add(textName);
        textName.setText(username);
        textName.setColumns(10);

        panelMessage = new JPanel();
        panelMessage.setBounds(6, 363, 649, 201);
        panelMessage.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Message"));
        frameChatGui.getContentPane().add(panelMessage);
        panelMessage.setLayout(null);
        txtMessage = new JTextField("");
        txtMessage.setBounds(10, 21, 479, 62);
        panelMessage.add(txtMessage);
        txtMessage.setColumns(10);

        btnSend = new JButton();
        btnSend.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnSend.setBounds(551, 33, 65, 39);
        btnSend.setBorder(new EmptyBorder(0, 0, 0, 0));
        btnSend.setContentAreaFilled(false);
        panelMessage.add(btnSend);
        btnSend.setIcon(new javax.swing.ImageIcon(SimpleChatUI.class.getResource("../images/send.png")));

        btnSend.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                String msg = txtMessage.getText();
                if (msg.equals(""))
                    return;
                try {
                    updateChat_send(msg);
                    updateChat_receive(msg);
                    txtMessage.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        progressSendFile = new JProgressBar(0, 100);
        progressSendFile.setBounds(115, 578, 388, 14);
        progressSendFile.setStringPainted(true);
        frameChatGui.getContentPane().add(progressSendFile);
        progressSendFile.setVisible(false);

        textState = new Label("");
        textState.setBounds(6, 570, 158, 22);
        textState.setVisible(false);
        frameChatGui.getContentPane().add(textState);

        txtDisplayChat = new JTextPane();
        txtDisplayChat.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDisplayChat.setEditable(false);
        txtDisplayChat.setContentType("text/html");
        txtDisplayChat.setMargin(new Insets(6, 6, 6, 6));
        txtDisplayChat.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);
        txtDisplayChat.setBounds(6, 59, 670, 291);
        String message = "<div style='color: black;'><b>You:</b> Hello, World!</div><br>";
        appendToPane(txtDisplayChat, message);
        frameChatGui.getContentPane().add(txtDisplayChat);

        scrollPane = new JScrollPane(txtDisplayChat);
        scrollPane.setBounds(6, 59, 649, 291);
        frameChatGui.getContentPane().add(scrollPane);

        JPanel panel = new JPanel();
        panel.setBounds(665, 6, 222, 592);
        frameChatGui.getContentPane().add(panel);
        panel.setLayout(null);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(215, 593, -207, -586);
        panel.add(scrollPane_1);

        renderUserChatted(panel, username);

        this.frameChatGui.setVisible(true);
    }

    public void renderUserChatted(JPanel panel, String userName) {
        String serverHost = "localhost";
        int serverPort = 12345;

        try (Socket socket = new Socket(serverHost, serverPort);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
            // Gửi yêu cầu lấy danh sách người dùng đến máy chủ
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("getListUser");

            int margin = 0;

            System.out.println("hello");
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            java.util.List<User> listUser = (java.util.List<User>) ois.readObject();
            System.out.println(listUser.size() + "hello");

            for (User user : listUser) {
                // System.out.println(user.getUsername());
                panel_1 = new JPanel();
                panel_1.setBackground(new Color(255, 255, 255));
                panel_1.setBounds(10, 54 + margin, 202, 86);
                panel.add(panel_1);
                panel_1.setLayout(null);

                lblNewLabel = new JLabel();
                lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
                lblNewLabel.setBounds(0, 0, 60, 86);
                panel_1.add(lblNewLabel);

                ImageIcon originalIcon = new ImageIcon(SimpleChatUI.class.getResource("../images/groom_friend2.jpg"));

                Image originalImage = originalIcon.getImage();

                Image scaledImage = originalImage.getScaledInstance(46, 46,
                        Image.SCALE_SMOOTH);

                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                lblNewLabel.setIcon(scaledIcon);

                lblNewLabel_1 = new JLabel(user.getUsername());
                lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
                lblNewLabel_1.setBounds(70, 28, 85, 13);
                panel_1.add(lblNewLabel_1);

                lblNewLabel_2 = new JLabel("Cai lon");
                lblNewLabel_2.setForeground(Color.GRAY);
                lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblNewLabel_2.setBounds(70, 51, 85, 13);
                panel_1.add(lblNewLabel_2);
                margin += 120;
                panel_1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            System.out.println("click user");
                            int userLoginId = 1;
                            int userReceiver = user.getId();
                            List<Message> listMessage = getListMessageFromServer(userLoginId, userReceiver);
                            renderConversation(listMessage);
                        } catch (Exception e1) {
                            System.out.println(e1.getMessage() + "click");
                        }

                    }
                });
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Message> getListMessageFromServer(int userLoginId, int userReceiverID) {
        try {
            Socket socket = new Socket("localhost", 12345);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("getMessageFromConversation");
            out.println(userLoginId);
            out.println(userReceiverID);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            List<Message> listMessage = (List<Message>) ois.readObject();
            System.out.println(listMessage.size());
            return listMessage;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage() + "GetList");
            e.printStackTrace();
        }
        return null;
    }

    public void renderConversation() {

    }

    // public synchronized void sendMessage(Object obj) throws Exception {
    // outPeer = new ObjectOutputStream(connect.getOutputStream());
    // // only send text
    // if (obj instanceof String) {
    // String message = obj.toString();
    // outPeer.writeObject(message);
    // outPeer.flush();
    // if (isReceiveFile)
    // isReceiveFile = false;
    // }
    // // send attach file
    // else if (obj instanceof DataFile) {
    // outPeer.writeObject(obj);
    // outPeer.flush();
    // }
    // }
    private void appendToPane(JTextPane tp, String msg) {
        HTMLDocument doc = (HTMLDocument) tp.getDocument();
        HTMLEditorKit editorKit = (HTMLEditorKit) tp.getEditorKit();
        try {
            editorKit.insertHTML(doc, doc.getLength(), msg, 0, 0, null);
            tp.setCaretPosition(doc.getLength());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateChat_send(String msg) {
        appendToPane(txtDisplayChat, "<table class='bang' style='color: white; clear:both; width: 100%;'>"
                + "<tr align='right'>"
                + "<td style='width: 59%; '></td>"
                + "<td style='width: 40%; background-color: #0084ff;'>" + msg
                + "</td> </tr>"
                + "</table>");
    }

    public void updateChat_receive(String msg) {
        appendToPane(txtDisplayChat,
                "<div class='left' style='width: 40%; background-color: #f1f0f0;'>" + msg + "</div>");
    }

    public void renderConversation(List<Message> listMessage) {
        txtDisplayChat.setText("");
        for (Message message : listMessage) {
            if(message.getSender_id() == 1){
                updateChat_send(message.getContent());
            }
            else{
                updateChat_receive(message.getContent());
            }
        }
    }
}