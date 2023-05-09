package pjsassy.mbtichatclon.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {

    private String loginId;
    private String password;
}
