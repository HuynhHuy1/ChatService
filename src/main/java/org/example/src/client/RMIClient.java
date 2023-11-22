// package org.example.src.client;


// import java.net.MalformedURLException;
// import java.rmi.Naming;
// import java.rmi.NotBoundException;
// import java.rmi.RemoteException;
// import java.util.ArrayList;
// import java.util.List;

// import org.example.src.Model.User;
// import org.example.src.rmi.RMIServiceInterface;;



// /**
//  *
//  * @author framgiavn
//  *
//  */
// public class RMIClient {
//     public static void main(String args[]) {
//         try {
//             //Xác định RMI máy chủ.
//             RMIServiceInterface iAccount = (RMIServiceInterface) Naming.lookup("rmi://192.168.1.4:1099/RMIService");
//             System.out.println(iAccount);
//         } catch (Exception e){
//             System.out.println(e.getMessage());
//         }
//     }
// }