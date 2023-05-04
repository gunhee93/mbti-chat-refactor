package pjsassy.mbtichatclon.common.httpMessageController.exception;

import lombok.Getter;
import pjsassy.mbtichatclon.common.httpMessageController.code.ErrorCode;

@Getter
public class CustomIllegalArgumentException extends IllegalArgumentException{

    private final ErrorCode errorCode;

    public CustomIllegalArgumentException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
