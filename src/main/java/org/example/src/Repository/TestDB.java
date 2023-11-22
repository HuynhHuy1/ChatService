package org.example.src.Repository;

import java.util.List;

import org.example.src.Model.Message;

public class TestDB {
    public static void main(String[] args) {
        List<Message> listMEssage = MessageRepository.getMessageByConversationId(2);
        for (Message message : listMEssage) {
            System.out.println(message.getContent());
        }
    }  
}
