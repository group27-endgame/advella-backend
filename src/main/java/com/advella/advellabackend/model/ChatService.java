package com.advella.advellabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="service_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private Service service;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="users_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private User userBidder;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="users_id" ,insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private User userPoster;
}
