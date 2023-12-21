package org.example.src.Controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.example.src.Model.Client;
import org.example.src.Model.Response;
import org.example.src.Repository.ConversationRepository;
import org.example.src.Repository.MessageRepository;
import org.example.src.Utils.ResponseUtil;
import org.example.src.rmi.RMIChatServiceInterface;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatController {
    private Client client;
    private List<Client>  listClient;
    private String userReceiver;


    public Client getClientReceiver(){
        for (Client client : listClient) {
            if(client.getUserName().equals(userReceiver)){
                return client;
            }
        }
        return null;
    }

    public void handleChat(String message) throws IOException{
        Client clientReceiver = this.getClientReceiver();
        if(clientReceiver != null){
            ResponseUtil responseUtil= new ResponseUtil(clientReceiver.getSocket());
            System.out.println(message);
            System.out.println(clientReceiver.getUserName());
            responseUtil.redirectMessageClient(message);
        }
    }
    public void addMessage(int userLogin, int userReceiver,String content){
        int conversationId = ConversationRepository.getConversationId(userLogin, userReceiver);
        System.out.println(conversationId);
        MessageRepository.addNewMessage(conversationId, userLogin,content);
        try {
            List<String> urlServer = new ArrayList<>();
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("IP của máy chủ 1: " + localhost.getHostAddress());
            RMIChatServiceInterface rmiChatService = (RMIChatServiceInterface) Naming.lookup("rmi://192.168.1.132:3099/ChatService");
            rmiChatService.updateChatRemote(userLogin, userReceiver, content);
        } catch (MalformedURLException | RemoteException | NotBoundException | UnknownHostException e) {
            e.printStackTrace();
        }
    }
}