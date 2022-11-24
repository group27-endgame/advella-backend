package com.advella.advellabackend.model.chat;

import com.advella.advellabackend.model.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Chats_Message")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(notes = "ID", example = "1", required = true)
    private Integer id;
    @Column(name = "chat_id")
    @ApiModelProperty(notes = "Chat ID", example = "12", required = true)
    private String chatId;
    @ManyToOne
    @JoinColumn(name="chat_sender_id", updatable = false, insertable = false)
    @ApiModelProperty(notes = "Message sender", example = "User model", required = true)
    private User chatMessageSender;
    @ManyToOne
    @JoinColumn(name="chat_recipient_id", updatable = false, insertable = false)
    @ApiModelProperty(notes = "Message recipient", example = "User model", required = true)
    private User chatMessageRecipient;
    @Column(name = "chat_content")
    @ApiModelProperty(notes = "Content of message", example = "Hello", required = true)
    private String chatContent;
    @Column(name = "chat_datetime")
    private Date sentTime;
    @Column(name = "chat_status")
    @ApiModelProperty(notes = "Message status", example = "DELIVERED")
    private MessageStatus chatStatus;
}
