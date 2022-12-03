package com.advella.advellabackend.integration;

import com.advella.advellabackend.model.User;
import com.advella.advellabackend.model.chat.ChatMessage;
import com.advella.advellabackend.model.chat.ChatNotification;
import com.advella.advellabackend.model.chat.ChatRoom;
import com.advella.advellabackend.unit.ChatMessageService;
import com.advella.advellabackend.unit.ChatRoomService;
import com.advella.advellabackend.unit.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final UserService userService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        User messageSender = userService.getUserById(chatMessage.getChatMessageSender().getUserId()).getBody();
        User messageRecipient = userService.getUserById(chatMessage.getChatMessageRecipient().getUserId()).getBody();

        var chatId = chatRoomService.getChatId(chatMessage.getChatMessageSender().getUserId(), chatMessage.getChatMessageRecipient().getUserId(), true);
        chatMessage.setChatId(chatId.get());
        chatMessage.setChatMessageSender(messageSender);
        chatMessage.setChatMessageRecipient(messageRecipient);

        chatMessage.setSentTime(chatMessage.getSentTime());

        ChatMessage saved = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessage.getChatMessageRecipient().getUserId()), "/queue/messages",
                new ChatNotification(
                        saved.getId().toString(),
                        saved.getChatMessageSender().getUserId(),
                        saved.getChatMessageSender().getUsername()));
    }

    @GetMapping("api/messages/{senderId}/{recipientId}/count")
    public ResponseEntity<Long> countNewMessages(@PathVariable Integer senderId, @PathVariable Integer recipientId) {
        return ResponseEntity.ok(chatMessageService.countNewMessages(senderId, recipientId));
    }

    @GetMapping("api/messages/{senderId}/{recipientId}")
    public ResponseEntity<?> findChatMessages(@PathVariable Integer senderId, @PathVariable Integer recipientId) {
        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId));
    }

    @GetMapping("api/messages/{id}")
    public ResponseEntity<?> findMessage(@PathVariable Integer id) {
        ChatMessage chatMessage = chatMessageService.findById(id);
        return ResponseEntity.ok(chatMessage);
    }

    @GetMapping("api/chatrooms/{userId}")
    public ResponseEntity<List<ChatRoom>> getUsersChatRoom(@PathVariable Integer userId) {
        return ResponseEntity.ok(chatRoomService.getUsersChatRooms(userId));
    }

    @ApiOperation(value = "Gets only basic user info", notes = "Gets only id and name of all users")
    @GetMapping("/api/users/infos")
    public ResponseEntity<List<User>> getAllUsersBasicInfo() {
        return ResponseEntity.ok(userService.getBasicUserInfos());
    }
}
