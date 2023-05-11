package pjsassy.mbtichatclon.chatting.domain.message;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pjsassy.mbtichatclon.chatting.domain.chattingroom.ChattingRoom;
import pjsassy.mbtichatclon.user.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "MESSAGE")
public class Message {

    @Id
    @GeneratedValue
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChattingRoom room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_user_id")
    private User sendUser;

    private String content;

    @CreatedDate
    private LocalDateTime createdAt;


    @Builder
    public Message(ChattingRoom room, User sendUser, String content) {
        this.room = room;
        this.sendUser = sendUser;
        this.content = content;
    }

    public static Message of(ChattingRoom room, User sendUser, String content) {
        return Message.builder()
                .room(room)
                .sendUser(sendUser)
                .content(content)
                .build();
    }
}
