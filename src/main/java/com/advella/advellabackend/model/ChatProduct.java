package com.advella.advellabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Chats_Product")
public class ChatProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    @ApiModelProperty(notes = "Chat ID", example = "1", required = true)
    private int chatId;
    @Column(name = "chat_message")
    @ApiModelProperty(notes = "Message", example = "Hello")
    private String message;
    @ManyToOne
    @JoinColumn(name="product_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private Product product;
    @ManyToOne
    @JoinColumn(name="users_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private User userBidder;
    @ManyToOne
    @JoinColumn(name="users_id" ,insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private User userPoster;
}
