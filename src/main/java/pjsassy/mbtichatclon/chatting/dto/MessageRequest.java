package pjsassy.mbtichatclon.chatting.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class MessageRequest {

    private String type;
    private String roomId;
    private String sendUserId;
    private String content;

}
