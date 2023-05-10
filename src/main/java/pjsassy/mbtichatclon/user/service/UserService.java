package pjsassy.mbtichatclon.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pjsassy.mbtichatclon.common.httpMessageController.code.ErrorCode;
import pjsassy.mbtichatclon.common.httpMessageController.exception.CustomIllegalArgumentException;
import pjsassy.mbtichatclon.common.util.RedisUtil;
import pjsassy.mbtichatclon.jwt.TokenProvider;
import pjsassy.mbtichatclon.jwt.dto.TokenResponse;
import pjsassy.mbtichatclon.user.domain.Email;
import pjsassy.mbtichatclon.user.domain.User;
import pjsassy.mbtichatclon.user.dto.*;
import pjsassy.mbtichatclon.user.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
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

    public LoginResponse login(LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(), loginRequest.getPassword());
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        User user = findById(Long.valueOf(authenticate.getName()));
        TokenResponse tokenResponse = tokenProvider.generateTokenDto(authenticate);
        redisUtil.setDataExpire(authenticate.getName(), tokenResponse.getRefreshToken(), 1000*60*60*24*7);

        return new LoginResponse(user.getId(), user.getNickname(), tokenResponse);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new CustomIllegalArgumentException(ErrorCode.NOT_FOUND_USER);
                });
    }

    public void logout(String accessToken, String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new CustomIllegalArgumentException(ErrorCode.INVALID_TOKEN);
        }

        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userId = authentication.getName();

        String findRefreshToken = redisUtil.getData(userId);
        if (findRefreshToken != null) {
            redisUtil.deleteData(userId);
        }

        Long expiration = tokenProvider.getExpiration(accessToken);
        redisUtil.setDataExpire(accessToken, "logout", expiration);
    }

    public UserProfileResponse getProfile(Long loginUserId) {
        User user = findById(loginUserId);

        return new UserProfileResponse(
                user.getLoginId(),
                user.getNickname(),
                user.getEmail().getEmail(),
                user.getMbti(),
                user.getGender());
    }

    @Transactional
    public UpdateProfileResponse updateProfile(Long loginUserId, UpdateProfileRequest updateProfileRequest) {
        User findUser = findById(loginUserId);

        findUser.updateProfile(updateProfileRequest.getNickname(), updateProfileRequest.getEmail(),
                updateProfileRequest.getMbti(), updateProfileRequest.getGender());

        return new UpdateProfileResponse(updateProfileRequest.getNickname(), updateProfileRequest.getEmail(),
                updateProfileRequest.getMbti(), updateProfileRequest.getGender());
    }

    @Transactional
    public void updatePassword(Long userId, UpdatePasswordRequest updatePasswordRequest) {
        User findUser = findById(userId);

        if (!encoder.matches(updatePasswordRequest.getPassword(), findUser.getPassword())) {
            throw new CustomIllegalArgumentException(ErrorCode.WRONG_PASSWORD);
        }

        String updatePassword = updatePasswordRequest.getUpdatePassword();
        findUser.changePassword(encoder.encode(updatePassword));
    }

    public FindIdResponse findMyId(FindIdRequest findIdRequest) {
        String redisEmail = redisUtil.getData(findIdRequest.getCode());
        String email = findIdRequest.getEmail();

        if (!redisEmail.equals(email)) {
            throw new CustomIllegalArgumentException(ErrorCode.INVALID_CODE);
        }

        User findUser = userRepository.findByEmail(new Email(email))
                .orElseThrow(() -> {
                    throw new CustomIllegalArgumentException(ErrorCode.NOT_FOUND_USER);
                });

        return new FindIdResponse(findUser.getLoginId());
    }

    public FindPasswordResponse findMyPassword(FindPasswordRequest findPasswordRequest) {
        String redisEmail = redisUtil.getData(findPasswordRequest.getCode());
        String email = findPasswordRequest.getEmail();

        if (!redisEmail.equals(email)) {
            throw new CustomIllegalArgumentException(ErrorCode.INVALID_CODE);
        }

        User findUser = userRepository.findByEmail(new Email(email))
                .orElseThrow(() -> {
                    throw new CustomIllegalArgumentException(ErrorCode.NOT_FOUND_USER);
                });

        return new FindPasswordResponse(findUser.getId());
    }

    @Transactional
    public LoginResponse afterFindNewPassword(AfterFindNewPasswordRequest afterFindNewPasswordRequest) {

        if (!afterFindNewPasswordRequest.getNewPassword()
                .equals(afterFindNewPasswordRequest.getNewPasswordCheck())) {
            throw new CustomIllegalArgumentException(ErrorCode.NO_MATCHES_PASSWORD);
        }

        User findUser = findById(afterFindNewPasswordRequest.getUserId());
        findUser.changePassword(encoder.encode(afterFindNewPasswordRequest.getNewPassword()));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                findUser.getLoginId(), afterFindNewPasswordRequest.getNewPassword());
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenResponse tokenResponse = tokenProvider.generateTokenDto(authenticate);

        redisUtil.setDataExpire(authenticate.getName(), tokenResponse.getRefreshToken(), 1000 * 60 * 60 * 7);

        return new LoginResponse(findUser.getId(), findUser.getNickname(), tokenResponse);
    }

    @Transactional
    public void delete(Long userId) {
        User findUser = findById(userId);
        userRepository.delete(findUser);
    }
}
