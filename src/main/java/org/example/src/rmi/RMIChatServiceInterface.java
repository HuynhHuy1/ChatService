package org.example.src.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIChatServiceInterface extends Remote {
    public void updateChatRemote(int userLogin, int userReceiver, String content) throws RemoteException;
}
