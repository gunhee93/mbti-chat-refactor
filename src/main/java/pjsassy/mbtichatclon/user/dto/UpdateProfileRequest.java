package pjsassy.mbtichatclon.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class UpdateProfileRequest {

    @NotNull(message = "닉네임은 Null 일 수 없습니다.")
    private String nickname;
    @NotNull(message = "이메일은 Null 일 수 없습니다.")
    private String email;
    @NotNull(message = "MBTI 는 Null 일 수 없습니다.")
    private String mbti;
    @NotNull(message = "성별은 Null 일 수 없습니다.")
    private String gender;
}
