package pjsassy.mbtichatclon.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pjsassy.mbtichatclon.common.httpMessageController.code.ErrorCode;
import pjsassy.mbtichatclon.common.httpMessageController.exception.CustomIllegalArgumentException;

public class SecurityUtil {

    private SecurityUtil() {}

    public static Long getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new CustomIllegalArgumentException(ErrorCode.NOT_FOUND_INFORMATION);
        }

        return Long.parseLong(authentication.getName());
    }
}
