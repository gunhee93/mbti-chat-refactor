package pjsassy.mbtichatclon.common.httpMessageController.code;

import lombok.Getter;

@Getter
public enum ErrorCode {
    /**
     * HTTP Status Code
     * 400: Bad Request
     * 401: Unauthorized
     * 403: Forbidden
     * 404: Not Found
     * 500: Internal Server Error
     */
    // Common
    INTERNAL_SERVER_ERROR(500, "C001", "서버 내부 오류입니다."),
    INVALID_INPUT_VALUE(400, "C002", "잘못된 입력입니다."),
    UNAUTHORIZED(401, "C003", "인증되지 않은 사용자입니다."),

    //User
    DUPLICATE_LOGIN_ID(400, "U001", "중복된 아이디입니다."),
    DUPLICATE_EMAIL(400, "U002", "중복된 이메일입니다."),
    NOT_FOUND_USER(404, "U003", "회원을 찾을 수 없습니다."),
    NO_INFORMATION(400, "U004", "토큰 정보가 유효하지 않습니다.");

    private final int status;
    private final String divisionCode;
    private final String message;

    ErrorCode(int status, String divisionCode, String message) {
        this.status = status;
        this.divisionCode = divisionCode;
        this.message = message;
    }
}
