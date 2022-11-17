package com.advella.advellabackend.model.chat;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ChatNotification {
    private String id;
    private Integer senderId;
    private String senderName;
}
