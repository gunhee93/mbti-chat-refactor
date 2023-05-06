package pjsassy.mbtichatclon.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pjsassy.mbtichatclon.common.httpMessageController.code.SuccessCode;
import pjsassy.mbtichatclon.common.httpMessageController.response.ApiResponse;
import pjsassy.mbtichatclon.user.dto.DuplicateEmailRequest;
import pjsassy.mbtichatclon.user.dto.DuplicateLoginIdRequest;
import pjsassy.mbtichatclon.user.dto.LoginResponse;
import pjsassy.mbtichatclon.user.dto.UserJoinRequest;
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
    public ResponseEntity<ApiResponse> login(LoginResponse loginResponse) {
        userService.login(loginResponse);

        return new ResponseEntity<>(new ApiResponse(SuccessCode.LOGIN_SUCCESS), HttpStatus.OK);
    }
    //로그아웃

    //마이페이지 조회

    //마이페이지 수정

    //비밀번호 수정

    //아이디 찾기

    //비밀번호 찾기

    //찾기 이후 비밀번호 변경

    //회원 탈퇴
}
