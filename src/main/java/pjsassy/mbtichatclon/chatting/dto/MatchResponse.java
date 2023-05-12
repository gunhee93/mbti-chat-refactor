package pjsassy.mbtichatclon.chatting.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class MatchResponse {

    private String type;
    private String roomId;
    private String matchedUserNickname;
}
