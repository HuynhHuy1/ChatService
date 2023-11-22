package org.example.src.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.example.src.Controller.ChatController;
import org.example.src.Repository.ConversationRepository;
import org.example.src.Repository.MessageRepository;

public class RMIChatService extends UnicastRemoteObject implements RMIChatServiceInterface {

    public RMIChatService() throws RemoteException {
        super();
    }

    @Override
    public void updateChatRemote(int userLoginId, int userReceiverID, String content) throws RemoteException {
        int conversationId = ConversationRepository.getConversationId(userLoginId, userReceiverID);
        MessageRepository.addNewMessage(conversationId, userLoginId,content);
    }
    
}
