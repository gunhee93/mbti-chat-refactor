package pjsassy.mbtichatclon.chatting.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pjsassy.mbtichatclon.user.domain.User;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(name = "ROOM")
public class ChattingRoom {

    @Id
    @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_user_id")
    private User sendUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiveUser")
    private User receiveUser;

    public ChattingRoom(User sendUser, User receiveUser) {
        this.sendUser = sendUser;
        this.receiveUser = receiveUser;
    }

    public void enterUser(User receiveUser){
        this.receiveUser = receiveUser;
    }
}
