package pjsassy.mbtichatclon.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileResponse {

    private String loginId;
    private String nickname;
    private String email;
    private String mbti;
    private String gender;

}
