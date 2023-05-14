package pjsassy.mbtichatclon.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pjsassy.mbtichatclon.common.httpMessageController.code.SuccessCode;
import pjsassy.mbtichatclon.common.httpMessageController.response.ApiResponse;
import pjsassy.mbtichatclon.common.util.RedisUtil;
import pjsassy.mbtichatclon.jwt.dto.TokenResponse;
import pjsassy.mbtichatclon.user.dto.EmailRequest;
import pjsassy.mbtichatclon.user.service.UserUtilService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserUtilController {

    private final RedisUtil redisUtil;
    private final UserUtilService userUtilService;


    //토큰 재발급
    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@RequestHeader(value = "Authorization") String acTokenRequest,
                                                 @RequestHeader(value = "RefreshToken") String rfTokenRequest) {
        String accessToken = acTokenRequest.substring(7);
        String refreshToken = rfTokenRequest.substring(7);

        TokenResponse tokenResponse = userUtilService.reissue(accessToken, refreshToken);

        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }

    //인증 코드 이메일 전송
    @PostMapping("/email")
    public ResponseEntity<ApiResponse> sendEmail(@Validated @RequestBody EmailRequest emailRequest) {
        userUtilService.sendEmail(emailRequest);
        return new ResponseEntity<>(new ApiResponse(SuccessCode.SEND_EMAIL), HttpStatus.OK);
    }
}
