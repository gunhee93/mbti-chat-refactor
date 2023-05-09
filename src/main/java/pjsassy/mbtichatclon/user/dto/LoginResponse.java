package pjsassy.mbtichatclon.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pjsassy.mbtichatclon.jwt.dto.TokenResponse;

@Getter
public class LoginResponse {

    private Long userId;
    private String nickname;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;

    public LoginResponse(Long userId, String nickname, TokenResponse tokenResponse) {
        this.userId = userId;
        this.nickname = nickname;
        this.accessToken = tokenResponse.getAccessToken();
        this.refreshToken = tokenResponse.getRefreshToken();
        this.accessTokenExpiresIn = tokenResponse.getAccessTokenExpiresIn();
    }
}
