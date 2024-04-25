package com.hellomeritz.chat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.util.Assert;

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

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "chat_room_password")
    private String chatRoomPassword;

    @Column(name = "salt")
    private String salt;

    private ChatRoom(
            long fcId,
            long userId
    ) {
        Assert.isTrue(userId >= USER_ID_MIN_VALUE, "userId는 음수이거나 0일 수 없습니다.");
        Assert.isTrue(fcId >= FC_ID_MIN_VALUE, "fcId는 음수이거나 0일 수 없습니다.");

        this.fcId = fcId;
        this.userId = userId;
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
