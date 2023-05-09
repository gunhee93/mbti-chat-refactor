package pjsassy.mbtichatclon.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pjsassy.mbtichatclon.common.httpMessageController.code.SuccessCode;
import pjsassy.mbtichatclon.common.httpMessageController.response.ApiResponse;
import pjsassy.mbtichatclon.common.util.SecurityUtil;
import pjsassy.mbtichatclon.user.dto.*;
import pjsassy.mbtichatclon.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    //회원 가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signUp(@Validated @RequestBody UserJoinRequest userJoinRequest) {
        userService.signUp(userJoinRequest);

        return new ResponseEntity(new ApiResponse(SuccessCode.SIGNUP_SUCCESS), HttpStatus.OK);
    }

    //회원 가입 시 아이디 중복 검사
    @PostMapping("/signup/id")
    public ResponseEntity<ApiResponse> duplicateLoginId(
            @Validated @RequestBody DuplicateLoginIdRequest duplicateLoginIdRequest) {
        userService.duplicateLoginId(duplicateLoginIdRequest);

        return new ResponseEntity(new ApiResponse(SuccessCode.CAN_USE_LOGIN_ID), HttpStatus.OK);
    }

    //회원 가입 시 이메일 중복 검사
    @PostMapping("/signup/email")
    public ResponseEntity<ApiResponse> duplicateEmail(
            @Validated @RequestBody DuplicateEmailRequest duplicateEmailRequest) {
        userService.duplicateEmail(duplicateEmailRequest);

        return new ResponseEntity(new ApiResponse(SuccessCode.CAN_USE_EMAIL), HttpStatus.OK);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.login(loginRequest);

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    //로그아웃
    @GetMapping("/logout")
    public ResponseEntity<ApiResponse> logout(
            @RequestHeader(value = "Authorization") String acTokenRequest,
            @RequestHeader(value = "RefreshToken") String rfTokenRequest
    ) {
        String accessToken = acTokenRequest.substring(7);
        String refreshToken = rfTokenRequest.substring(7);

        userService.logout(accessToken, refreshToken);
        return new ResponseEntity<>(new ApiResponse(SuccessCode.LOGOUT_COMPLATE), HttpStatus.OK);
    }

    //마이페이지 조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getProfile(@PathVariable Long userId) {
        Long loginUserId = SecurityUtil.getCurrentUserId();

        UserProfileResponse userProfileResponse = userService.getProfile(loginUserId);

        return new ResponseEntity<>(userProfileResponse, HttpStatus.OK);
    }

    //마이페이지 수정
    @PatchMapping("/{userId}")
    public ResponseEntity<UpdateProfileResponse> updateProfile(
            @PathVariable Long userId,
            @Validated @RequestBody UpdateProfileRequest updateProfileRequest) {
        Long loginUserId = SecurityUtil.getCurrentUserId();
        UpdateProfileResponse updateProfileResponse = userService.updateProfile(loginUserId, updateProfileRequest);

        return new ResponseEntity<>(updateProfileResponse, HttpStatus.OK);
    }

    //비밀번호 수정

    //아이디 찾기

    //비밀번호 찾기

    //찾기 이후 비밀번호 변경

    //회원 탈퇴
}
