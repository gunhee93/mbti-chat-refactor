package pjsassy.mbtichatclon.common.httpMessageController.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pjsassy.mbtichatclon.common.httpMessageController.code.SuccessCode;

import static lombok.AccessLevel.PROTECTED;

@Getter
@RequiredArgsConstructor(access = PROTECTED)
public class ApiResponse {

    private String code;
    private String message;

    public ApiResponse(SuccessCode successCode) {
        this.code = successCode.getCode();
        this.message = successCode.getMessage();
    }
}
