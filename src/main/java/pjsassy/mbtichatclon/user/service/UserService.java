package pjsassy.mbtichatclon.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pjsassy.mbtichatclon.common.httpMessageController.code.ErrorCode;
import pjsassy.mbtichatclon.common.httpMessageController.exception.CustomIllegalArgumentException;
import pjsassy.mbtichatclon.user.domain.Email;
import pjsassy.mbtichatclon.user.domain.User;
import pjsassy.mbtichatclon.user.dto.DuplicateEmailRequest;
import pjsassy.mbtichatclon.user.dto.DuplicateLoginIdRequest;
import pjsassy.mbtichatclon.user.dto.LoginResponse;
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
        userRepository.findByLoginId(duplicateLoginIdRequest.getLoginId())
                .ifPresent(u -> {throw new CustomIllegalArgumentException(ErrorCode.DUPLICATE_LOGIN_ID);
                });
    }

    public void duplicateEmail(DuplicateEmailRequest duplicateEmailRequest) {
        String email = duplicateEmailRequest.getEmail();
        userRepository.findByEmail(new Email(email))
                .ifPresent(u -> {throw new CustomIllegalArgumentException(ErrorCode.DUPLICATE_EMAIL);
                });
    }

    public void login(LoginResponse loginResponse) {
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new CustomIllegalArgumentException(ErrorCode.NOT_FOUND_USER);
                });
    }
}
