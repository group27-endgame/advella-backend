package com.advella.advellabackend.model.chat;

import com.advella.advellabackend.model.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Chats_Room")
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(notes = "ID", example = "1", required = true)
    private String id;
    @Column(name = "chat_id")
    @ApiModelProperty(notes = "Chat Id", example = "12", required = true)
    private String chatId;
    @ManyToOne
    @JoinColumn(name = "chat_sender_id", updatable = false, insertable = false)
    @ApiModelProperty(notes = "Sender", example = "User model", required = true)
    private User chatSender;
    @ManyToOne
    @JoinColumn(name = "chat_recipient_id", updatable = false, insertable = false)
    @ApiModelProperty(notes = "Recipient", example = "User model", required = true)
    private User chatRecipient;
}
