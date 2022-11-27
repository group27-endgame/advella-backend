package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    Optional<ChatRoom> findByChatSender_UserIdAndChatRecipient_UserId(int userId, int userId1);

    List<ChatRoom> findByChatSender_UserId(int userId);
}
