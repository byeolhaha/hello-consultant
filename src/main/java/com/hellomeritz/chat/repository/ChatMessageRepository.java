package com.hellomeritz.chat.repository;

import com.hellomeritz.chat.domain.ChatMessage;

public interface ChatMessageRepository {
    ChatMessage save(ChatMessage chatMessage);
}
