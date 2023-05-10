package pjsassy.mbtichatclon.user.dto;

import lombok.Getter;

@Getter
public class AfterFindNewPasswordRequest {

    private Long userId;
    private String newPassword;
    private String newPasswordCheck;
}
