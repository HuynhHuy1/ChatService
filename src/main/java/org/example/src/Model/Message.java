package org.example.src.Model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message implements Serializable {
    private static final long serialVersionUID = 8523655099374056501L; 
    private int id;
    private int conversation_id;
    private int sender_id;
	private String content;
}
