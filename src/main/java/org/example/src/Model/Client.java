package org.example.src.Model;

import java.net.Socket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private String userName;
    private Socket socket;
}
