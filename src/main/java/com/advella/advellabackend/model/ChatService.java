package com.advella.advellabackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Chats_Service")
public class ChatService {
    @Id
    @Column(name = "chat_id")
    private int chatId;
    @Column(name = "chat_message")
    private String message;
}
