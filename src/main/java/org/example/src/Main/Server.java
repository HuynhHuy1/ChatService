package org.example.src.Main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.example.src.Model.Client;
import org.example.src.rmi.RMIChatService;
import org.example.src.rmi.RMIChatServiceInterface;

public class Server {

	private int port;
	private List<Client> listClient;
	private ServerSocket server;

	public static void main(String[] args) throws IOException {
		Thread thread = new Thread() {
			public void run() {
				try {
					RMIChatServiceInterface chatService = new RMIChatService();
					Registry registry = LocateRegistry.createRegistry(3099);
					registry.rebind("ChatService", chatService);
					System.out.println("New Server chat running on port 3099...");
				} catch (RemoteException e) {
					try {
						RMIChatServiceInterface chatService = new RMIChatService();
						Registry registry = LocateRegistry.getRegistry(3099);
						System.out.println("Server chat running on port 3099...");
						registry.rebind("ChatService", chatService);
					} catch (RemoteException e1) {
						System.out.println(e1.getMessage());
					}
				}
			}
		};
		thread.start();

		new Server(12345).run();
	}

	public Server(int port) {
		this.port = port;
		this.listClient = new ArrayList<Client>();
	}

	public void run() throws IOException {
		server = new ServerSocket(port);
		System.out.println("Port 12345 is now open.");
		while (true) {
			Socket socketClient = server.accept();
			new Thread(new HandleConnection(socketClient)).start();
		}
	}

	public List<Client> getListCLient() {
		return this.listClient;
	}
}