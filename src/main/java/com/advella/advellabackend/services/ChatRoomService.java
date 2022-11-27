package com.advella.advellabackend.services;

import com.advella.advellabackend.model.User;
import com.advella.advellabackend.model.chat.ChatRoom;
import com.advella.advellabackend.repositories.IChatRoomRepository;
import com.advella.advellabackend.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {

    private final IChatRoomRepository chatRoomRepository;
    private final IUserRepository userRepository;

    public Optional<String> getChatId(Integer senderId, Integer recipientId, boolean createIfNotExist) {
        User sender;
        User recipient;
        try {
            sender = userRepository.findById(senderId).orElseThrow();
            recipient = userRepository.findById(recipientId).orElseThrow();
        } catch (Exception e) {
            System.out.println("Did not find sender or recipient! RecipientId: "+recipientId+" SenderId: "+senderId);
            return Optional.empty();
        }

        return chatRoomRepository.findByChatSender_UserIdAndChatRecipient_UserId(senderId, recipientId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if (!createIfNotExist) {
                        return Optional.empty();
                    }
                    String chatId = String.format("%s_%s", sender.getUserId(), recipient.getUserId());

                    ChatRoom senderRecipient = new ChatRoom(null, chatId, sender, recipient);

                    ChatRoom recipientSender = new ChatRoom(null, chatId, recipient, sender);

                    sender.getSendChatRoom().add(senderRecipient);
                    recipient.getReceivedChatRoom().add(recipientSender);

                    chatRoomRepository.save(senderRecipient);
                    chatRoomRepository.save(recipientSender);

                    return Optional.of(chatId);
                });
    }
}
