package com.advella.advellabackend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "chatId")
@Table(name = "Chats_Service")
public class ChatService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JsonIgnore
    private User userBidder;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="users_id" ,insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @JsonIgnore
    private User userPoster;
}
