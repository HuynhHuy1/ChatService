package org.example.src.Utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.example.src.Model.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseUtil {
    private Socket socket;
    

    public ResponseUtil(Socket socket) {
        this.socket = socket;
    }

    public void redirectMessageClient(String message) throws IOException{
        PrintWriter out = new PrintWriter(this.socket.getOutputStream());
        out.println(message);
        out.flush();
    }
    public void reponseClient(Response response) throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream());
        System.out.println(response.getData() + "from Utils");
        out.println(this.objectToJson(response));
        out.flush();
    }

    public String objectToJson(Response response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(response);
    }
}
