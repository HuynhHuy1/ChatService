package org.example.src.Controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.src.Model.Client;
import org.example.src.Model.Message;
import org.example.src.Model.User;
import org.example.src.Repository.ConversationRepository;
import org.example.src.Repository.MessageRepository;
import org.example.src.rmi.RMIServiceInterface;

public class ConversationController {
    private Client client;
    private List<Client> listClient;
    private String userReceiver;
    private Socket socket;
    private ObjectOutputStream out;

    public ConversationController(Socket socket) {
        this.socket = socket;
    }

    public void getListConversation(int id) {
        try {
            System.setProperty("sun.rmi.transport.tcp.responseTimeout", "5000");
            RMIServiceInterface iAccount = (RMIServiceInterface) Naming.lookup("rmi://34.143.232.173:30005/RMIService");
            List<Integer> listUserId = ConversationRepository.getConversations(id);
            out = new ObjectOutputStream(socket.getOutputStream());
            List<User> listUser = iAccount.getListUserByListId(listUserId);
            // List<User> listUser = new ArrayList<>();
            // listUser.add(new User(id, userReceiver, userReceiver, userReceiver, userReceiver, null));
            // listUser.add(new User(id, userReceiver, userReceiver, userReceiver, userReceiver, null));
            System.out.println(listUser.size());
            out.writeObject(listUser);
        } catch (java.rmi.ConnectException e) {
            System.out.println(e.getMessage() + " get list");
            // Xử lý lỗi kết nối RMI ở đây.
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void getListMessageByConversationID(int userLoginId, int userReceiverId) {
        try {
            int conversationID = ConversationRepository.getConversationId(userLoginId, userReceiverId);
            List<Message> listMessage = MessageRepository.getMessageByConversationId(conversationID);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(listMessage);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}