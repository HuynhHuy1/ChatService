package org.example.src.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.example.src.Controller.ChatController;
import org.example.src.Controller.ConversationController;
import org.example.src.Model.Client;

public class HandleConnection implements Runnable {
    private Client client;
    private Server server;
    private Socket socket;

    // public HandleConnection(Client client,Server server) {
    // this.client = client;
    // this.server = server;
    // }
    public HandleConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            Scanner sc = new Scanner(socket.getInputStream());
            String method = sc.nextLine();
            System.out.println(method + "hehe cl");
            switch (method) {
                case "getListUser": {
                    int id = sc.nextInt();
                    ConversationController conversationController = new ConversationController(socket);
                    conversationController.getListConversation(id);
                    socket.close();
                    System.out.println("Close Socket`");
                    break;
                }
                case "getMessageFromConversation": {
                    int userLogin = sc.nextInt();
                    int userReceiver = sc.nextInt();
                    ConversationController conversationController = new ConversationController(socket);
                    conversationController.getListMessageByConversationID(userLogin, userReceiver);
                    socket.close();
                    break;
                }
                case "chat": {
                    System.out.println(" vao chat");
                    int userLogin = sc.nextInt();
                    int userReceiver = sc.nextInt();
                    sc.nextLine();
                    String message = sc.nextLine();                    
                    ChatController chatController = new ChatController();
                    System.out.println(userLogin + "     " + userReceiver + "    " + message);
                    chatController.addMessage(userLogin, userReceiver, message);
                    socket.close();
                    System.out.println("Close Socket`");
                    break;
                }
            }
        }

        catch (

        IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
