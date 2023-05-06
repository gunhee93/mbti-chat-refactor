package pjsassy.mbtichatclon.common.httpMessageController.code;

import lombok.Getter;

@Getter
public enum SuccessCode {
    SIGNUP_SUCCESS("SignUp", "회원가입에 성공하였습니다."),
    CAN_USE_LOGIN_ID("CanUseLoginId", "사용 가능한 아이디입니다."),
    CAN_USE_EMAIL("CanUseEmail", "사용 가능한 이메일입니다."),
    LOGIN_SUCCESS("Login", "로그인 되었습니다.");

    private final String code;
    private final String message;

    SuccessCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
