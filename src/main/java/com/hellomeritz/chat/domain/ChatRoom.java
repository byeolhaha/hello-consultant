package com.hellomeritz.chat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Table(name = "chat_rooms")
@Getter
@Entity
public class ChatRoom {

    private static final int USER_ID_MIN_VALUE = 1;
    private static final int FC_ID_MIN_VALUE = 1;

    protected ChatRoom() {
    }

    @Column(name = "chat_room_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long chatRoomId;

    @Column(name = "fc_id", nullable = false)
    private long fcId;

    @Column(name = "foreigner_id", nullable = false)
    private long foreignerId;

    @Column(name = "chat_room_password")
    private String chatRoomPassword;

    @Column(name = "salt")
    private String salt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private ChatRoom(
            long fcId,
            long foreignerId
    ) {
        Assert.isTrue(foreignerId >= USER_ID_MIN_VALUE, "userId는 음수이거나 0일 수 없습니다.");
        Assert.isTrue(fcId >= FC_ID_MIN_VALUE, "fcId는 음수이거나 0일 수 없습니다.");

        this.fcId = fcId;
        this.foreignerId = foreignerId;
        this.createdAt = LocalDateTime.now();
    }

    public static ChatRoom of(
            long fcId,
            long userId
    ) {
        return new ChatRoom(
                fcId,
                userId
        );
    }

    public void setChatRoomPassword(
            String chatRoomPassword
    ) {
        this.chatRoomPassword = chatRoomPassword;
    }

    public void setSalt(
            String salt
    ) {
        this.salt = salt;
    }

}
