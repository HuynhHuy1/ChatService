package org.example.src.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {
    private int userCreateId;
    private int anotherUserId;
    private String userName;
}
