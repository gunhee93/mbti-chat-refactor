package pjsassy.mbtichatclon.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pjsassy.mbtichatclon.common.httpMessageController.code.ErrorCode;
import pjsassy.mbtichatclon.common.httpMessageController.exception.CustomIllegalArgumentException;
import pjsassy.mbtichatclon.user.domain.User;
import pjsassy.mbtichatclon.user.dto.DuplicateEmailRequest;
import pjsassy.mbtichatclon.user.dto.DuplicateLoginIdRequest;
import pjsassy.mbtichatclon.user.dto.UserJoinRequest;
import pjsassy.mbtichatclon.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public void signUp(UserJoinRequest userJoinRequest) {
        User user = userJoinRequest.toEntity();

        userRepository.save(user);
    }


    public void duplicateLoginId(DuplicateLoginIdRequest duplicateLoginIdRequest) {
        userRepository.findByLoginId(duplicateLoginIdRequest)
                .ifPresent(u -> {throw new CustomIllegalArgumentException(ErrorCode.DUPLICATE_LOGIN_ID);
                });
    }

    public void duplicateEmail(DuplicateEmailRequest duplicateEmailRequest) {
        userRepository.findByEmail(duplicateEmailRequest)
                .ifPresent(u -> {throw new CustomIllegalArgumentException(ErrorCode.DUPLICATE_EMAIL);
                });
    }
}
