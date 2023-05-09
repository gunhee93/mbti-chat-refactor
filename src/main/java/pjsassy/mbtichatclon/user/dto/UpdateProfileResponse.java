package pjsassy.mbtichatclon.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateProfileResponse {

    private String nickname;
    private String email;
    private String mbti;
    private String gender;

}
