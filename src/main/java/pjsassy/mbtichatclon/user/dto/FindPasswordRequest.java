package pjsassy.mbtichatclon.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
public class FindPasswordRequest {

    @NotNull(message = "Email 은 Null 일 수 없습니다.")
    private String email;
    @NotNull(message = "아이디는 Null 일 수 없습니다.")
    private String loginId;
    @NotNull(message = "인증번호는 Null 일 수 없습니다.")
    private String code;
}
