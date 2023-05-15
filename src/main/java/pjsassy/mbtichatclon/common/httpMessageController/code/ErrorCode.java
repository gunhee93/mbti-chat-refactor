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
    INVALID_TOKEN(400, "U005", "토큰 정보가 유효하지 않습니다."),
    NOT_FOUND_INFORMATION(404, "U006", "토큰에서 유저정보를 찾을 수 없습니다."),
    WRONG_PASSWORD(405, "U007", "기존 비밀번호가 틀렸습니다.."),
    INVALID_CODE(400, "U008", "인증코드가 틀렸습니다."),
    NO_MATCHES_PASSWORD(400, "U009", "비밀번호가 일치하지 않습니다."),
    NO_MATCHES_INFO(400, "U010", "유저 정보가 일치하지 않습니다."),
    NO_INFORMATION(400, "U011", "유저정보가 일치하지 않습니다."),

    //chat
    NOT_FOUND_ROOM(404, "C001", "채팅방이 활성화 되지 않았습니다."),

    //Post
    WRONG_CATEGORY(400, "P001", "카테고리 선택이 잘못 되었습니다.");

    private final int status;
    private final String divisionCode;
    private final String message;

    ErrorCode(int status, String divisionCode, String message) {
        this.status = status;
        this.divisionCode = divisionCode;
        this.message = message;
    }
}
